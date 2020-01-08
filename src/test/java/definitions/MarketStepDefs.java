package definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.QuoteForm;
import pages.QuoteResult;
import pages.UspsHome;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketStepDefs {
    @Given("I navigate to {string} page")
    public void iNavigateToPage(String page) {
        switch (page) {
            case "quote":
                new QuoteForm().open();
                break;
            case "usps":
                new UspsHome().open();
                break;
            default:
                throw new RuntimeException("Unknown page: " + page);
        }
    }

    @When("I fill out {string} fields")
    public void iFillOutFields(String scope) {
        QuoteForm form = new QuoteForm();
        form.fillUsername("skryabin");
        form.fillEmail("slava@skryabin.com");
        form.fillPassword("welcome");
        form.fillConfirmPassword("welcome");
        form.fillName("Slava", "Skryabin");
        form.acceptPrivacy();
        form.submitForm();
    }

    @Then("I verify {string} fields")
    public void iVerifyFields(String scope) {
        QuoteResult result = new QuoteResult();
        String resultText = result.getResult();

        assertThat(resultText).contains("skryabin");
        assertThat(resultText).contains("slava@skryabin.com");
        assertThat(resultText).contains("Slava Skryabin");
        assertThat(resultText).doesNotContain("welcome");
        assertThat(result.getPrivacy()).isEqualTo("true");
        assertThat(result.getPassword()).isEqualTo("[entered]");
        assertThat(result.areAllResultElementsBold()).isTrue();
    }
}
