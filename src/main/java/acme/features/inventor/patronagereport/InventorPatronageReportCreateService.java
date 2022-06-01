/*
 * InventorPatronageReportCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.patronagereport;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronageReport.PatronageReport;
import acme.entities.patronages.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportCreateService implements AbstractCreateService<Inventor, PatronageReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPatronageReportRepository repository;

	// AbstractCreateService<Inventor, PatronageReport> interface -------------------------


	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		assert request != null;

		return true;
	}

	@Override
	public PatronageReport instantiate(final Request<PatronageReport> request) {
		assert request != null;

		PatronageReport result;
		int masterId;
		masterId = request.getModel().getInteger("masterId");
		Patronage patronage;
		patronage = this.repository.findOnePatronageById(masterId);
		
		result = new PatronageReport();
		result.setPatronage(patronage);

		return result;
	}

	@Override
	public void bind(final Request<PatronageReport> request, final PatronageReport entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date moment;
		String sequenceNumber;

		moment = new Date(System.currentTimeMillis() - 1);
		sequenceNumber = this.createAutomaticSequenceNumber(entity.getPatronage());
		request.bind(entity, errors, "memorandum", "link");
		entity.setCreationMoment(moment);
		entity.setAutomaticSequenceNumber(sequenceNumber);
		
	}

	@Override
	public void validate(final Request<PatronageReport> request, final PatronageReport entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		boolean confirmation;
		confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "inventor.patronage-report.form.error.confirmation");
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "memorandum", "link", "creationMoment", "patronage");
	}

	@Override
	public void create(final Request<PatronageReport> request, final PatronageReport entity) {
		assert request != null;
		assert entity != null;

		
		this.repository.save(entity);
	}
	
	private String createAutomaticSequenceNumber(final Patronage patronage){
		final String firstPart =  patronage.getCode();
		String secondPart = "0001";
		final List<PatronageReport> existingReports = this.repository.findManyPatronageReportByPatronageId(patronage.getId());
		if (!existingReports.isEmpty()) {
			final String[] lastSequence = existingReports.get(existingReports.size()-1).getAutomaticSequenceNumber().split(":");
			secondPart = String.format("%04d", Integer.valueOf(lastSequence[1]) + 1);
		}
		
		return firstPart + ":" + secondPart;
	}

}
