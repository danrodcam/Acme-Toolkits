/*
 * AdministratorAdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

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
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;



	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;


		result = new Dashboard();
		
		
		result.setStatsComponents(this.createStatsComponentsMap());
		result.setStatsTools(this.createStatsToolsMap());
		result.setNumberOfTools(this.repository.numberOfTools());
		result.setNumberOfComponents(this.repository.numberOfComponents());
		result.setStatsPatronages(this.createStatsPatronageMap());
		result.setRatioToolsChimpum(this.ratioToolsChimpum());
		result.setStatsChimpums(this.createStatsChimpumMap());
		

		return result;
		
	}
	
	
	
	private Double ratioToolsChimpum() {
		final int numberToolsWithChimpum = this.repository.numberToolsWithChimpum();
		final int numberOfTools = this.repository.numberOfTools();
		
		return (double) numberToolsWithChimpum/numberOfTools;
	}
	
	
	private Map<String,Map<String,Double>> createStatsChimpumMap(){
		final List<List<Object>> statsList = this.repository.getStatsChimpum();
		final Map<String,Map<String,Double>> map = new HashMap<String,Map<String,Double>>();
		for (final List<Object> l:statsList) {
			final Map<String, Double> stats = new HashMap<String,Double>();
			stats.put("AVG", (Double) l.get(1));
			stats.put("STDEV", (Double) l.get(2));
			stats.put("MIN", (Double) l.get(4));
			stats.put("MAX", (Double) l.get(3));
			map.put((String) l.get(0), stats);
			
		}
		return map;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "statsComponents", "numberOfComponents", "statsPatronages", "numberOfTools","statsTools","ratioToolsChimpum","statsChimpums");
	}
	
	private Map<Record,Map<String,Double>> createStatsComponentsMap(){
		final List<List<Object>> statsList = this.repository.getStatsComponents();
		final Map<Record,Map<String,Double>> map = new HashMap<Record,Map<String,Double>>();
		for (final List<Object> l:statsList) {
			final Record record = new Record();
			final Map<String, Double> stats = new HashMap<String,Double>();
			stats.put("AVG", (Double) l.get(2));
			stats.put("STDEV", (Double) l.get(3));
			stats.put("MIN", (Double) l.get(5));
			stats.put("MAX", (Double) l.get(4));
			record.put((String) l.get(1), (String) l.get(0));
			map.put(record, stats);
			
		}
		return map;
	}
	
	private Map<String,Map<String,Double>> createStatsToolsMap(){
		final List<List<Object>> statsList = this.repository.getStatsTools();
		final Map<String,Map<String,Double>> map = new HashMap<String,Map<String,Double>>();
		for (final List<Object>l:statsList) {
			final Map<String, Double> stats = new HashMap<String,Double>();
			stats.put("AVG", (Double) l.get(1));
			stats.put("STDEV", (Double) l.get(2));
			stats.put("MIN", (Double) l.get(4));
			stats.put("MAX", (Double) l.get(3));
			map.put((String) l.get(0), stats);
			
		}
		return map;
	}

	
	private Map<PatronageStatus,Map<String,Double>> createStatsPatronageMap(){
		final List<List<Object>> statsList = this.repository.getStatsPatronages();
		final Map<PatronageStatus,Map<String,Double>> map = new HashMap<PatronageStatus,Map<String,Double>>();
		for (final List<Object> l:statsList) {
			final Map<String, Double> stats = new HashMap<String,Double>();
			stats.put("TOTAL", ((Long) l.get(5)).doubleValue());
			stats.put("AVG", (Double) l.get(1));
			stats.put("STDEV", (Double) l.get(2));
			stats.put("MIN", (Double) l.get(4));
			stats.put("MAX", (Double) l.get(3));
			map.put((PatronageStatus) l.get(0), stats);
			
		}
		return map;
	}
}
