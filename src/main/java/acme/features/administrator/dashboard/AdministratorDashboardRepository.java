/*
 * AdministratorDashboardRepository.java
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

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronages.PatronageStatus;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Item i where i.type = 'COMPONENT'")
	Integer numberOfComponents();
	
	@Query("select avg(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency,i.technology")
	List<Double> averagePriceOfComponents();
	
	@Query("select stddev(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency,i.technology")
	List<Double> stdPriceOfComponents();
	
	@Query("select max(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency,i.technology")
	List<Double> maxPriceOfComponents();
	
	@Query("select min(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency,i.technology")
	List<Double> minPriceOfComponents();
	
	@Query("select i.technology from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency,i.technology")
	List<String> technologyComponents();
	
	@Query("select i.retailPrice.currency from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency,i.technology")
	List<String> currencyComponents();
	
	@Query("select count(i) from Item i where i.type = 'TOOL'")
	Integer numberOfTools();
	
	@Query("select avg(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Double> averagePriceOfTools();
	
	@Query("select stddev(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Double> stdPriceOfTools();
	
	@Query("select max(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Double> maxPriceOfTools();
	
	@Query("select min(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Double> minPriceOfTools();
	
	@Query("select i.retailPrice.currency from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<String> currencyTools();
	
	@Query("select  count(p) from Patronage p group by p.status")
	List<Integer> countPatronages();
	
	@Query("select  avg(p.budget.amount) from Patronage p group by p.status")
	List<Double> averageBudgetPatronages();
	
	@Query("select  stddev(p.budget.amount) from Patronage p group by p.status")
	List<Double> stdBudgetPatronages();
	
	@Query("select  min(p.budget.amount) from Patronage p group by p.status")
	List<Double> minBudgetPatronages();
	
	@Query("select  max(p.budget.amount) from Patronage p group by p.status")
	List<Double> maxBudgetPatronages();
	
	@Query("select  p.status from Patronage p group by p.status")
	List<PatronageStatus> statusPatronages();
}
