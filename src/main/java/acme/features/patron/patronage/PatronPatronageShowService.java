package acme.features.patron.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronages.Patronage;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.forms.MoneyExchange;
import acme.features.any.userAccount.AnyUserAccountRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.roles.Patron;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class PatronPatronageShowService implements AbstractShowService<Patron, Patronage> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected PatronPatronageRepository repository;
		
		@Autowired
		protected AuthenticatedSystemConfigurationRepository repositorySC;
		
		@Autowired
		protected AuthenticatedMoneyExchangePerformService exchangeService;
    
    @Autowired
		protected AnyUserAccountRepository invRepository;

		// AbstractCreateService<Authenticated, Provider> interface ---------------

	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int itemId;
		Patronage item;
		int principalId; 
		
		principalId = request.getPrincipal().getAccountId();
		itemId = request.getModel().getInteger("id");
		item = this.repository.findOnePatronageById(itemId);
		result = item.getPatron().getUserAccount().getId()==principalId; 

		return result;
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		assert request != null;
		
		Patronage result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOnePatronageById(id);
		return result;
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "status", "legalStuff", "code", "budget", "creationMoment", "optionalLink", 
				"initialDate", "finalDate", "isPublished", "inventor.userAccount.identity.name", "inventor.userAccount.identity.surname",
				"inventor.userAccount.username", "inventor.company", "inventor.statement", "inventor.optionalLink");
		model.setAttribute("exchange", this.moneyExchange(request));
	
		
		final Inventor inventor = entity.getInventor();
		model.setAttribute("inventor", inventor);
		model.setAttribute("inventors", this.invRepository.findAllInventors());		
	}
	
	private Money moneyExchange(final Request<Patronage> request) {
		int masterId;
        masterId = request.getModel().getInteger("id");
        final Patronage p = this.repository.findOnePatronageById(masterId);
        
        final SystemConfiguration sys = this.repositorySC.findSystemConfiguration();
        
        final String sysCurr = sys.getSystemCurrency(); 
        
        final Money moneda = p.getBudget();
        
        final MoneyExchange monEx = this.exchangeService.computeMoneyExchange(moneda, sysCurr);
        
        return monEx.getTarget();


    }

}
