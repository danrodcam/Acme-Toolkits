package acme.features.patron.patronage;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronages.Patronage;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Patron;

@Repository
public interface PatronPatronageRepository extends AbstractRepository {
	
	@Query("select patronage from Patronage patronage where patronage.id = :id")
	Patronage findOnePatronageById(int id);
	
	@Query("select patronage from Patronage patronage where patronage.patron.id = :patronId")
	Collection<Patronage> findManyPatronageByPatronId(int patronId);
	
	@Query("SELECT patron from Patron patron where patron.id = :patronId")
	Patron findPatronById(int patronId);
	
	@Query("select patronage from Patronage patronage where patronage.code = :code")
	Patronage findOnePatronageByCode(String code);
}
