package acme.features.inventor.chimpum;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import acme.entities.chimpum.Chimpum;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;


@Service
public class InventorChimpumUpdateService implements AbstractUpdateService<Inventor, Chimpum> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected InventorChimpumRepository repository;
		
		@Autowired
		protected AdministratorSystemConfigurationRepository sys;

		// AbstractCreateService<Inventor, Item> interface -------------------------

		@Override
		public boolean authorise(final Request<Chimpum> request) {
			assert request != null;

			return true;
		}

		@Override
		public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			request.bind(entity, errors, "title", "description", "initialDate","finalDate","budget","optionalLink");
			
		}

		@Override
		public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "code", "creationMoment", "title","description", "initialDate","finalDate","budget","optionalLink");
			
			
		}

		@Override
		public Chimpum findOne(final Request<Chimpum> request) {
			assert request != null;

			Chimpum result;
			int id;

			id = request.getModel().getInteger("id");
			result = this.repository.findOneChimpumById(id);

			return result;
		}

		@Override
		public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			if (!errors.hasErrors("code")) {
				
				final String regexp = "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$";
				errors.state(request, entity.getCode().matches(regexp), "code", "inventor.Chimpum.form.error.code.regexp");
			}
			
			
			if (!errors.hasErrors("initialDate")) {
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(entity.getCreationMoment());
				calendar.add(Calendar.DATE, 7);
				final Date minimumDeadline = calendar.getTime();
				
				errors.state(request, entity.getInitialDate().after(minimumDeadline), "initialDate", "inventor.chimpum.form.error.initial-date.duration");
			}
			
			if (!errors.hasErrors("finalDate")) {
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(entity.getInitialDate());
				calendar.add(Calendar.DATE, 7);
				final Date minimumDeadline = calendar.getTime();
				
				errors.state(request, entity.getFinalDate().after(minimumDeadline), "finalDate", "inventor.chimpum.form.error.final-date.duration");
			}
			
			if (!errors.hasErrors("budget")) {
				errors.state(request, entity.getBudget().getAmount() > 0, "budget", "inventor.create.chimpum.budget.positive");
			}
			
			if (!errors.hasErrors("budget")) {
				errors.state(request, entity.getBudget() != null, "budget", "inventor.create.chimpum.null.budget");
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
			
		}

		@Override
		public void update(final Request<Chimpum> request, final Chimpum entity) {
			assert request != null;
			assert entity != null;
			
			this.repository.save(entity);
			
		}
}
