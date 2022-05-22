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

package acme.features.any.amount;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Amount;
import acme.entities.item.Item;
import acme.entities.toolkit.Toolkit;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface AnyAmountRepository extends AbstractRepository {

	@Query("select tk from Toolkit tk where tk.id = :id")
	Toolkit findOneToolkitById(int id);
	
	@Query("select i from Item i where i.id = :id")
	Item findItemById(int id);
	
	@Query("select item from Item item where item.type = acme.entities.item.ItemType.COMPONENT and item.published = true")
	Collection<Item> findManyPublishedComponents();
	
	@Query("select item from Item item where item.type = acme.entities.item.ItemType.TOOL and item.published = true")
	Collection<Item> findManyPublishedTools();
	
	@Query("select amount from Amount amount where amount.toolkit.id = :masterId")
    Collection<Amount> findAmountsByToolkit(int masterId);
	
	@Query("select amount from Amount amount where amount.toolkit.id = :masterId and amount.item.type = acme.entities.item.ItemType.COMPONENT")
    Collection<Amount> findComponentAmountsByToolkit(int masterId);
	
	@Query("select amount from Amount amount where amount.toolkit.id = :masterId and amount.item.type = acme.entities.item.ItemType.TOOL")
    Collection<Amount> findToolAmountsByToolkit(int masterId);
	
	@Query("select amount from Amount amount where amount.toolkit.id = :toolkitId and amount.item.id = :itemId")
	Amount findAmountByToolkitAndItem(int toolkitId, int itemId);
	
	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorById(int id);
	
	@Query("select a from Amount a where a.id = :id")
	Amount findOneAmountById(int id);
	
	@Query("select amount.item from Amount amount where amount.toolkit.id = :masterId and amount.item.type = acme.entities.item.ItemType.TOOL and amount.item.published = true")
	Collection<Item> findManyToolsByToolkit(int masterId);
	
	@Query("select amount.item from Amount amount where amount.toolkit.id = :masterId and amount.item.type = acme.entities.item.ItemType.COMPONENT and amount.item.published = true")
	Collection<Item> findManyComponentsByToolkit(int masterId);
	
}
