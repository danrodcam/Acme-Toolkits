package acme.testing.inventor.toolkit;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class InventorToolkitListOwnTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/toolkit/list-own.csv", encoding = "utf-8", numLinesToSkip = 1 )
	@Order(10)
	public void positiveCase(final int recordIndex, final String code, final String title, final String description, final String assemblyNotes, final String link, final String totalPrice, final String firstToolCode, final String firstComponentCode) {
		
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Toolkits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, description);
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("assemblyNotes", assemblyNotes);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("totalPrice", totalPrice);
		
		super.checkButtonExists("Tools");
		super.clickOnButton("Tools");
		super.sortListing(0, "asc");
		super.checkListingExists();
		if (!firstToolCode.equals("null")) {
			super.checkColumnHasValue(0, 1, firstToolCode);
		} else {
			super.checkListingEmpty();
		}
		
		super.clickOnButton("Return");
		
		super.checkButtonExists("Components");
		super.clickOnButton("Components");
		super.sortListing(0, "asc");
		super.checkListingExists();
		if (!firstComponentCode.equals("null")) {
			super.checkColumnHasValue(0, 1, firstComponentCode);
		} else {
			super.checkListingEmpty();
		}
		super.clickOnButton("Return");
		
		super.signOut();
	}
	
	/*
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/toolkit/list-own.csv", encoding = "utf-8", numLinesToSkip = 1 )
	@Order(20)
	public void negativeCase(final int recordIndex, final String code, final String title, final String description, final String assemblyNotes, final String link, final String totalPrice) {
		
		super.signIn("inventor4", "inventor4");
		
		super.navigate("/inventor/toolkit/show"," id=118");
		super.checkFormExists();
		
		super.signOut();
	}*/
}
