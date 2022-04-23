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

package acme.features.any.toolkit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Amount;
import acme.entities.item.Item;
import acme.entities.tookit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;



@Service
public class AnyToolkitShowService implements AbstractShowService<Any, Toolkit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyToolkitRepository repository;

	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;

		boolean result;
		final int toolkitId;
		final Toolkit toolkit;
		
		toolkitId = request.getModel().getInteger("id");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		result = !toolkit.isDraftMode();

		return result;
	}


	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title","description", "assemblyNotes", "link","totalPrice");
	}



	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
        assert request != null;

        Toolkit result;
        int id;

        id = request.getModel().getInteger("id");
        result = this.repository.findOneToolkitById(id);
        result.setTotalPrice(this.calcularPrecioTotal(request));
        return result;
    }
	
	
	 private Money calcularPrecioTotal(final Request<Toolkit> request) {
	        int masterId;
	        masterId = request.getModel().getInteger("id");
	        Double cantidad = 0.;
	        String moneda = "";

	        final Money result = new Money();

	        final Collection<Amount> amounts = this.repository.findManyAmountByMasterId(masterId);

	        for(final Amount amount:amounts) {
	            final Item i = amount.getItem();
	            cantidad =  cantidad + i.getRetailPrice().getAmount() * amount.getUnits();
	            moneda = i.getRetailPrice().getCurrency();
	        }

	        result.setAmount(cantidad);
	        result.setCurrency(moneda);

	        return result;


	    }



	

}
