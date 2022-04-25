package acme.features.patron.patronageReport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronageReport.PatronageReport;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface PatronPatronageReportRepository extends AbstractRepository {
	
	@Query("select report from PatronageReport report where report.id = :id")
	PatronageReport findOnePatronageReportById(int id);
	
	@Query("select report from PatronageReport report where report.patronage.patron.id = :patronId")
	Collection<PatronageReport> findManyPatronageReportByPatronId(int patronId);

}
