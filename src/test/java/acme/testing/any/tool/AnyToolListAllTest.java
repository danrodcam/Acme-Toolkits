package acme.testing.any.tool;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyToolListAllTest extends TestHarness {
	
		// Lifecycle management ---------------------------------------------------

		// Test cases -------------------------------------------------------------

		@ParameterizedTest
		@CsvFileSource(resources = "/any/tool/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(4)
		public void positiveTest(final int recordIndex, final String name, final String code, final String technology, final String description, final String retailPrice,
			final String link, final String type, final String published, final String inventor) {

			super.clickOnMenu("Any", "List Tools");
			super.checkListingExists();
			super.sortListing(0, "asc");
			
			super.checkColumnHasValue(recordIndex, 0, name);
			super.checkColumnHasValue(recordIndex, 1, code);
			super.checkColumnHasValue(recordIndex, 2, technology);
			super.checkColumnHasValue(recordIndex, 3, retailPrice);

			super.clickOnListingRecord(recordIndex);
			super.checkFormExists();
			super.checkInputBoxHasValue("name", name);
			super.checkInputBoxHasValue("code", code);
			super.checkInputBoxHasValue("technology", technology);
			super.checkInputBoxHasValue("description", description);
			super.checkInputBoxHasValue("link", link);
			super.checkInputBoxHasValue("retailPrice", retailPrice);
			
		}

}
