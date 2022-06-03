package acme.features.inventor.priripi;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.piripi.Piripi;
import acme.entities.item.Item;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorPiripiUpdateService implements AbstractUpdateService<Inventor, Piripi> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPiripiRepository repository;
	
	@Autowired
	protected AdministratorSystemConfigurationRepository sys;
	
	

	// AbstractCreateService<Inventor, Item> interface -------------------------

	@Override
	public boolean authorise(final Request<Piripi> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Piripi> request, final Piripi entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationMoment", "title","description", "initialDate","finalDate","budget","link");
		
	}

	@Override
	public void unbind(final Request<Piripi> request, final Piripi entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "title","description", "initialDate","finalDate","budget","link");
		final Item item = this.repository.findOneItemByPiripiId(entity.getId());
		model.setAttribute("published", item.getPublished());
		
		
	}

	@Override
	public Piripi findOne(final Request<Piripi> request) {
		assert request != null;
		
		final Piripi result;
		int id;
		
		id = request.getModel().getInteger("id");

		final Item item = this.repository.findOneItemByPiripiId(id);
		
		result = item.getPiripi();
		return result;
	}

	@Override
	public void validate(final Request<Piripi> request, final Piripi entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			
			final String regexp = "^[0-9]{2}-[0-9]{2}-[0-9]{2}$";
			errors.state(request, entity.getCode().matches(regexp), "code", "inventor.Piripi.form.error.code.regexp");
		}
		
		if (!errors.hasErrors("initialDate")) {
			Calendar calendar;
			Date minimumDeadline;

			calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, 1);
			minimumDeadline = calendar.getTime();
			errors.state(request, entity.getInitialDate().after(minimumDeadline), "initialDate", "inventor.Piripi.form.error.initialDate");
		}
		
		if (!errors.hasErrors("finalDate")) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getInitialDate());
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			final Date minimumDeadline = calendar.getTime();
			
			errors.state(request, entity.getFinalDate().after(minimumDeadline), "finalDate", "inventor.Piripi.form.error.finalDate");
		}
		
		if (!errors.hasErrors("budget")) {
			errors.state(request, entity.getBudget().getAmount() > 0, "budget", "inventor.Piripi.form.error.positivePrice");
			final String acceptedCurrency = this.sys.findSystemConfiguration().getAcceptedCurrency();
			
			final String[]currencys = acceptedCurrency.split(";");
			
			Boolean res = false;
			
			for(int i=0;i<currencys.length;i++) {
				if(currencys[i].equals(entity.getBudget().getCurrency())) {
					res = true;
					break;
				}
		
			}
			errors.state(request, res.equals(true),"budget", "inventor.create.piripi.price.error.currency");
		}
		
		
	}

	@Override
	public void update(final Request<Piripi> request, final Piripi entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}


}
