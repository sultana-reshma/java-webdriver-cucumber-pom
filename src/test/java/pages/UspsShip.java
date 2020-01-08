package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UspsShip extends Page {

    @FindBy(id = "btn-submit")
    private WebElement signIn;

    @FindBy(id = "sign-up-button")
    private WebElement signUp;

    @FindBy(id = "error-username")
    private WebElement errorUsername;

    @FindBy(id = "error-password")
    private WebElement errorPassword;

    public void clickSignIn() {
        signIn.click();
    }

    public boolean isSignInErrorsDisplayed() {
        try {
            waitForVisible(errorUsername);
            return errorPassword.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isSignUpPossible() {
        return signUp.isDisplayed() && signUp.isEnabled();
    }

}
