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

package acme.features.inventor.deta;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.deta.Deta;
import acme.entities.item.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorDetaRepository extends AbstractRepository {

	@Query("select item from Item item where item.inventor.id = :inventorId")
	Collection<Item> findManyItemByInventor(int inventorId);
	
	@Query("select item from Item item where item.deta.id = :detaId")
	Item findOneItemByDeta(int chimpumId);
	
	@Query("select deta from Deta deta where deta.id = :detaId")
	Deta findOneDetaById(int chimpumId);
	
	@Query("select item from Item item where item.id = :itemId")
	Item findOneItemById(int itemId);
	
	@Query("select deta from Deta deta where deta.code = :code")
	Deta findOneDetaByCode(String code);
	
}
