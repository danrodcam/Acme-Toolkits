package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness{

    @ParameterizedTest
    @CsvFileSource(resources = "/inventor/chimpum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(11)
    
    public void positiveTest(final int recordIndex, final String title, final String description, final String initialDate, 
            final String finalDate, final String budget) {
        super.signIn("inventor4", "inventor4");

        super.clickOnMenu("Inventor", "My Components");
        super.checkListingExists();

        super.clickOnListingRecord(recordIndex);
        super.clickOnButton("Create Chimpum");

        super.fillInputBoxIn("title", title);
        super.fillInputBoxIn("description", description);
        super.fillInputBoxIn("initialDate", initialDate);
        super.fillInputBoxIn("finalDate", finalDate);
        super.fillInputBoxIn("budget", budget);

        super.clickOnSubmit("Create");
        
        super.clickOnButton("Show Chimpum");
        
        super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);
		super.checkInputBoxHasValue("budget", budget);
		
        
        super.signOut();
    }
    
    
    @ParameterizedTest
    @CsvFileSource(resources = "/inventor/chimpum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    
    public void negativeTest(final int recordIndex, final String title, final String description, final String initialDate, 
        final String finalDate, final String budget) {
    
    	super.signIn("inventor4", "inventor4");

    	super.clickOnMenu("Inventor", "My Components");
    	super.checkListingExists();

    	super.clickOnListingRecord(recordIndex);
    	super.clickOnButton("Create Chimpum");

    	super.fillInputBoxIn("title", title);
    	super.fillInputBoxIn("description", description);
    	super.fillInputBoxIn("initialDate", initialDate);
    	super.fillInputBoxIn("finalDate", finalDate);
    	super.fillInputBoxIn("budget", budget);

    	super.clickOnSubmit("Create");
    	
    	super.checkErrorsExist();
    	
    }
    
    
}