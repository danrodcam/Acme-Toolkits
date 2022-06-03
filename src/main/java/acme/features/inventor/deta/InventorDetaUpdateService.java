package acme.features.inventor.deta;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.deta.Deta;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;


@Service
public class InventorDetaUpdateService implements AbstractUpdateService<Inventor, Deta> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected InventorDetaRepository repository;
		
		@Autowired
		protected AdministratorSystemConfigurationRepository sys;

		// AbstractCreateService<Inventor, Item> interface -------------------------

		@Override
		public boolean authorise(final Request<Deta> request) {
			assert request != null;

			return true;
		}

		@Override
		public void bind(final Request<Deta> request, final Deta entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			request.bind(entity, errors, "summary", "subject", "initialDate", "finalDate", "allowance", "moreInfo");
			
		}

		@Override
		public void unbind(final Request<Deta> request, final Deta entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "summary", "subject", "initialDate", "finalDate", "allowance", "moreInfo");
			
			
		}

		@Override
		public Deta findOne(final Request<Deta> request) {
			assert request != null;

			Deta result;
			int id;

			id = request.getModel().getInteger("id");
			result = this.repository.findOneDetaById(id);

			return result;
		}

		@Override
		public void validate(final Request<Deta> request, final Deta entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			if (!errors.hasErrors("code")) {
				
				final String regexp = "^\\w{6}:\\d{2}\\d{4}:\\d{2}$";
				errors.state(request, entity.getCode().matches(regexp), "code", "inventor.Deta.form.error.code.regexp");
			}
			
			
			if (!errors.hasErrors("initialDate")) {
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(entity.getCreationMoment());
				calendar.add(Calendar.DATE, 7);
				final Date minimumDeadline = calendar.getTime();
				
				errors.state(request, entity.getInitialDate().after(minimumDeadline), "initialDate", "inventor.deta.form.error.initial-date.duration");
			}
			
			if (!errors.hasErrors("finalDate")) {
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(entity.getInitialDate());
				calendar.add(Calendar.DATE, 7);
				final Date minimumDeadline = calendar.getTime();
				
				errors.state(request, entity.getFinalDate().after(minimumDeadline), "finalDate", "inventor.deta.form.error.final-date.duration");
			}
			
			if (!errors.hasErrors("allowance")) {
				errors.state(request, entity.getAllowance().getAmount() > 0, "budget", "inventor.create.deta.allowance.positive");
			}
			
			if (!errors.hasErrors("allowance")) {
				errors.state(request, entity.getAllowance() != null, "budget", "inventor.create.deta.null.allowance");
			}
			
			final String acceptedCurrency = this.sys.findSystemConfiguration().getAcceptedCurrency();
			final String[] currencys = acceptedCurrency.split(";");
			Boolean res = false;

			for(int i=0;i<currencys.length;i++) {
				if(currencys[i].equals(entity.getAllowance().getCurrency())) {
					res = true;
					break;
				}
			}
			errors.state(request, res.equals(true),"allowance", "inventor.create.deta.allowance.error.currency");
			
		}

		@Override
		public void update(final Request<Deta> request, final Deta entity) {
			assert request != null;
			assert entity != null;
			
			this.repository.save(entity);
			
		}
}
