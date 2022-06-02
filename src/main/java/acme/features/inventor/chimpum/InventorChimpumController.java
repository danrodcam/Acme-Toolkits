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

package acme.features.inventor.chimpum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.chimpum.Chimpum;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorChimpumController extends AbstractController<Inventor, Chimpum> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorChimpumListService	listService;

	@Autowired
	protected InventorChimpumShowService	showService;
	
	@Autowired
	protected InventorChimpumCreateService	createService;
	
	@Autowired
	protected InventorChimpumUpdateService 		updateService;
	
	@Autowired
	protected InventorChimpumDeleteService 		deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-own-chimpum", "list" ,this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
		super.addCommand("create-chimpum", "create", this.createService);
	}
}
