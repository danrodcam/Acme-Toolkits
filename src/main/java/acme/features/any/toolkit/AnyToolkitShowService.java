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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkit.Toolkit;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;
import acme.systemConfiguration.SystemConfiguration;



@Service
public class AnyToolkitShowService implements AbstractShowService<Any, Toolkit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyToolkitRepository repository;
	
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;

	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;

		boolean result;
		final int toolkitId;
		final Toolkit toolkit;
		
		toolkitId = request.getModel().getInteger("id");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		result = !toolkit.getDraftMode();

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

	        final Money result = new Money();

	        final List<List<Object>> amounts = this.repository.getPricesByToolkitId(masterId);
	        
	        final SystemConfiguration sys = this.repositorySC.findSystemConfiguration();
	        final String sysCurr = sys.getSystemCurrency(); 

	        for (final List<Object>l:amounts) {
				final Double amount = (Double) l.get(0);
				final String currency = (String) l.get(1);
				if (currency.equals(sysCurr)) {
					cantidad = cantidad + amount;
				}else {
					final Money moneda = new Money();
					moneda.setAmount(amount);
					moneda.setCurrency(currency);
					final MoneyExchange monEx = this.exchangeService.computeMoneyExchange(moneda, sysCurr);
					cantidad = cantidad + monEx.getTarget().getAmount();
				}
	        }

	        result.setAmount(cantidad);
	        result.setCurrency(sysCurr);

	        return result;


	    }
	 




	

}
