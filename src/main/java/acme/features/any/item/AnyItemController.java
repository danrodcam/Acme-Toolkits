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

package acme.features.any.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.item.Item;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;

@Controller
public class AnyItemController extends AbstractController<Any, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyItemListPublishedComponentsService	listPublishedComponentsService;
	
	@Autowired
	protected AnyItemListPublishedToolsService	listPublishedToolsService;
	
	@Autowired
	protected AnyItemListComponentsToolkitService listComponentsToolkitService;
	
	@Autowired
	protected AnyItemListToolsToolkitService anyItemListToolsToolkitService;

	@Autowired
	protected AnyItemShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-published-components", "list" ,this.listPublishedComponentsService);
		super.addCommand("list-published-tools", "list" ,this.listPublishedToolsService);
		super.addCommand("list-components-toolkits","list", this.listComponentsToolkitService);
		super.addCommand("list-tools-toolkits","list", this.anyItemListToolsToolkitService);
		super.addCommand("show", this.showService);
	}

}
