/*
 * EmployerJobListMineTest.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.any.chirp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyChirpListAllTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/any/chirp/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String creationmoment, final String title, final String author, final String body, final String email) {
		
		
		super.clickOnMenu("Any", "Chirps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		LocalDate deadline;
		deadline = LocalDate.now();
		deadline = deadline.minusMonths(1);
		
		
		final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		final LocalDate localDate = LocalDate.parse(creationmoment, df);
		if(localDate.isAfter(deadline) ) {
			
			
			super.checkColumnHasValue(recordIndex, 0, creationmoment);
			super.checkColumnHasValue(recordIndex, 1, title);
			super.checkColumnHasValue(recordIndex, 2, body);

			super.clickOnListingRecord(recordIndex);
			super.checkFormExists();
			super.checkInputBoxHasValue("title", title);
			super.checkInputBoxHasValue("author", author);
			super.checkInputBoxHasValue("body", body);
			super.checkInputBoxHasValue("email", email);
			
		}
		

	}

	// Ancillary methods ------------------------------------------------------

}
