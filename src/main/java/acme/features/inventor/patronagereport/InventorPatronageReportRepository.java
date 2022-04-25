package acme.features.inventor.patronagereport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronageReport.PatronageReport;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorPatronageReportRepository extends AbstractRepository {
	
	@Query("select report from PatronageReport report where report.id = :id")
	PatronageReport findOnePatronageReportById(int id);
	
	@Query("select report from PatronageReport report where report.patronage.inventor.id = :inventorId")
	Collection<PatronageReport> findManyPatronageReportByInventorId(int inventorId);

}
