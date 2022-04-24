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

package acme.features.inventor.item;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorItemRepository extends AbstractRepository {

	@Query("select item from Item item where item.id = :id")
	Item findOneComponentById(int id);
	
	@Query("select item from Item item where item.type = acme.entities.item.ItemType.COMPONENT and item.inventor.id = :inventorId")
	Collection<Item> findManyComponentsByInventorId(int inventorId);
	
	@Query("select item from Item item where item.type = acme.entities.item.ItemType.TOOL and item.inventor.id = :inventorId")
	Collection<Item> findManyToolsByInventorId(int inventorId);

}
