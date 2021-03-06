package acme.features.inventor.item;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.ItemType;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.features.systemConfiguration.SpamFilter.SystemConfigurationSpamFilterService;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorToolCreateService implements AbstractCreateService<Inventor, Item>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorItemRepository repository;
	
	@Autowired
	protected SystemConfigurationSpamFilterService spamFilterService;
	
	@Autowired
	protected AdministratorSystemConfigurationRepository sys;

	// AbstractCreateService<Inventor, Item> interface -------------------------
	
	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		
		
		return true;
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "name", "technology", "description","retailPrice", "link");
		
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "code", "technology", "description","retailPrice", "link", "type", "published");
		
	}

	@Override
	public Item instantiate(final Request<Item> request) {
		assert request != null;

		Item result;
		Inventor inventor;

		inventor = this.repository.findOneInventorById(request.getPrincipal().getActiveRoleId());
		result = new Item();
		result.setName("");
		result.setType(ItemType.TOOL);
		result.setTechnology("");
		result.setDescription("");
		result.setPublished(false);
		result.setInventor(inventor);
		String code = this.generateCode();
		Item existing = this.repository.findOneItemByCode(code);
		while(existing != null) {
			code = this.generateCode();
			existing = this.repository.findOneItemByCode(code);
		}
		result.setCode(code);

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
			errors.state(request, existing == null, "code", "inventor.item.form.error.code.unique");
			
			final String regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$";
			errors.state(request, entity.getCode().matches(regexp), "code", "inventor.item.form.error.code.regexp");
		}
		
		if (!errors.hasErrors("retailPrice")) {
			errors.state(request, entity.getRetailPrice() != null, "retailPrice", "inventor.create.item.null.price");
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
			errors.state(request, entity.getRetailPrice().getAmount() >= 0, "retailPrice", "inventor.create.item.price.positive");
			
			final String acceptedCurrency = this.sys.findSystemConfiguration().getAcceptedCurrency();
			
			final String[]currencys = acceptedCurrency.split(";");
			
			Boolean res = false;
			
			for(int i=0;i<currencys.length;i++) {
				if(currencys[i].equals(entity.getRetailPrice().getCurrency())) {
					res = true;
					break;
				}
			}
			
			
			errors.state(request, res.equals(true),"retailPrice", "inventor.create.item.price.error.currency");
		}
	}

	@Override
	public void create(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}
	
	private String generateCode() {
		String result;
		String part1 = "";
		String part2 = "";
		String part3 = "";
		final Random r = new Random();
		for (int i = 0; i < 3; i++) {
			final char c = (char)(r.nextInt(26) + 'A');
			part1 = part1 + c;
	    } 
		
		for (int i = 0; i < 3; i++) {
			final char c = (char)(r.nextInt(10) + '0');
			part2 = part2 + c;
	    } 
		
		if(r.nextBoolean()) {
			final char c = (char)(r.nextInt(26) + 'A');
			part3 = "-" + c;
		}
		result = part1 + "-" + part2 + part3;
		return result;
	}

}