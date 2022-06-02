package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness {

	

	@Test
	@Order(10)
	public void positiveTest() {
		super.signIn("inventor2", "inventor2");

		super.clickOnMenu("Inventor", "List Chimpum");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.checkListingEmpty();
		

		super.signOut();
	}


	// Ancillary methods ------------------------------------------------------

}