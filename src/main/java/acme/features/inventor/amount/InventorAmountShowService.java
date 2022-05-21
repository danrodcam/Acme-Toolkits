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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Amount;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;


@Service
public class InventorAmountShowService implements AbstractShowService<Inventor, Amount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorAmountRepository repository;

	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Amount> request) {
		assert request != null;

		final int inventorId = request.getPrincipal().getActiveRoleId();
		final int amountId = request.getModel().getInteger("id");
		final Amount amount = this.repository.findOneAmountById(amountId);
		return inventorId == amount.getToolkit().getInventor().getId();
	}


	@Override
	public void unbind(final Request<Amount> request, final Amount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "units", "item");
		model.setAttribute("publishedItems", this.repository.findManyPublishedTools());
		model.setAttribute("type", "tool");
		model.setAttribute("masterId", entity.getToolkit().getId());
		model.setAttribute("itemId", entity.getItem().getId());
		model.setAttribute("draftMode", entity.getToolkit().getDraftMode());
	}



	@Override
	public Amount findOne(final Request<Amount> request) {
		assert request != null;
		
		Amount result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneAmountById(id);
		return result;
	}

	

}
