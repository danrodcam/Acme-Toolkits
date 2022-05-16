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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Amount;
import acme.entities.toolkit.Toolkit;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorToolkitRepository extends AbstractRepository {

	@Query("select tk from Toolkit tk where tk.id = :id")
	Toolkit findOneToolkitById(int id);
	
	@Query("select tk from Toolkit tk where tk.inventor.id = :inventorId")
	Collection<Toolkit> findManyToolkitsByInventorId(int inventorId);
	
	@Query("select amount from Amount amount where amount.toolkit.id = :masterId")
    Collection<Amount> findAmountsByToolkit(int masterId);
	
	@Query("select tk from Toolkit tk where tk.code = :code")
	Toolkit findOneToolkitByCode(String code);
	
	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorById(int id);
	
	@Query("select sum(a.item.retailPrice.amount*a.units) from Amount a where a.toolkit.id = :toolkitId")
	Double computePriceByToolkitId(int toolkitId);

}
