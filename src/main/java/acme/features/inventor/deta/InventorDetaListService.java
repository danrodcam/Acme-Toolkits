package acme.features.inventor.deta;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.deta.Deta;
import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorDetaListService implements AbstractListService<Inventor, Deta> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorDetaRepository repository;

	// AbstractCreateService<Authenticated, Provider> interface ---------------

	@Override
	public boolean authorise(final Request<Deta> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Deta> findMany(final Request<Deta> request) {
		assert request != null;
		
		Collection<Deta> result;
		Collection<Item> aux;
		
		final int inventorId = request.getPrincipal().getActiveRoleId();
		aux = this.repository.findManyItemByInventor(inventorId);
		result = aux.stream().map(Item::getDeta).filter(deta -> deta != null).collect(Collectors.toList());
		return result;
	}

	@Override
	public void unbind(final Request<Deta> request, final Deta entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "creationMoment", "summary", "subject", "initialDate", 
				"finalDate", "allowance", "moreInfo");
	}

}
