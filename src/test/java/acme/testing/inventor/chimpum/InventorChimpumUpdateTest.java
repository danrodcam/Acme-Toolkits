package acme.testing.inventor.chimpum;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class InventorChimpumUpdateTest extends TestHarness {
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(11)
	public void positiveTest(final int recordIndex, final String title, final String description, final String initialDate, 
        final String finalDate, final String budget) {
		
		super.signIn("inventor3", "inventor3");

        super.clickOnMenu("Inventor", "My Tools");
        super.checkListingExists();

        super.clickOnListingRecord(recordIndex);
        super.clickOnButton("Show Chimpum");
		super.checkFormExists();
		super.checkSubmitExists("Update");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialDate", initialDate);
	    super.fillInputBoxIn("finalDate", finalDate);
	    super.fillInputBoxIn("budget", budget);
	    
	    super.clickOnSubmit("Update");
	    
	    super.clickOnButton("Show Chimpum");
		super.checkFormExists();
		
		
		super.signOut();
	}
	
	
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest(final int recordIndex, final String title, final String description, final String initialDate, 
        final String finalDate, final String budget) {
		
		super.signIn("inventor3", "inventor3");

        super.clickOnMenu("Inventor", "My Tools");
        super.checkListingExists();

        super.clickOnListingRecord(recordIndex);
        super.clickOnButton("Show Chimpum");
		super.checkFormExists();
		super.checkSubmitExists("Update");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialDate", initialDate);
	    super.fillInputBoxIn("finalDate", finalDate);
	    super.fillInputBoxIn("budget", budget);
	    
	    super.clickOnSubmit("Update");
	    
	    super.checkErrorsExist();
		
		super.signOut();
	}



}
