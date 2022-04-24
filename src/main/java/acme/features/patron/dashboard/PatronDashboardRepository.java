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

package acme.features.patron.dashboard;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface PatronDashboardRepository extends AbstractRepository {

	
	@Query("select avg(select count(p) from Patronage p where p.patron.id = pa.id) from Patron pa")
	Double numberOfPatronages();
	
	@Query("select p.status,p.budget.currency,avg(p.budget.amount),stddev(p.budget.amount),max(p.budget.amount),min(p.budget.amount),count(p) from Patronage p where p.patron.id = :patronId group by p.status, p.budget.currency")
	List<List<Object>> getStatsPatronages(int patronId);

}
