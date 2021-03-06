/*
 * EmployerDutyUpdateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.patron.patronage;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronages.Patronage;
import acme.features.administrator.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.features.any.userAccount.AnyUserAccountRepository;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.roles.Patron;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class PatronPatronageUpdateService implements AbstractUpdateService<Patron, Patronage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronPatronageRepository repository;
	
	@Autowired
	protected AnyUserAccountRepository invRepository;
	
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;
	
	@Autowired
	protected AdministratorSystemConfigurationRepository sys;

	// AbstractUpdateService<Employer, Duty> -------------------------------------


	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int patronageId;
		Patronage patronage;

		patronageId = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(patronageId);
		result = (patronage != null && !patronage.isPublished() && request.isPrincipal(patronage.getPatron()));

		return result;
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {

		Patronage result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOnePatronageById(id);

		return result;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "legalStuff", "budget", "optionalLink", 
				"initialDate", "finalDate");
		
		final Inventor inventor = this.repository.findInventorById(request.getModel().getInteger("inventor"));
		
		entity.setInventor(inventor);
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Patronage existing;

		existing = this.repository.findOnePatronageByCode(entity.getCode());
		errors.state(request, existing == null || existing.getId()==entity.getId(), "code", "patron.patronage.form.error.code.unique");
		
		if (!errors.hasErrors("initialDate")) {
			Calendar calendar;
			Date minimumDeadline;

			calendar = Calendar.getInstance();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			minimumDeadline = calendar.getTime();
			errors.state(request, entity.getInitialDate().after(minimumDeadline), "initialDate", "patron.patronage.form.error.date.creation");
		}
		
		if (!errors.hasErrors("finalDate")) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getInitialDate());
			calendar.add(Calendar.MONTH, 1);
			final Date minimumDeadline = calendar.getTime();
			
			errors.state(request, entity.getFinalDate().after(minimumDeadline), "finalDate", "patron.patronage.form.error.date.duration");
		}
		
		if (!errors.hasErrors("budget")) {
			errors.state(request, entity.getBudget().getAmount() > 0, "budget", "patron.create.patronage.price.error.positive");
			
			final String acceptedCurrency = this.sys.findSystemConfiguration().getAcceptedCurrency();
			
			final String[]currencys = acceptedCurrency.split(";");
			
			Boolean res = false;
			
			for(int i=0;i<currencys.length;i++) {
				if(currencys[i].equals(entity.getBudget().getCurrency())) {
					res = true;
					break;
				}
			}
			
			
			errors.state(request, res.equals(true),"budget", "patron.create.patronage.price.error.currency");
		}

	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "status", "legalStuff", "code", "budget", "creationMoment", "optionalLink", "initialDate", "finalDate", "isPublished");
		model.setAttribute("masterId", entity.getId());
		model.setAttribute("exchange", this.moneyExchange(request));
		model.setAttribute("inventors", this.invRepository.findAllInventors());
		final Inventor inventor = entity.getInventor();
		model.setAttribute("inventor", inventor);
		}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

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
