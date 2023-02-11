package SwagLabs.Page;

import SwagLabs.Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage extends BaseTest {

    public CheckOutPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy (id = "first-name")
    public WebElement firstNameField;

    @FindBy (id = "last-name")
    public WebElement lastNameField;

    @FindBy(id = "postal-code")
    public WebElement zipField;

    @FindBy (id = "continue")
    public WebElement continueButton;

    @FindBy(id ="cancel")
    public WebElement cancelButton;

    @FindBy(className = "header_secondary_container")
    public WebElement checkOutTitle;

    @FindBy(id = "finish")
    public WebElement finishButton;

    @FindBy(id = "cancel")
    public WebElement cancelShoppingButton;

    @FindBy(className = "complete-header")
    public WebElement compliteMessage;

    @FindBy(id = "back-to-products")
    public WebElement backToProducts;

    public void inputFirstName(String firstName){
        firstNameField.sendKeys(firstName);
    }

    public void inputLastName(String lastName){
        lastNameField.sendKeys(lastName);
    }
    public void inputZip(String zipCode){
        zipField.sendKeys(zipCode);
    }

    public void clickOnContinueButton(){
        continueButton.click();
    }

    public void clickOnFinishButton(){
        finishButton.click();
    }

    public void clickToCancelShopping(){
        cancelShoppingButton.click();
    }
}
