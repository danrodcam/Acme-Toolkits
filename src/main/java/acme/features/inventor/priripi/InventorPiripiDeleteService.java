package acme.features.inventor.priripi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.piripi.Piripi;
import acme.entities.item.Item;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorPiripiDeleteService implements AbstractDeleteService<Inventor, Piripi> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPiripiRepository repository;
	
	@Autowired
	protected InventorItemRepository    itemRepository;

	// AbstractCreateService<Inventor, Item> interface -------------------------

	@Override
	public boolean authorise(final Request<Piripi> request) {
		assert request != null;

		boolean result;
		int masterId;
		Item item;
		Inventor inventor;

		masterId = request.getModel().getInteger("id");
		item = this.repository.findOneItemByPiripiId(masterId);
		inventor = item.getInventor();
		result = request.isPrincipal(inventor);

		return result;
	}

	@Override
	public void bind(final Request<Piripi> request, final Piripi entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "creationMoment", "title","description", "initialDate","finalDate","budget","link");
		
	}

	@Override
	public void unbind(final Request<Piripi> request, final Piripi entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "title","description", "initialDate","finalDate","budget","link");
		
	}

	@Override
	public Piripi findOne(final Request<Piripi> request) {
		assert request != null;

		int id;
		Piripi result;

		id = request.getModel().getInteger("id");
		result = this.repository.findOnePiripiById(id);

		return result;
	}

	@Override
	public void validate(final Request<Piripi> request, final Piripi entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Piripi> request, final Piripi entity) {
		assert request != null;
		assert entity != null;
		
		final Item item = this.repository.findOneItemByPiripiId(request.getModel().getInteger("id"));
		item.setPiripi(null);
		
		this.itemRepository.save(item);
		
		this.repository.delete(entity);	
	}
	
	


}