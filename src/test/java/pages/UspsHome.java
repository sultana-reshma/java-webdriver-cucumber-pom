package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UspsHome extends Page {

    public UspsHome() {
        url = "https://www.usps.com/";
    }

    @FindBy(xpath = "//a[@data-gtm-section='quicktools'][text()='Stamps & Supplies']")
    private WebElement stamps;

    @FindBy(xpath = "//li[contains(@class,'qt-nav')]/a[@role='menuitem']")
    private WebElement quickTools;

    @FindBy(xpath = "//li[not(@class)]//a[contains(@href,'shipping-supplies')]")
    private WebElement boxes;

    public void mouseOverQuickTools() {
        mouseOver(quickTools);
    }

    public void mouseOverMenu(String name) {
        mouseOver(byXpath("//a[text()='" + name + "']"));
    }

    public void clickMenuItem(String name) {
        byXpath("//li/a[text()='" + name + "']").click();
    }

    public void clickStamps() {
        stamps.click();
    }

    public void clickBoxes() {
        boxes.click();
    }

}
