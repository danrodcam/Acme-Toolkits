package acme.features.inventor.patronagereport;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronageReport.PatronageReport;
import acme.entities.patronages.Patronage;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorPatronageReportRepository extends AbstractRepository {
	
	@Query("select report from PatronageReport report where report.id = :id")
	PatronageReport findOnePatronageReportById(int id);
	
	@Query("select report from PatronageReport report where report.patronage.inventor.id = :inventorId")
	Collection<PatronageReport> findManyPatronageReportByInventorId(int inventorId);
	
	@Query("select patronage from Patronage patronage where patronage.id = :id")
	Patronage findOnePatronageById(int id);
	
	@Query("select report from PatronageReport report where report.patronage.id = :patronageId order by report.automaticSequenceNumber")
	List<PatronageReport> findManyPatronageReportByPatronageId(int patronageId);
	
	

}
