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

package acme.features.inventor.deta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.deta.Deta;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;


@Service
public class InventorDetaShowService implements AbstractShowService<Inventor, Deta> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorDetaRepository repository;
	
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;

	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Deta> request) {
		assert request != null;

	
		return true;
	}


	@Override
	public void unbind(final Request<Deta> request, final Deta entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "summary", "subject", "initialDate", 
				"finalDate", "allowance", "moreInfo");
	}



	@Override
	public Deta findOne(final Request<Deta> request) {
		assert request != null;
		
		Deta result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneDetaById(id);
		return result;
	}
	
}
