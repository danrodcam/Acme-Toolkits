/*
 * InventorToolkitDeleteService.java
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
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorAmountDeleteService implements AbstractDeleteService<Inventor, Amount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorAmountRepository repository;

	// AbstractDeleteService<Inventor, Toolkit> interface -------------------------


	@Override
	public boolean authorise(final Request<Amount> request) {
		assert request != null;

		boolean result;
		int amountId;
		Toolkit toolkit;
		Inventor inventor;

		amountId = request.getModel().getInteger("id");
		final Amount amount = this.repository.findOneAmountById(amountId);
		toolkit = this.repository.findOneToolkitById(amount.getToolkit().getId());
		inventor = toolkit.getInventor();
		result = toolkit.getDraftMode() && request.isPrincipal(inventor);

		return result;
	}

	@Override
	public void bind(final Request<Amount> request, final Amount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "units");
		
	}

	@Override
	public void unbind(final Request<Amount> request, final Amount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "units", "item");
		model.setAttribute("publishedItems", this.repository.findManyPublishedTools());
		model.setAttribute("type", "tool");
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
	public void validate(final Request<Amount> request, final Amount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Amount> request, final Amount entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
	}

}
