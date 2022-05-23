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

package acme.features.inventor.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronages.Patronage;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.systemConfiguration.SystemConfiguration;


@Service
public class InventorPatronageShowService implements AbstractShowService<Inventor, Patronage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPatronageRepository repository;
	
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;

	// AbstractCreateService<Authenticated, Provider> interface ---------------


	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int itemId;
		Patronage item;
		int principalId; 
		
		principalId = request.getPrincipal().getAccountId();
		itemId = request.getModel().getInteger("id");
		item = this.repository.findOnePatronageById(itemId);
		result = item.getInventor().getUserAccount().getId()==principalId; 

		return result;
	}


	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "status", "legalStuff", "code", "budget", "creationMoment", "optionalLink", 
				"initialDate", "finalDate", "patron.userAccount.identity.name", "patron.userAccount.identity.surname", 
				"patron.userAccount.username", "patron.company", "patron.statement", "patron.optionalLink");
		model.setAttribute("exchange", this.moneyExchange(request));
	}



	@Override
	public Patronage findOne(final Request<Patronage> request) {
		assert request != null;
		
		Patronage result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOnePatronageById(id);
		return result;
	}
	
	private Money moneyExchange(final Request<Patronage> request) {
		int masterId;
        masterId = request.getModel().getInteger("id");
        final Patronage p = this.repository.findOnePatronageById(masterId);
        
        final SystemConfiguration sys = this.repositorySC.findSystemConfiguration();
        
        final String sysCurr = sys.getSystemCurrency(); 
        
        final Money moneda = p.getBudget();
        
        final MoneyExchange monEx = this.exchangeService.computeMoneyExchange(moneda, sysCurr);
        
        return monEx.getTarget();


    }

	

}
