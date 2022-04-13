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
		

		return result;
		
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "statsComponents", "numberOfComponents", "statsPatronages", "numberOfTools","statsTools");
	}
	
	private Map<Record,Map<String,Double>> createStatsComponentsMap(){
		final List<Double> avgItms = this.repository.averagePriceOfComponents();
		final List<Double> stdItms = this.repository.stdPriceOfComponents();
		final List<Double> maxItms = this.repository.maxPriceOfComponents();
		final List<Double> minItms = this.repository.minPriceOfComponents();
		final List<String> techItms = this.repository.technologyComponents();
		final List<String> curItms = this.repository.currencyComponents();
		final Integer size = avgItms.size();
		final Map<Record,Map<String,Double>> map = new HashMap<Record,Map<String,Double>>();
		for (int i=0;i<size;i++) {
			final Record record = new Record();
			final Map<String, Double> stats = new HashMap<String,Double>();
			stats.put("AVG", avgItms.get(i));
			stats.put("STDEV", stdItms.get(i));
			stats.put("MIN", minItms.get(i));
			stats.put("MAX", maxItms.get(i));
			final String technology = techItms.get(i);
			final String currency = curItms.get(i);
			record.put(technology, currency);
			map.put(record, stats);
			
		}
		return map;
	}
	
	private Map<String,Map<String,Double>> createStatsToolsMap(){
		final List<Double> avgItms = this.repository.averagePriceOfTools();
		final List<Double> stdItms = this.repository.stdPriceOfTools();
		final List<Double> maxItms = this.repository.maxPriceOfTools();
		final List<Double> minItms = this.repository.minPriceOfTools();
		final List<String> curItms = this.repository.currencyTools();
		final Integer size = avgItms.size();
		final Map<String,Map<String,Double>> map = new HashMap<String,Map<String,Double>>();
		for (int i=0;i<size;i++) {
			final Map<String, Double> stats = new HashMap<String,Double>();
			stats.put("AVG", avgItms.get(i));
			stats.put("STDEV", stdItms.get(i));
			stats.put("MIN", minItms.get(i));
			stats.put("MAX", maxItms.get(i));
			map.put(curItms.get(i), stats);
			
		}
		return map;
	}

	private Map<PatronageStatus,Map<String,Double>> createStatsPatronageMap(){
		final List<Double> avgItms = this.repository.averageBudgetPatronages();
		final List<Double> stdItms = this.repository.stdBudgetPatronages();
		final List<Double> maxItms = this.repository.maxBudgetPatronages();
		final List<Double> minItms = this.repository.minBudgetPatronages();
		final List<Integer> countItms = this.repository.countPatronages();
		final List<PatronageStatus> statusItms = this.repository.statusPatronages();
		final Integer size = avgItms.size();
		final Map<PatronageStatus,Map<String,Double>> map = new HashMap<PatronageStatus,Map<String,Double>>();
		for (int i=0;i<size;i++) {
			final Map<String, Double> stats = new HashMap<String,Double>();
			stats.put("TOTAL", countItms.get(i).doubleValue());
			stats.put("AVG", avgItms.get(i));
			stats.put("STDEV", stdItms.get(i));
			stats.put("MIN", minItms.get(i));
			stats.put("MAX", maxItms.get(i));
			map.put(statusItms.get(i), stats);
			
		}
		return map;
	}
}
