package acme.testing.inventor.deta;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class InventorDetaDeleteTest extends TestHarness {

	
	@Test
	@Order(10)
	public void positiveTest() {
		super.signIn("inventor2", "inventor2");

		super.clickOnMenu("Inventor", "My Detas");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.clickOnSubmit("Delete Deta");

		super.checkListingExists();
		super.checkListingEmpty();
		

		super.signOut();
	}
	// Ancillary methods ------------------------------------------------------
}

