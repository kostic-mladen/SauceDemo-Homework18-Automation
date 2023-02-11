package SwagLabs.Page;

import SwagLabs.Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class ProductsPage extends BaseTest {

    public ProductsPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "app_logo")
    public WebElement logoProducts;

    @FindBy(id = "shopping_cart_container")
    public WebElement shoppingCart;

    @FindBy(className = "bm-burger-button")
    public WebElement reactMenu;

    @FindBy(id = "logout_sidebar_link")
    public WebElement LogoutButton;

    @FindBy(className = "title")
    public WebElement title;

    @FindBy(linkText = "Twitter")
    public WebElement twitterButton;

    @FindBy(linkText = "Facebook")
    public WebElement facebookButton;

    @FindBy(linkText = "LinkedIn")
    public WebElement linkedIn;

    @FindBy(id = "about_sidebar_link")
    public WebElement aboutButton;

    @FindBy(className = "inventory_item_name")
    public WebElement item;

    @FindBy(className = "inventory_item_price")
    public WebElement itemPrice;

    @FindBy (className = "header_secondary_container")
    public WebElement titleCart;

    @FindBy(id = "continue-shopping")
    public WebElement continueShopping;

    @FindBy(id = "checkout")
    public WebElement checkOutButton;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    public WebElement addToCartSauceLabsBackpack;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    public WebElement addToCartSauceLabsBikeLight;

    @FindBy(id = "add-to-cart-sauce-labs-bolt-t-shirt")
    public WebElement addToCartSauceLabsBoltTShirt;

    @FindBy(id = "add-to-cart-sauce-labs-onesie")
    public WebElement addToCartSauceLabsOnesie;

    @FindBy (className = "footer_copy")
    public WebElement footer;


    public void clickOnReactMenu() {
        reactMenu.click();
    }

    public void clickOnLogoutButton() {
        LogoutButton.click();
    }

    public void clickOnTwitterButton() {
        twitterButton.click();
    }

    public void clickOnFacebookButton() {
        facebookButton.click();
    }

    public void clickOnLinkedInButton() {
        linkedIn.click();
    }

    public void clickOnAboutButton() {
        aboutButton.click();
    }

    public void clickOnItem(String name) {
        item.click();
    }

    public void clickOnCart(){
        shoppingCart.click();
    }

    public void clickOnContinueShoppingButton(){
        continueShopping.click();
    }
    public void clickOnCheckOutButton(){
        checkOutButton.click();
    }
}