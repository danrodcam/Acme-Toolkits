package acme.features.inventor.priripi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.piripi.Piripi;
import acme.entities.item.Item;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPiripiShowService implements AbstractShowService<Inventor, Piripi> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPiripiRepository repository;
	
	@Autowired
	protected InventorItemRepository itemRepository;
	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Piripi> request) {
		assert request != null;
		
		final boolean result;
		
		int principalId;
		int piripiId;
		piripiId = request.getModel().getInteger("id");
		
		final Item item = this.repository.findOneItemByPiripiId(piripiId);
		
		principalId = request.getPrincipal().getAccountId();
		
		result = item.getInventor().getUserAccount().getId()==principalId;
		
		return result;
	}


	@Override
	public void unbind(final Request<Piripi> request, final Piripi entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "title", "description","initialDate", "finalDate", "budget","link");
		final Item item = this.repository.findOneItemByPiripiId(entity.getId());
		model.setAttribute("published", item.getPublished());
		
	}



	@Override
	public Piripi findOne(final Request<Piripi> request) {
		assert request != null;
		
		final Piripi result;
		int id;
		
		id = request.getModel().getInteger("id");

		final Item item = this.repository.findOneItemByPiripiId(id);
		
		result = item.getPiripi();
		return result;
	}

	

}