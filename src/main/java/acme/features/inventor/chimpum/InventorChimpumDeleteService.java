package acme.features.inventor.chimpum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorChimpumDeleteService implements AbstractDeleteService<Inventor, Chimpum> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected InventorItemRepository    itemRepository;

	// AbstractCreateService<Inventor, Item> interface -------------------------

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;

		boolean result;
		int masterId;
		Item item;
		Inventor inventor;

		masterId = request.getModel().getInteger("id");
		item = this.itemRepository.findOneItemByChimpumId(masterId);
		inventor = item.getInventor();
		result = request.isPrincipal(inventor);

		return result;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "creationMoment", "title","description", "initialDate","finalDate","budget","link");
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "title","description", "initialDate","finalDate","budget","link");
		
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;

		int id;
		Chimpum result;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneChimpumById(id);

		return result;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		final Item item = this.itemRepository.findOneItemByChimpumId(request.getModel().getInteger("id"));
		item.setChimpum(null);
		
		this.itemRepository.save(item);
		
		this.repository.delete(entity);	
	}
	
	


}