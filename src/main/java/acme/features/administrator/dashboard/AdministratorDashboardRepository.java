package acme.features.administrator.dashboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(e) from Items e where e.type==Component")
	Double numberOfComponents();
	
	@Query("select avg(e.retailPrice) from Items e where e.type==Component group by e.technology")
	Double averageComponents();
	
	@Query("select stddev(e.retailPrice) from Items e where e.type==Component group by e.technology")
	Double desviationComponents();
	
	@Query("select max(e.retailPrice) from Items e where e.type==Component group by e.technology")
	Double maxComponents();
	
	@Query("select min(e.retailPrice) from Items e where e.type==Component group by e.technology")
	Double minComponents();
	
	
	
	
	@Query("select count(e) from Tools e where e.type==Tool")
	Double numberOfTools();
	
	@Query("select avg(e.retailPrice) from Items e where e.type==Tool")
	Double averageTools();
	
	@Query("select stddev(e.retailPrice) from Items e where e.type==Tool")
	Double desviationTools();
	
	@Query("select max(e.retailPrice) from Items e where e.type==Tool")
	Double maxTools();
	
	@Query("select min(e.retailPrice) from Items e where e.type==Tool")
	Double minTools();
	
	
	
	
	@Query("select count(e) from Patronage e group by e.status ")
	Double numberOfPatronages();
	
	@Query("select avg(e.budget) from Patronage e group by e.status")
	Double averagePatronages();
	
	@Query("select stddev(e.budget) from Patronage e group by e.status")
	Double desviationPatronages();
	
	@Query("select max(e.budget) from Patronage e group by e.status")
	Double maxPatronages();
	
	@Query("select min(e.budget) from Patronage e group by e.status")
	Double minPatronages();
	
	
	
	
	

	
}
