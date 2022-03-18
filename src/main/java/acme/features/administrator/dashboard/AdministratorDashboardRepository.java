package acme.features.administrator.dashboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(*) from Components")
	Double numberOfComponents();

	@Query("select count(*) from Tools")
	Double numberOfTools();

	
}
