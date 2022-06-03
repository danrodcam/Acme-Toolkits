package acme.testing.inventor.deta;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorDetaCreateTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String summary, final String subject, final String initialDate, 
			final String finalDate, final String allowance) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Deta");
		
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("allowance", allowance);
		
		super.clickOnSubmit("Create Deta");
		super.clickOnButton("Show Deta");
		
		super.checkFormExists();
			super.checkInputBoxHasValue("summary", summary);
			super.checkInputBoxHasValue("subject", subject);
			super.checkInputBoxHasValue("initialDate", initialDate);
			super.checkInputBoxHasValue("finalDate", finalDate);
			super.checkInputBoxHasValue("allowance", allowance);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/create-negative-1.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest1(final int recordIndex, final String summary, final String subject, final String initialDate, 
			final String finalDate, final String allowance) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Deta");
		
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("allowance", allowance);
		
		super.clickOnSubmit("Create Deta");
		super.checkErrorsExist();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/create-negative-2.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest2(final int recordIndex, final String summary, final String subject, final String initialDate, 
			final String finalDate, final String allowance) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Deta");
		
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("allowance", allowance);
		
		super.clickOnSubmit("Create Deta");
		super.checkErrorsExist();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/create-negative-3.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest3(final int recordIndex, final String summary, final String subject, final String initialDate, 
			final String finalDate, final String allowance) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Deta");
		
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("allowance", allowance);
		
		super.clickOnSubmit("Create Deta");
		super.checkErrorsExist();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/deta/create-negative-4.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest4(final int recordIndex, final String summary, final String subject, final String initialDate, 
			final String finalDate, final String allowance) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "My Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(3);
		super.clickOnButton("Create Deta");
		
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("allowance", allowance);
		
		super.clickOnSubmit("Create Deta");
		super.checkErrorsExist();
	}
}
