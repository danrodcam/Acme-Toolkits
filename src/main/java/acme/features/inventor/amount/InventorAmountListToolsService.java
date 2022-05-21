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

package acme.features.inventor.amount;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Amount;
import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;


@Service
public class InventorAmountListToolsService implements AbstractListService<Inventor, Amount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorAmountRepository repository;

	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Amount> request) {
		assert request != null;
		final int inventorId = request.getPrincipal().getActiveRoleId();
		final int toolkitId = request.getModel().getInteger("masterId");
		return inventorId == this.repository.findOneToolkitById(toolkitId).getInventor().getId();
	}


	@Override
	public void unbind(final Request<Amount> request, final Collection<Amount> entities, final Model model) {
		assert request != null;
		assert !CollectionHelper.someNull(entities);
		assert model != null;

		int masterId;
		Toolkit toolkit;
		final boolean showCreate;

		masterId = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(masterId);
		showCreate = (toolkit.getDraftMode() && request.isPrincipal(toolkit.getInventor()));

		model.setAttribute("masterId", masterId);
		model.setAttribute("showCreate", showCreate);
	}
	
	@Override
	public void unbind(final Request<Amount> request, final Amount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "item.name", "item.code", "units", "item.technology","item.description", "item.retailPrice");
	}



	@Override
	public Collection<Amount> findMany(final Request<Amount> request) {
		assert request != null;
		
		Collection<Amount> result;
	
		final int toolkitId = request.getModel().getInteger("masterId");
		result = this.repository.findToolAmountsByToolkit(toolkitId);
		
		
		return result;
	}

	

}
