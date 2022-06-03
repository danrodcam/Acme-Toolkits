package acme.features.inventor.deta;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.deta.Deta;
import acme.entities.item.Item;
import acme.entities.item.ItemType;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorDetaCreateService implements AbstractCreateService<Inventor, Deta>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorDetaRepository repository;
	
	@Autowired
	protected AdministratorSystemConfigurationRepository sys;
	// AbstractCreateService<Inventor, Item> interface -------------------------
	
	@Override
	public boolean authorise(final Request<Deta> request) {
		assert request != null;
		
		boolean result;
		final Item item = this.repository.findOneItemById(request.getModel().getInteger("masterId"));
		result = item.getInventor().getUserAccount().getId() == request.getPrincipal().getAccountId() && 
		item.getType()==ItemType.TOOL && item.getPublished()==true && item.getDeta() == null;
		
		return result;
	}

	@Override
	public void bind(final Request<Deta> request, final Deta entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "summary", "subject", "initialDate", 
				"finalDate", "allowance", "moreInfo");
		
	}

	@Override
	public void unbind(final Request<Deta> request, final Deta entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		
		request.unbind(entity, model, "code", "creationMoment", "summary", "subject", "initialDate", 
				"finalDate", "allowance", "moreInfo");
		model.setAttribute("item", this.repository.findOneItemById(request.getModel().getInteger("masterId")));
	}

	@Override
	public Deta instantiate(final Request<Deta> request) {
		assert request != null;

		Deta result;
		final Date moment = new Date(System.currentTimeMillis() -1);

		result = new Deta();
		result.setCreationMoment(moment);
		result.setSubject("");
		result.setSummary("");
		result.setMoreInfo("");
		String code = this.generateCode();
		Deta existing = this.repository.findOneDetaByCode(code);
		while(existing != null) {
			code = this.generateCode();
			existing = this.repository.findOneDetaByCode(code);
		}
		result.setCode(code);
		
		return result;
	}

	@Override
	public void validate(final Request<Deta> request, final Deta entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Deta existing;

			existing = this.repository.findOneDetaByCode(entity.getCode());
			errors.state(request, existing == null, "code", "inventor.deta.form.error.code.unique");
			
			final String regexp = "^\\w{6}:\\d{2}\\d{4}:\\d{2}$";
			errors.state(request, entity.getCode().matches(regexp), "code", "inventor.deta.form.error.code.regexp");
		}
		
		if (!errors.hasErrors("allowance")) {
			errors.state(request, entity.getAllowance() != null, "allowance", "inventor.create.deta.null.allowance");
		}
		
		if (!errors.hasErrors("allowance")) {
			errors.state(request, entity.getAllowance().getAmount() >= 0, "allowance", "inventor.create.deta.allowance.positive");
		}
		
		final String acceptedCurrency = this.sys.findSystemConfiguration().getAcceptedCurrency();
		final String[]currencys = acceptedCurrency.split(";");
		Boolean res = false;

		for(int i=0;i<currencys.length;i++) {
			if(currencys[i].equals(entity.getAllowance().getCurrency())) {
				res = true;
				break;
			}
		}
		errors.state(request, res.equals(true),"allowance", "inventor.create.deta.allowance.error.currency");
		
		if (!errors.hasErrors("initialDate")) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			final Date minimumDeadline = calendar.getTime();
			
			errors.state(request, entity.getInitialDate().after(minimumDeadline), "initialDate", "inventor.deta.form.error.initial-date.duration");
		}
		
		if (!errors.hasErrors("finalDate")) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getInitialDate());
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			final Date minimumDeadline = calendar.getTime();
			
			errors.state(request, entity.getFinalDate().after(minimumDeadline), "finalDate", "inventor.deta.form.error.final-date.duration");
		}
		
	}

	@Override
	public void create(final Request<Deta> request, final Deta entity) {
		assert request != null;
		assert entity != null;
		final Item item = this.repository.findOneItemById(request.getModel().getInteger("masterId"));
		item.setDeta(entity);
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