/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.itemToolkitSearch;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkit.Toolkit;
import acme.forms.ItemToolkitSearch;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractPerformService;

@Service
public class AnyItemToolkitSearchPerformService implements AbstractPerformService<Any, ItemToolkitSearch> {

	@Autowired
	protected AnyItemToolkitSearchRepository repository;

	@Override
	public boolean authorise(final Request<ItemToolkitSearch> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<ItemToolkitSearch> request, final ItemToolkitSearch entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "search");
	}

	@Override
	public void unbind(final Request<ItemToolkitSearch> request, final ItemToolkitSearch entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "search" , "toolkits");
	}

	@Override
	public ItemToolkitSearch instantiate(final Request<ItemToolkitSearch> request) {
		assert request != null;

		ItemToolkitSearch result;

		result = new ItemToolkitSearch();

		return result;
	}

	@Override
	public void validate(final Request<ItemToolkitSearch> request, final ItemToolkitSearch entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void perform(final Request<ItemToolkitSearch> request, final ItemToolkitSearch entity, final Errors errors) {
		assert request != null;
		assert entity != null;

		
		String search;
		Collection<Toolkit> toolkits;

		search = request.getModel().getString("search");
		toolkits = this.repository.findManyPublishedToolkitByItem(search);
		entity.setSearch(search);
		entity.setToolkits(toolkits);
		
	}

	
}
