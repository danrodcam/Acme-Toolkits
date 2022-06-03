package acme.testing.inventor.deta;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorDetaListTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(5)
	public void positiveTest(final int recordIndex, final String code, final String creationMoment, final String summary, final String subject, final String initialDate, final String finalDate,
			final String allowance, final String moreInfo) {
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "My Detas");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, summary);
		super.checkColumnHasValue(recordIndex, 2, subject);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);
		super.checkInputBoxHasValue("allowance", allowance);
		
		super.signOut();
	}

	// Ancillary methods ------------------------------------------------------

}
