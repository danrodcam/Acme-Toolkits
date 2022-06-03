package acme.features.inventor.priripi;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.piripi.Piripi;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorPiripiController extends AbstractController<Inventor, Piripi> {
	// Internal state ---------------------------------------------------------

		

		@Autowired
		protected InventorPiripiShowService	showService;
		
		@Autowired
		protected InventorPiripiListOwnService     listService;
		
		@Autowired
		protected InventorPiripiCreateService   createService;
		
		@Autowired
		protected InventorPiripiDeleteService   deleteService;
		
		@Autowired
		protected InventorPiripiUpdateService   updateService;
		
		

		// Constructors -----------------------------------------------------------


		@PostConstruct
		protected void initialise() {
			super.addCommand("show", this.showService);
			super.addCommand("list-piripi", "list", this.listService);
			super.addCommand("create-piripi","create", this.createService);
			super.addCommand("delete", this.deleteService);
			super.addCommand("update", this.updateService);
			
			

		}

	}
