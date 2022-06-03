package acme.features.inventor.chimpum;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.entities.item.ItemType;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumCreateService implements AbstractCreateService<Inventor, Chimpum>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected AdministratorSystemConfigurationRepository sys;
	// AbstractCreateService<Inventor, Item> interface -------------------------
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		boolean result;
		final Item item = this.repository.findOneItemById(request.getModel().getInteger("masterId"));
		result = item.getInventor().getUserAccount().getId() == request.getPrincipal().getAccountId() && 
		item.getType()==ItemType.TOOL && item.getPublished()==true && item.getChimpum() == null;
		
		return result;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "title", "description", "initialDate", 
				"finalDate", "budget", "optionalLink");
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		
		request.unbind(entity, model, "code", "creationMoment", "title", "description", "initialDate", 
				"finalDate", "budget", "optionalLink");
		model.setAttribute("item", this.repository.findOneItemById(request.getModel().getInteger("masterId")));
	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		assert request != null;

		Chimpum result;
		final Date moment = new Date(System.currentTimeMillis() -1);

		result = new Chimpum();
		result.setCreationMoment(moment);
		result.setTitle("");
		result.setDescription("");
		result.setOptionalLink("");
		String code = this.generateCode();
		Chimpum existing = this.repository.findOneChimpumByCode(code);
		while(existing != null) {
			code = this.generateCode();
			existing = this.repository.findOneChimpumByCode(code);
		}
		result.setCode(code);
		
		return result;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Chimpum existing;

			existing = this.repository.findOneChimpumByCode(entity.getCode());
			errors.state(request, existing == null, "code", "inventor.chimpum.form.error.code.unique");
			
			//final String regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$";
			//errors.state(request, entity.getCode().matches(regexp), "code", "inventor.chimpum.form.error.code.regexp");
		}
		
		if (!errors.hasErrors("budget")) {
			errors.state(request, entity.getBudget() != null, "budget", "inventor.create.chimpum.null.budget");
		}
		
		if (!errors.hasErrors("budget")) {
			errors.state(request, entity.getBudget().getAmount() >= 0, "budget", "inventor.create.chimpum.budget.positive");
		}
		
		final String acceptedCurrency = this.sys.findSystemConfiguration().getAcceptedCurrency();
		final String[]currencys = acceptedCurrency.split(";");
		Boolean res = false;

		for(int i=0;i<currencys.length;i++) {
			if(currencys[i].equals(entity.getBudget().getCurrency())) {
				res = true;
				break;
			}
		}
		errors.state(request, res.equals(true),"budget", "inventor.create.chimpum.budget.error.currency");
		
		if (!errors.hasErrors("initialDate")) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			final Date minimumDeadline = calendar.getTime();
			
			errors.state(request, entity.getInitialDate().after(minimumDeadline), "initialDate", "inventor.chimpum.form.error.initial-date.duration");
		}
		
		if (!errors.hasErrors("finalDate")) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getInitialDate());
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			final Date minimumDeadline = calendar.getTime();
			
			errors.state(request, entity.getFinalDate().after(minimumDeadline), "finalDate", "inventor.chimpum.form.error.final-date.duration");
		}
		
	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		final Item item = this.repository.findOneItemById(request.getModel().getInteger("masterId"));
		item.setChimpum(entity);
		this.repository.save(item);
		this.repository.save(entity);
	}
	
	private String generateCode() {
		String result;
		
		final Date date = new Date();
		final ZoneId timeZone = ZoneId.systemDefault();
		final LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
		
		final Integer year = getLocalDate.getYear();
		final Integer month = getLocalDate.getMonthValue();
		final Integer day = getLocalDate.getDayOfMonth();
		
		final String anyo = year.toString().substring(2);
		String mes = month.toString();
		String dia = day.toString();
		
		if(mes.length()==1) {
			mes = "0" + mes;
		}
		
		if(day.toString().length()==1) {
			dia = "0" + dia.toString();
		}
		
		result = anyo + "-" + mes + "-" + dia;
		return result;
	}

}