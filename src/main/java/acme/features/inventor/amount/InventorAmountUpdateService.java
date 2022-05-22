/*
 * InventorToolkitUpdateService.java
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
import acme.entities.item.ItemType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorAmountUpdateService implements AbstractUpdateService<Inventor, Amount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorAmountRepository repository;

	// AbstractUpdateService<Inventor, Toolkit> -------------------------------------


	@Override
	public boolean authorise(final Request<Amount> request) {
		assert request != null;

		final int inventorId = request.getPrincipal().getActiveRoleId();
		final int amountId = request.getModel().getInteger("id");
		final Amount amount = this.repository.findOneAmountById(amountId);
		return inventorId == amount.getToolkit().getInventor().getId();

		
	}

	@Override
	public void validate(final Request<Amount> request, final Amount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("units") && entity.getItem().getType().equals(ItemType.TOOL)) {
			errors.state(request, entity.getUnits() == 1, "units", "inventor.amount.form.error.tool.unique");		
		}
	}

	@Override
	public void bind(final Request<Amount> request, final Amount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "units", "item.code");
	}

	@Override
	public void unbind(final Request<Amount> request, final Amount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "units", "item", "item.code");
		if (entity.getItem().getType()==ItemType.COMPONENT) {
			model.setAttribute("type", "component");
		}
		if (entity.getItem().getType()==ItemType.TOOL) {
			model.setAttribute("type", "tool");
		}
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

	@Override
	public void update(final Request<Amount> request, final Amount entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}

}
