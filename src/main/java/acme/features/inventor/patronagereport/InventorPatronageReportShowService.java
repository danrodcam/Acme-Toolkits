package acme.features.inventor.patronagereport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronageReport.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportShowService implements AbstractShowService<Inventor, PatronageReport> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected InventorPatronageReportRepository repository;

		// AbstractCreateService<Authenticated, Provider> interface ---------------

		@Override
		public boolean authorise(final Request<PatronageReport> request) {
			assert request != null;

			boolean result;
			int itemId;
			PatronageReport item;
			int principalId; 
			
			principalId = request.getPrincipal().getAccountId();
			itemId = request.getModel().getInteger("id");
			item = this.repository.findOnePatronageReportById(itemId);
			result = item.getPatronage().getInventor().getUserAccount().getId()==principalId; 

			return result;
		}

		@Override
		public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "creationMoment", "automaticSequenceNumber", "memorandum", "link");
		}

		@Override
		public PatronageReport findOne(final Request<PatronageReport> request) {
			assert request != null;
			
			PatronageReport result;
			int id;
			
			id = request.getModel().getInteger("id");
			result = this.repository.findOnePatronageReportById(id);
			return result;
		}

}
