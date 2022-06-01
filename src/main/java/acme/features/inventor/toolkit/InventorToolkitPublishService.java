/*
 * InventorToolkitPublishService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.toolkit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Amount;
import acme.entities.item.ItemType;
import acme.entities.toolkit.Toolkit;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.features.systemConfiguration.SpamFilter.SystemConfigurationSpamFilterService;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class InventorToolkitPublishService implements AbstractUpdateService<Inventor, Toolkit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorToolkitRepository repository;
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;
	
	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;
	
	@Autowired
	protected SystemConfigurationSpamFilterService spamFilterService;

	// AbstractUpdateService<Inventor, Toolkit> interface ---------------------------


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;

		boolean result;
		int toolkitId;
		Toolkit toolkit;
		Inventor inventor;

		toolkitId = request.getModel().getInteger("id");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		inventor = toolkit.getInventor();
		result = toolkit.getDraftMode() && request.isPrincipal(inventor);

		return result;
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

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "title", "description","assemblyNotes", "link", "totalPrice");
	}

	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Toolkit existing;

			existing = this.repository.findOneToolkitByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId()==entity.getId(), "code", "inventor.toolkit.form.error.code.unique");
			
			final String regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$";
			errors.state(request, entity.getCode().matches(regexp), "code", "inventor.toolkit.form.error.code.regexp");
		}
		
		
		if (!errors.hasErrors("title")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getTitle()), "title", "inventor.toolkit.form.error.spam");
		}
		if (!errors.hasErrors("description")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getDescription()), "description", "inventor.toolkit.form.error.spam");
		}
		if (!errors.hasErrors("assemblyNotes")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getAssemblyNotes()), "assemblyNotes", "inventor.toolkit.form.error.spam");
		}
		
		final Collection<Amount> amounts = this.repository.findAmountsByToolkit(entity.getId());
		final Boolean anyComponents = amounts.stream().anyMatch(a->a.getItem().getType().equals(ItemType.COMPONENT));
		final Boolean anyTools = amounts.stream().anyMatch(a->a.getItem().getType().equals(ItemType.TOOL));
		
		errors.state(request, anyComponents && anyTools, "*", "inventor.toolkit.form.error.empty");
		
		
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title", "description","assemblyNotes", "link", "totalPrice", "draftMode");
	}

	@Override
	public void update(final Request<Toolkit> request, final Toolkit entity) {
		assert request != null;
		assert entity != null;

		entity.setDraftMode(false);
		this.repository.save(entity);
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
