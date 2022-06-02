/*
 * EmployerJobCreateTest.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

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
