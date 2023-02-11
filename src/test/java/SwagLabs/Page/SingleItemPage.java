package SwagLabs.Page;

import SwagLabs.Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SingleItemPage extends BaseTest {

    public SingleItemPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy (css = ".inventory_details_name.large_size")
    public WebElement item1;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    public WebElement addToCardButton;

    @FindBy(className = "shopping_cart_badge")
    public WebElement cartAfterAddingItem;

    @FindBy (id = "remove-sauce-labs-backpack")
    public WebElement removeButton;



    public void clickOnAddToCartButton(){
        addToCardButton.click();
    }

    public void clickOnRemoveButton(){
        removeButton.click();
    }
}
