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

package acme.features.inventor.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.item.Item;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorItemController extends AbstractController<Inventor, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorItemListOwnComponentsService	listOwnComponentsService;
	
	@Autowired
	protected InventorItemListOwnToolsService	listOwnToolsService;

	@Autowired
	protected InventorItemShowService	showService;
	
	
	@Autowired
	protected InventorComponentUpdateService	updateService;
	
	@Autowired
	protected InventorComponentCreateService	createService;

	
	@Autowired
	protected InventorComponentDeleteService	deleteService;
	
	@Autowired
	protected InventorComponentPublishService	publishService;
	
	@Autowired
	protected InventorToolCreateService 		createToolService;
	
	@Autowired
	protected InventorToolPublishService 		publishToolService;
	
	@Autowired
	protected InventorToolUpdateService 		updateToolService;
	
	@Autowired
	protected InventorToolDeleteService 		deleteToolService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-own-components", "list" ,this.listOwnComponentsService);
		super.addCommand("list-own-tools", "list" ,this.listOwnToolsService);
		super.addCommand("show", this.showService);
		
		super.addCommand("update-tool", "update", this.updateToolService);
		super.addCommand("delete-tool", "delete", this.deleteToolService);
		super.addCommand("create-tool", "create", this.createToolService);
		super.addCommand("publish-tool", "update", this.publishToolService);
		
		super.addCommand("update-component", "update", this.updateService);
		super.addCommand("delete-component", "delete", this.deleteService);
		super.addCommand("create-component", "create", this.createService);
		super.addCommand("publish-component", "update" ,this.publishService);
	}

}
