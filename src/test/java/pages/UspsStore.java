package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class UspsStore extends Page {

    @FindBy(xpath="//div[@class='nav-table']//span[text()='Stamps']")
    private WebElement stamps;

    @FindBy(id = "Ns")
    private WebElement sortBy;

    @FindBy(xpath = "(//div[@class='results-product-desc'])[1]")
    private WebElement firstResultItem;

    public void selectStampsCategory() {
        stamps.click();
    }

    public void selectSortBy(String text) {
        new Select(sortBy).selectByVisibleText(text);
    }

    public String getFirstResultItemName() {
        return firstResultItem.getText();
    }
}
