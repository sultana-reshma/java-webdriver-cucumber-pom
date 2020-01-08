package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.UspsHome;
import pages.UspsShip;
import pages.UspsStore;

import static org.assertj.core.api.Assertions.assertThat;

public class UspsStepDefs {
    @When("I go to usps {string} store")
    public void iGoToUspsStore(String store) {
        switch (store) {
            case "stamps":
                new UspsHome().clickStamps();
                new UspsStore().selectStampsCategory();
            break;
            case "boxes":
                new UspsHome().mouseOverQuickTools();
                new UspsHome().clickBoxes();
                break;
            default:
                throw new RuntimeException("Invalid store: " + store);
        }
    }

    @And("I sort usps results by {string}")
    public void iSortUspsResultsBy(String sortBy) {
        new UspsStore().selectSortBy(sortBy);
    }

    @Then("I verify that usps {string} is cheapest")
    public void iVerifyThatUspsIsCheapest(String item) {
        String actualItem = new UspsStore().getFirstResultItemName();
        assertThat(actualItem).contains(item);
    }

    @When("I go to {string} under {string} menu")
    public void iGoToUnderMenu(String menuItem, String menu) throws InterruptedException {
        new UspsHome().mouseOverMenu(menu);
        new UspsHome().clickMenuItem(menuItem);
    }

    @Then("I verify that {string} is required")
    public void iVerifyThatIsRequired(String arg0) {
        UspsShip ship = new UspsShip();
        ship.clickSignIn();
        assertThat(ship.isSignInErrorsDisplayed()).isTrue();
    }

    @Then("I verify that {string} is possible")
    public void iVerifyThatIsPossible(String arg0) {
        assertThat(new UspsShip().isSignUpPossible()).isTrue();
    }
}
