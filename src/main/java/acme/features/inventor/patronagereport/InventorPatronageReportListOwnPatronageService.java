package acme.features.inventor.patronagereport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronageReport.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportListOwnPatronageService implements AbstractListService<Inventor, PatronageReport> {
	
	// Internal state ---------------------------------------------------------

		@Autowired
		protected InventorPatronageReportRepository repository;

		// AbstractCreateService<Authenticated, Provider> interface ---------------

		@Override
		public boolean authorise(final Request<PatronageReport> request) {
			assert request != null;

			return true;
		}

		@Override
		public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "creationMoment", "link");
		}

		@Override
		public Collection<PatronageReport> findMany(final Request<PatronageReport> request) {
			assert request != null;
			
			Collection<PatronageReport> result;
			final int inventorId;
			
			inventorId = request.getPrincipal().getActiveRoleId();
			result = this.repository.findManyPatronageReportByInventorId(inventorId);
			return result;
		}

}
