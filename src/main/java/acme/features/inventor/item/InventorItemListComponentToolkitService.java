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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Amount;
import acme.entities.item.Item;
import acme.entities.item.ItemType;
import acme.features.inventor.toolkit.InventorToolkitRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;


@Service
public class InventorItemListComponentToolkitService implements AbstractListService<Inventor, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorItemRepository repository;
	
	@Autowired
	protected InventorToolkitRepository repositoryToolkit;

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

		request.unbind(entity, model, "name", "code", "technology","description", "retailPrice", "link");
	}
	
	
	
	@Override
	public List<Item> findMany(final Request<Item> request){
		assert request != null;
		
		int masterId;
		
		masterId = request.getModel().getInteger("masterId");
		
		final List<Item> result = new ArrayList<>() ;
		
		final Collection<Amount> amounts = this.repositoryToolkit.findItemsByToolkit(masterId);
		
		for(final Amount amount:amounts) {
			final Item i = amount.getItem();
			if(i.getType() == ItemType.COMPONENT) {
				result.add(i);
			}
		}
		return result;
		
	}

	

}