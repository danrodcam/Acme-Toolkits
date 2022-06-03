package acme.features.inventor.priripi;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.piripi.Piripi;
import acme.entities.item.Item;
import acme.framework.repositories.AbstractRepository;


@Repository
public interface InventorPiripiRepository extends AbstractRepository {
	
	
	@Query("select item.piripi from Item item where item.piripi.id = :id")
	Piripi findOnePiripiById(int id);
	
	@Query("select item from Item item where item.piripi.id = :id")
	Item findOneItemByPiripiId(int id);
	
	@Query("select item.piripi from Item item where item.inventor.id = :id and item.type = acme.entities.item.ItemType.COMPONENT")
	List<Piripi> findManyPiripiByInventorId(int id);
	
	

}
