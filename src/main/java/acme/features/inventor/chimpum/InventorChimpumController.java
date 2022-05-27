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
		protected InventorChimpumListService	listChimpumToolsService;
		

		@Autowired
		protected InventorChimpumShowService	showService;
		
		

		// Constructors -----------------------------------------------------------


		@PostConstruct
		protected void initialise() {
			super.addCommand("list-chimpum-tools", "list" ,this.listChimpumToolsService);
			super.addCommand("show", this.showService);
			
			

		}

	}




