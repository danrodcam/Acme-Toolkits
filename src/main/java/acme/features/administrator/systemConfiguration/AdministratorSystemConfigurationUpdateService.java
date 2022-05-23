package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractUpdateService;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationUpdateService implements AbstractUpdateService<Administrator, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	
	@Autowired
	protected AdministratorSystemConfigurationRepository repository;



	@Override
	public boolean authorise(final Request<SystemConfiguration> request) {
		assert request != null;

		return true;
	}
	
	
	@Override
	public void validate(final Request<SystemConfiguration> request, final SystemConfiguration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		
		Boolean r1 = false;
		
		Boolean r2 = true;
	
			
		final String systemCurrency = entity.getSystemCurrency();
		
		final String regexp = "^[A-Z]{3}?$";
		
		errors.state(request, systemCurrency.matches(regexp), "systemCurrency", "administrator.system-configuration.form.error.systemCurrency");
			
		final String acceptedCurrency = entity.getAcceptedCurrency();
			
			
		final String[]currencys = acceptedCurrency.split(";");
			
		for(int i=0;i<currencys.length;i++) {
			if(currencys[i].equals(systemCurrency)) {
				r1 = true;
			}else if(!currencys[i].matches(regexp)) {
				r2 = false;
				
				
			}
		}
		
		errors.state(request, r1.equals(true), "acceptedCurrency", "administrator.system-configuration.form.error.acceptedCurrency");
		
		errors.state(request, r2.equals(true), "acceptedCurrency", "administrator.system-configuration.form.error.acceptedCurrency2");
		
		
		errors.state(request, entity.getWeakSpamThreshold()>0.0, "weakSpamThreshold","administrator.system-configuration.form.error.weakSpamThreshold");
			
		
		
		
		
				
		}
			
		

	@Override
	public void bind(final Request<SystemConfiguration> request, final SystemConfiguration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "systemCurrency", "acceptedCurrency", "strongSpamTerms","weakSpamTerms", "strongSpamThreshold", "weakSpamThreshold");
	}

	@Override
	public void unbind(final Request<SystemConfiguration> request, final SystemConfiguration entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "systemCurrency", "acceptedCurrency", "strongSpamTerms","weakSpamTerms", "strongSpamThreshold", "weakSpamThreshold");
	}
	
	
	

	@Override
	public SystemConfiguration findOne(final Request<SystemConfiguration> request) {
		assert request != null;

		SystemConfiguration result;
		
		result = this.repository.findSystemConfiguration();

		return result;
	}

	
	

	@Override
	public void update(final Request<SystemConfiguration> request, final SystemConfiguration entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
	
	
	
}
