/*
 * AnyJobRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.userAccount;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface AnyUserAccountRepository extends AbstractRepository {

	@Query("SELECT DISTINCT ua FROM UserAccount ua JOIN FETCH ua.roles r WHERE ua.enabled = true AND Administrator NOT IN ( SELECT type(r1) FROM UserAccount ua2 JOIN ua2.roles r1 WHERE ua.id=ua2.id)")
	Collection<UserAccount> findAllUserAccounts();
	
	@Query("SELECT ua FROM UserAccount ua WHERE ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("SELECT inventor From Inventor inventor")
	List<Inventor> findAllInventors();

}
