package acme.features.inventor.priripi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.piripi.Piripi;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorPiripiListOwnService implements AbstractListService<Inventor, Piripi> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected InventorPiripiRepository repository;

		// AbstractListService<Inventor, Piripi> interface ---------------

	@Override
	public boolean authorise(final Request<Piripi> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Piripi> findMany(final Request<Piripi> request) {
		assert request != null;
		
		Collection<Piripi> result;
		int inventorId;
		
		inventorId = request.getPrincipal().getActiveRoleId();
		
		result = this.repository.findManyPiripiByInventorId(inventorId);
		
		
		return result;
		
		
	}

	@Override
	public void unbind(final Request<Piripi> request, final Piripi entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "title", "description","initialDate", "finalDate", "budget","link");
		
	}

}
