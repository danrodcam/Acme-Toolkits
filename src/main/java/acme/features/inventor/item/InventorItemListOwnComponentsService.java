/*
 * AuthenticatedProviderCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;


@Service
public class InventorItemListOwnComponentsService implements AbstractListService<Inventor, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorItemRepository repository;

	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		return true;
	}


	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "code", "type", "technology","description", "retailPrice", "link","published");
	}



	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		assert request != null;
		
		Collection<Item> result;
		int inventorId;
		
		inventorId = request.getPrincipal().getActiveRoleId();
		if(request.getModel().hasAttribute("masterId")){
			final int toolkitId = request.getModel().getInteger("masterId");
			result = this.repository.findManyComponentsByToolkit(toolkitId);
		} else {
			result = this.repository.findManyComponentsByInventorId(inventorId);
		}
		return result;
	}

	

}
