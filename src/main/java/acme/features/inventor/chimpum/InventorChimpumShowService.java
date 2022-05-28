package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorChimpumShowService implements AbstractShowService<Inventor, Chimpum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected InventorItemRepository itemRepository;
	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		final boolean result;
		
		int principalId;
		
		final Item item = this.itemRepository.findOneItemById(request.getModel().getInteger("masterId"));
		
		principalId = request.getPrincipal().getAccountId();
		
		result = item.getInventor().getUserAccount().getId()==principalId;
		
		return result;
	}


	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "title", "description","initialDate", "finalDate", "budget","link");
		
	}



	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		
		final Chimpum result;
		int id;
		
		id = request.getModel().getInteger("masterId");
		final Item item = this.itemRepository.findOneItemById(id);
		
		result = item.getChimpum();
		return result;
	}

	

}
