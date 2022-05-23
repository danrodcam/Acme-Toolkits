package acme.features.inventor.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronages.Patronage;
import acme.entities.patronages.PatronageStatus;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class InventorPatronageUpdateService implements AbstractUpdateService<Inventor, Patronage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPatronageRepository repository;

	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;


	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int patronageId;
		Patronage patronage;

		patronageId = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(patronageId);
		result = patronage != null && //
			patronage.getStatus().equals(PatronageStatus.PROPOSED) && //
			request.isPrincipal(patronage.getInventor());

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
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "status", "legalStuff", "code", "budget", "creationMoment", "optionalLink", 
			"initialDate", "finalDate", "patron.userAccount.identity.name", "patron.userAccount.identity.surname", 
			"patron.userAccount.username", "patron.company", "patron.statement", "patron.optionalLink");
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "status", "legalStuff", "code", "budget", "creationMoment", "optionalLink", 
			"initialDate", "finalDate", "patron.userAccount.identity.name", "patron.userAccount.identity.surname", 
			"patron.userAccount.username", "patron.company", "patron.statement", "patron.optionalLink");
		
		model.setAttribute("masterId", entity.getId());
		model.setAttribute("exchange", this.moneyExchange(request));
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
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
