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

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Item i where i.type = 'COMPONENT'")
	Integer numberOfComponents();
	
	@Query("select i.retailPrice.currency,i.technology,avg(i.retailPrice.amount),stddev(i.retailPrice.amount),max(i.retailPrice.amount),min(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency,i.technology")
	List<List<Object>> getStatsComponents();
	
	@Query("select count(i) from Item i where i.type = 'TOOL'")
	Integer numberOfTools();
	
	@Query("select i.retailPrice.currency,avg(i.retailPrice.amount),stddev(i.retailPrice.amount),max(i.retailPrice.amount),min(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<List<Object>> getStatsTools();
	
	@Query("select  p.status,avg(p.budget.amount),stddev(p.budget.amount),max(p.budget.amount),min(p.budget.amount),count(p) from Patronage p group by p.status")
	List<List<Object>> getStatsPatronages();
}
