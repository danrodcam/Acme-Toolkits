/*
 * EmployerDutyCreateService.java
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
import java.util.GregorianCalendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronages.Patronage;
import acme.entities.patronages.PatronageStatus;
import acme.features.any.userAccount.AnyUserAccountRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.roles.Patron;


@Service
public class PatronPatronageCreateService implements AbstractCreateService<Patron, Patronage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronPatronageRepository repository;
	
	@Autowired
	protected AnyUserAccountRepository invRepository;

	// AbstractCreateService<Employer, Job> interface -------------------------


	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		return true;
	}

	@Override
	public Patronage instantiate(final Request<Patronage> request) {
		assert request != null;

		Patronage result;
		Patron patron;
		patron = this.repository.findPatronById(request.getPrincipal().getActiveRoleId());
		result = new Patronage();
		result.setPublished(false);
		result.setPatron(patron);
		result.setStatus(PatronageStatus.PROPOSED);
		final Date moment = new Date(System.currentTimeMillis() -1);
		result.setCreationMoment(moment);
		result.setInitialDate(new Date());
		result.setFinalDate(new Date());
		result.setInventor(new Inventor());
		
		String code = this.generateCode();
		Patronage existing = this.repository.findOnePatronageByCode(code);
		while(existing != null) {
			code = this.generateCode();
			existing = this.repository.findOnePatronageByCode(code);
		}
		result.setCode(code);

		return result;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "legalStuff", "budget", "optionalLink", "initialDate", "finalDate");
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

			calendar = new GregorianCalendar();
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
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "status", "legalStuff", "code", "budget", "creationMoment", "optionalLink", "initialDate", "finalDate","isPublished");
		model.setAttribute("inventors", this.invRepository.findAllInventors());
	}

	@Override
	public void create(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;

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