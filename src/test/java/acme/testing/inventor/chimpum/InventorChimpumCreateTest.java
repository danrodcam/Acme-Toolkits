package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String title, final String description, final String initialDate, 
			final String finalDate, final String budget) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Chimpum");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("budget", budget);
		
		super.clickOnSubmit("Create Chimpum");
		super.clickOnButton("Show Chimpum");
		
		super.checkFormExists();
			super.checkInputBoxHasValue("title", title);
			super.checkInputBoxHasValue("description", description);
			super.checkInputBoxHasValue("initialDate", initialDate);
			super.checkInputBoxHasValue("finalDate", finalDate);
			super.checkInputBoxHasValue("budget", budget);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative-1.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest1(final int recordIndex, final String title, final String description, final String initialDate, 
			final String finalDate, final String budget) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Chimpum");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("budget", budget);
		
		super.clickOnSubmit("Create Chimpum");
		super.checkErrorsExist();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative-2.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest2(final int recordIndex, final String title, final String description, final String initialDate, 
			final String finalDate, final String budget) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Chimpum");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("budget", budget);
		
		super.clickOnSubmit("Create Chimpum");
		super.checkErrorsExist();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative-3.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest3(final int recordIndex, final String title, final String description, final String initialDate, 
			final String finalDate, final String budget) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Chimpum");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("budget", budget);
		
		super.clickOnSubmit("Create Chimpum");
		super.checkErrorsExist();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative-4.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest4(final int recordIndex, final String title, final String description, final String initialDate, 
			final String finalDate, final String budget) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Chimpum");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("budget", budget);
		
		super.clickOnSubmit("Create Chimpum");
		super.checkErrorsExist();
	}
}
