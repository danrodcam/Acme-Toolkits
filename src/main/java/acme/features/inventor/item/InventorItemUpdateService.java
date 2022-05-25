package acme.features.inventor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;

import acme.features.systemConfiguration.SpamFilter.SystemConfigurationSpamFilterService;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class InventorItemUpdateService implements AbstractUpdateService<Inventor, Item> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorItemRepository repository;
	
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;

	
	@Autowired
	protected SystemConfigurationSpamFilterService spamFilterService;

	// AbstractCreateService<Inventor, Item> interface -------------------------

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		boolean result;
		int masterId;
		Item item;
		Inventor inventor;

		masterId = request.getModel().getInteger("id");
		item = this.repository.findOneItemById(masterId);
		inventor = item.getInventor();
		result = (!item.getPublished()) && request.isPrincipal(inventor);

		return result;
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "name", "code", "technology", "description","retailPrice", "link");
		
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "code", "technology", "description","retailPrice", "link", "type", "published");
		model.setAttribute("exchange", this.moneyExchange(request));
		
	}

	@Override
	public Item findOne(final Request<Item> request) {
		assert request != null;

		Item result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneItemById(id);

		return result;
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Item existing;

			existing = this.repository.findOneItemByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId()==entity.getId(), "code", "inventor.item.form.error.code.unique");
			
			final String regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$";
			errors.state(request, entity.getCode().matches(regexp), "code", "inventor.item.form.error.code.regexp");
		}
		

		if (!errors.hasErrors("name")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getName()), "name", "inventor.item.form.error.spam");
		}
		
		if (!errors.hasErrors("technology")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getTechnology()), "technology", "inventor.item.form.error.spam");
		}
		
		if (!errors.hasErrors("description")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getDescription()), "description", "inventor.item.form.error.spam");
		}
		
		if (!errors.hasErrors("retailPrice")) {
			errors.state(request, !(entity.getRetailPrice().getAmount() < 0), "retailPrice", "inventor.create.item.price.positive");
		}
		
	}

	@Override
	public void update(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}
	
	private Money moneyExchange(final Request<Item> request) {
		int masterId;
        masterId = request.getModel().getInteger("id");
        final Item it = this.repository.findOneItemById(masterId);
        
        final SystemConfiguration sys = this.repositorySC.findSystemConfiguration();
        
        final String sysCurr = sys.getSystemCurrency(); 
        
        final Money moneda = it.getRetailPrice();
        
        final MoneyExchange monEx = this.exchangeService.computeMoneyExchange(moneda, sysCurr);
        
        return monEx.getTarget();


    }


}
