package acme.features.inventor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.features.systemConfiguration.SpamFilter.SystemConfigurationSpamFilterService;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorItemPublishService implements AbstractUpdateService<Inventor, Item>{

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

			boolean result;
			int itemId;
			Item item;
			Inventor inventor;

			itemId = request.getModel().getInteger("id");
			item = this.repository.findOneItemById(itemId);
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
		public void update(final Request<Item> request, final Item entity) {
			assert request != null;
			assert entity != null;
			
			entity.setPublished(true);
			this.repository.save(entity);
			
		}

}
