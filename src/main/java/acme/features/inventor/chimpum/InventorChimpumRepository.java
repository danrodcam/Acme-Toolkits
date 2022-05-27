package acme.features.inventor.chimpum;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chimpum.Chimpum;
import acme.framework.repositories.AbstractRepository;


@Repository
public interface InventorChimpumRepository extends AbstractRepository {
	
	
	@Query("select chimpum from Chimpum chimpum where chimpum.id = :id")
	Chimpum findOneChimpumById(int id);
	
	
	@Query("select chimpum from Chimpum chimpum where chimpum.item.id=:id and item.type = acme.entities.item.ItemType.TOOL")
	List<Chimpum> findChimpumByItemId(int id);

}
