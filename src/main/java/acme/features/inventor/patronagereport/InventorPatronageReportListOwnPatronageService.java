package acme.features.inventor.patronagereport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronageReport.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportListOwnPatronageService implements AbstractShowService<Inventor, PatronageReport> {
	
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

		public Collection<PatronageReport> findMany(final Request<PatronageReport> request) {
			assert request != null;
			
			Collection<PatronageReport> result;
			int patronageReportId;
			
			patronageReportId = request.getPrincipal().getActiveRoleId();
			result = this.repository.findManyPatronageReportByInventorId(patronageReportId);
			return result;
		}

		@Override
		public PatronageReport findOne(final Request<PatronageReport> request) {
			return null;
		}

}
