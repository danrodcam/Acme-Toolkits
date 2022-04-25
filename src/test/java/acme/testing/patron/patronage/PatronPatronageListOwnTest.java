package acme.testing.patron.patronage;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import acme.testing.TestHarness;

public class PatronPatronageListOwnTest extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/list-own.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(17)
	public void positiveTest(final int recordIndex,final String status, final String legalStuff, final String code, final String budget, final String creationMoment,
			final String optionalLink, final String initialDate, final String finalDate, final String inventorName, final String inventorSurname,
			final String inventorUsername, final String inventorCompany, final String inventorStatement, final String inventorLink) {
		
		super.signIn("patron7", "patron7");

		super.clickOnMenu("Patron", "My Patronages");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, status);
		super.checkColumnHasValue(recordIndex, 1, legalStuff);
		super.checkColumnHasValue(recordIndex, 2, budget);
		super.checkColumnHasValue(recordIndex, 3, creationMoment);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("status", status);
		super.checkInputBoxHasValue("legalStuff", legalStuff);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("optionalLink", optionalLink);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);
		super.checkInputBoxHasValue("inventor.userAccount.identity.name", inventorName);
		super.checkInputBoxHasValue("inventor.userAccount.identity.surname", inventorSurname);
		super.checkInputBoxHasValue("inventor.userAccount.username", inventorUsername);
		super.checkInputBoxHasValue("inventor.company", inventorCompany);
		super.checkInputBoxHasValue("inventor.statement", inventorStatement);
		super.checkInputBoxHasValue("inventor.optionalLink", inventorLink);
		
		

		super.signOut();
	}
}
