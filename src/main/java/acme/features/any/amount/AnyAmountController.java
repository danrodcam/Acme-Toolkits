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

package acme.features.any.amount;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.item.Amount;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;


@Controller
public class AnyAmountController extends AbstractController<Any, Amount> {

	// Internal state ---------------------------------------------------------

	
	@Autowired
	protected AnyAmountListComponentsService	listAmountComponentsService;
	
	@Autowired
	protected AnyAmountListToolsService	listAmountToolsService;
	
	@Autowired
	protected AnyAmountShowService	showService;
	

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-amount-components", "list" ,this.listAmountComponentsService);
		super.addCommand("list-amount-tools", "list" ,this.listAmountToolsService);
		super.addCommand("show" ,this.showService);

	}

}
