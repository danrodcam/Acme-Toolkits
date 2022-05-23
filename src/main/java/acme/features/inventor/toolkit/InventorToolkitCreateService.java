/*
 * InventorToolkitCreateService.java
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

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkit.Toolkit;
import acme.features.authenticated.systemConfiguration.AuthenticatedSystemConfigurationRepository;
import acme.features.systemConfiguration.SpamFilter.SystemConfigurationSpamFilterService;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class InventorToolkitCreateService implements AbstractCreateService<Inventor, Toolkit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorToolkitRepository repository;
	
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repositorySC;

	@Autowired
	protected SystemConfigurationSpamFilterService spamFilterService;
	
	// AbstractCreateService<Inventor, Toolkit> interface -------------------------


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;
		
		
		return true;
		

		
	}

	@Override
	public Toolkit instantiate(final Request<Toolkit> request) {
		assert request != null;

		Toolkit result;
		Inventor inventor;

		inventor = this.repository.findOneInventorById(request.getPrincipal().getActiveRoleId());
		result = new Toolkit();
		result.setDraftMode(true);
		result.setInventor(inventor);
		String code = this.generateCode();
		Toolkit existing = this.repository.findOneToolkitByCode(code);
		while(existing != null) {
			code = this.generateCode();
			existing = this.repository.findOneToolkitByCode(code);
		}
		result.setCode(code);

		return result;
	}

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code","title", "description","assemblyNotes", "link", "totalPrice");
		
	}

	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Toolkit existing;

			existing = this.repository.findOneToolkitByCode(entity.getCode());
			errors.state(request, existing == null, "code", "inventor.toolkit.form.error.code.unique");
			
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

		
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title", "description","assemblyNotes", "link", "totalPrice", "draftMode");
	}

	@Override
	public void create(final Request<Toolkit> request, final Toolkit entity) {
		assert request != null;
		assert entity != null;

		final Money price = new Money();
		final SystemConfiguration sys = this.repositorySC.findSystemConfiguration();
        final String sysCurr = sys.getSystemCurrency(); 
		price.setAmount(0.00);
		price.setCurrency(sysCurr);
		entity.setTotalPrice(price);
		this.repository.save(entity);
	}

	
	private String generateCode() {
		String result;
		String part1 = "";
		String part2 = "";
		String part3 = "";
		final Random r = new Random();
		for (int i = 0; i < 3; i++) {
			final char c = (char)(r.nextInt(26) + 'A');
			part1 = part1 + c;
	    } 
		
		for (int i = 0; i < 3; i++) {
			final char c = (char)(r.nextInt(10) + '0');
			part2 = part2 + c;
	    } 
		
		if(r.nextBoolean()) {
			final char c = (char)(r.nextInt(26) + 'A');
			part3 = "-" + c;
		}
		result = part1 + "-" + part2 + part3;
		return result;
	}
}
