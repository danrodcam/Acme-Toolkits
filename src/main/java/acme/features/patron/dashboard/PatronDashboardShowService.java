/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.patron.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronages.PatronageStatus;
import acme.forms.Dashboard;
import acme.framework.components.database.Record;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Patron;

@Service
public class PatronDashboardShowService implements AbstractShowService<Patron, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;

		int patronId = request.getPrincipal().getActiveRoleId();
		result = new Dashboard();
		result.setStatsPatronagesCurrency(this.createStatsPatronageMap(patronId));
		

		return result;
		
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "statsPatronagesCurrency");
	}

	
	private Map<Record,Map<String,Double>> createStatsPatronageMap(int patronId){
		final List<List<Object>> statsList = this.repository.getStatsPatronages(patronId);
		final Map<Record, Map<String, Double>> map = new HashMap<Record,Map<String,Double>>();
		for (final List<Object> l:statsList) {
			final Map<String, Double> stats = new HashMap<String,Double>();
			final Record record = new Record();
			stats.put("TOTAL", ((Long) l.get(6)).doubleValue());
			stats.put("AVG", (Double) l.get(2));
			stats.put("STDEV", (Double) l.get(3));
			stats.put("MIN", (Double) l.get(5));
			stats.put("MAX", (Double) l.get(4));
			record.put((String) l.get(1), (PatronageStatus) l.get(0));
			map.put(record, stats);
			
		}
		return map;
	}
}
