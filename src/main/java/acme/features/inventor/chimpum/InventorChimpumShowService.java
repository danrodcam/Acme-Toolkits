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
		
		int principalId;
		
		final boolean result;
		
		int id;
		
		principalId = request.getPrincipal().getAccountId();
		
		
		id = request.getModel().getInteger("id");
		final Item item = this.itemRepository.findOneItemById(id);
		
		if(item!=null) {
			result = item.getInventor().getUserAccount().getId()==principalId;
		}else {
			
			final Chimpum chimpum = this.repository.findOneChimpumById(id);
				
			final Item item2 = this.itemRepository.findOneItemByChimpumId(chimpum.getId());
			
			result = item2.getInventor().getUserAccount().getId()==principalId;
			
			
		}
			
		
		
		return result;
	}


	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title", "description","initialDate", "finalDate", "budget","link");
		final Item item = this.itemRepository.findOneItemByChimpumId(entity.getId());
		model.setAttribute("published", item.getPublished());
		
	}



	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		
		final Chimpum result;
		int id;
		
		
		id = request.getModel().getInteger("id");
		final Item item = this.itemRepository.findOneItemById(id);
		
		if(item!=null) {
			result = item.getChimpum();
		}else {
			
			final Chimpum chimpum = this.repository.findOneChimpumById(id);
				
			result = chimpum;
		}
			
		
		
		
		
		
		return result;
	}

	

}
