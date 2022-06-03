/*
 * AuthenticatedProviderController.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.inventor.deta;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.deta.Deta;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorDetaController extends AbstractController<Inventor, Deta> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorDetaListService	listService;

	@Autowired
	protected InventorDetaShowService	showService;
	
	@Autowired
	protected InventorDetaCreateService	createService;
	
	@Autowired
	protected InventorDetaUpdateService 		updateService;
	
	@Autowired
	protected InventorDetaDeleteService 		deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-own-deta", "list" ,this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
		super.addCommand("create-deta", "create", this.createService);
	}
}
