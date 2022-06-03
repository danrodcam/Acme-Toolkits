package acme.testing.inventor.deta;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorDetaUpdateTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String creationMoment, final String summary, final String subject, final String initialDate, final String finalDate, final String allowance, final String moreInfo) {
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "My Detas");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("allowance", allowance);
		super.clickOnSubmit("Edit Deta");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, summary);
		super.checkColumnHasValue(recordIndex, 2, subject);
		super.checkColumnHasValue(recordIndex, 3, allowance);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);
		super.checkInputBoxHasValue("allowance", allowance);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String code, final String creationMoment, final String summary, final String subject, final String initialDate, final String finalDate, final String allowance, final String moreInfo) {
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "My Detas");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("allowance", allowance);
		super.clickOnSubmit("Edit Deta");

		super.checkErrorsExist();

		super.signOut();
	}
	
}
