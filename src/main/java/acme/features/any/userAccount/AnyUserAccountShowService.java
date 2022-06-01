/*
 * AnyJobShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.UserAccount;
import acme.framework.roles.Any;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractShowService;

@Service
public class AnyUserAccountShowService implements AbstractShowService<Any, UserAccount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyUserAccountRepository repository;

	// AbstractShowService<Anonymous, Patron> interface --------------------------

	@Override
	public boolean authorise(final Request<UserAccount> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "identity.name", "identity.surname", "identity.email", "username");
		model.setAttribute("masterId", entity.getId());

		StringBuilder buffer;
		Collection<UserRole> roles;

		roles = entity.getRoles();
		buffer = new StringBuilder();
		int i = 0;
		for (final UserRole role : roles) {
			if(!role.getAuthorityName().equals("Authenticated")) {
				buffer.append(role.getAuthorityName());
				if(i!=roles.size()-2) {
					buffer.append(" | ");
				}
				i= i+1;
			}
		}
		model.setAttribute("roleList", buffer.toString());
	}

	public Collection<UserAccount> findMany(final Request<UserAccount> request) {
		assert request != null;
		
		Collection<UserAccount> result;
		result = this.repository.findAllUserAccounts();
		return result;
	}

	@Override
	public UserAccount findOne(final Request<UserAccount> request) {
		assert request != null;
		
		UserAccount result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneUserAccountById(id);
		result.getRoles().forEach(r -> {
		});
		
		return result;
	}

}
