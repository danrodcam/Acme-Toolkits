package acme.testing.administrator.systemConfiguration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AdministratorSystemConfigurationTest extends TestHarness {
	
		// Lifecycle management ---------------------------------------------------

		// Test cases -------------------------------------------------------------

		@ParameterizedTest
		@CsvFileSource(resources = "/administrator/systemConfiguration/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void positiveTest(final int recordIndex, final String systemCurrency, final String acceptedCurrency, final String strongSpamTerms, final String weakSpamTerms,
			final String strongSpamThreshold, final String weakSpamThreshold) {
			
			super.signIn("administrator", "administrator");
			
			super.clickOnMenu("Administrator", "System Configuration");
			
			super.checkNotPanicExists();

				
//			super.checkInputBoxHasValue("system-currency", systemCurrency);
//			super.checkInputBoxHasValue("accepted-currency", acceptedCurrency);
//			super.checkInputBoxHasValue("strong-spam-terms", strongSpamTerms);
//			super.checkInputBoxHasValue("weak-spam-terms", weakSpamTerms);
//			super.checkInputBoxHasValue("strong-spam-threshold", strongSpamThreshold);
//			super.checkInputBoxHasValue("weak-spam-threshold", weakSpamThreshold);
				
			

		}

}
