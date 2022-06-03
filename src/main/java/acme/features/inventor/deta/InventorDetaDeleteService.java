package acme.features.inventor.deta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.deta.Deta;
import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorDetaDeleteService implements AbstractDeleteService<Inventor, Deta> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected InventorDetaRepository repository;

		// AbstractCreateService<Inventor, Item> interface -------------------------

		@Override
		public boolean authorise(final Request<Deta> request) {
			assert request != null;

			boolean result;
			int masterId;
			Item item;
			Inventor inventor;

			masterId = request.getModel().getInteger("id");
			item = this.repository.findOneItemByDeta(masterId);
			inventor = item.getInventor();
			result = request.isPrincipal(inventor);

			return result;
		}

		@Override
		public void bind(final Request<Deta> request, final Deta entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			request.bind(entity, errors, "code", "creationMoment", "summary", "subject", "initialDate", "finalDate", "allowance", "moreInfo");
			
		}

		@Override
		public void unbind(final Request<Deta> request, final Deta entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "code", "creationMoment", "summary", "subject", "initialDate", "finalDate", "allowance", "moreInfo");
			
		}

		@Override
		public Deta findOne(final Request<Deta> request) {
			assert request != null;

			int id;
			Deta result;

			id = request.getModel().getInteger("id");
			result = this.repository.findOneDetaById(id);

			return result;
		}

		@Override
		public void validate(final Request<Deta> request, final Deta entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;
			
		}

		@Override
		public void delete(final Request<Deta> request, final Deta entity) {
			assert request != null;
			assert entity != null;
			
			final Item item = this.repository.findOneItemByDeta(request.getModel().getInteger("id"));
			item.setDeta(null);
			
			this.repository.save(item);
			
			this.repository.delete(entity);	
		}
}

