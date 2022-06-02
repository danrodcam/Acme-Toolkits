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

package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorChimpumRepository extends AbstractRepository {

	@Query("select item from Item item where item.inventor.id = :inventorId")
	Collection<Item> findManyItemByInventor(int inventorId);
	
	@Query("select item from Item item where item.chimpum.id = :chimpumId")
	Item findOneItemByChimpum(int chimpumId);
	
	@Query("select chimpum from Chimpum chimpum where chimpum.id = :chimpumId")
	Chimpum findOneChimpumById(int chimpumId);
	
	@Query("select item from Item item where item.id = :itemId")
	Item findOneItemById(int itemId);
	
	@Query("select chimpum from Chimpum chimpum where chimpum.code = :code")
	Chimpum findOneChimpumByCode(String code);
	
}
