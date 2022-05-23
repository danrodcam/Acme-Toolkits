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

package acme.features.inventor.amount;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.item.Amount;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorAmountController extends AbstractController<Inventor, Amount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorAmountComponentCreateService	createComponentService;

	@Autowired
	protected InventorAmountToolCreateService	createToolService;
	
	@Autowired
	protected InventorAmountListComponentsService	listAmountComponentsService;
	
	@Autowired
	protected InventorAmountListToolsService	listAmountToolsService;
	
	@Autowired
	protected InventorAmountDeleteService	deleteService;
	
	@Autowired
	protected InventorAmountShowService	showService;
	
	@Autowired
	protected InventorAmountUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("create-component","create", this.createComponentService);
		super.addCommand("create-tool","create", this.createToolService);
		super.addCommand("list-amount-components", "list" ,this.listAmountComponentsService);
		super.addCommand("list-amount-tools", "list" ,this.listAmountToolsService);
		super.addCommand("show" ,this.showService);
		super.addCommand("delete" ,this.deleteService);
		super.addCommand("update" ,this.updateService);
	}

}
