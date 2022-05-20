/*
 * InventorAmountCreateService.java
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
import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorAmountComponentCreateService implements AbstractCreateService<Inventor, Amount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorAmountRepository repository;

	// AbstractCreateService<Inventor, Amount> interface -------------------------


	@Override
	public boolean authorise(final Request<Amount> request) {
		assert request != null;

		boolean result;
		int toolkitId;
		Toolkit toolkit;
		int principalId;  
		
		principalId = request.getPrincipal().getAccountId();
		toolkitId = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		result = toolkit.getInventor().getUserAccount().getId()==principalId; 
		
		
		return result;
	}

	@Override
	public Amount instantiate(final Request<Amount> request) {
		assert request != null;

		Amount result;
		Toolkit toolkit;
		final int toolkitId = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		result = new Amount();
		result.setToolkit(toolkit);
		
		return result;
	}

	@Override
	public void bind(final Request<Amount> request, final Amount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "units");
		entity.setItem(this.repository.findItemById(request.getModel().getInteger("item")));
	}

	@Override
	public void validate(final Request<Amount> request, final Amount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		


		
	}

	@Override
	public void unbind(final Request<Amount> request, final Amount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "units");
		
		model.setAttribute("publishedItems", this.repository.findManyPublishedComponents());
		
		model.setAttribute("masterId", request.getModel().getAttribute("masterId"));
		model.setAttribute("type", "component");
		
		
	}

	@Override
	public void create(final Request<Amount> request, final Amount entity) {
		assert request != null;
		assert entity != null;
		Amount amount;
		
		if(this.repository.findManyComponentsByToolkit(entity.getToolkit().getId()).contains(entity.getItem())){
			amount = this.repository.findAmountByToolkitAndItem(entity.getToolkit().getId(), entity.getItem().getId());
			amount.setUnits(amount.getUnits() + entity.getUnits());
		} else {
			amount = entity;
		}
		this.repository.save(amount);
	}

	
	
}
