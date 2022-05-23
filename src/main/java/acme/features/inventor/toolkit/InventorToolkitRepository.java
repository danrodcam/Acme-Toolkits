/*
 * AuthenticatedProviderRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Amount;
import acme.entities.toolkit.Toolkit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorToolkitRepository extends AbstractRepository {

	@Query("select tk from Toolkit tk where tk.id = :id")
	Toolkit findOneToolkitById(int id);
	
	@Query("select tk from Toolkit tk where tk.draftMode = false and tk.inventor.id = :inventorId")
	Collection<Toolkit> findManyToolkitsByInventorId(int inventorId);
	
	@Query("select amount from Amount amount where amount.toolkit.id = :masterId")
    Collection<Amount> findItemsByToolkit(int masterId);
	
	@Query("select sum(a.item.retailPrice.amount*a.units), a.item.retailPrice.currency from Amount a where a.toolkit.id = :toolkitId group by a.item.retailPrice.currency")
	List<List<Object>> getPricesByToolkitId(int toolkitId);

}
