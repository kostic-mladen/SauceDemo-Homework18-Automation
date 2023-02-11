package SwagLabs.Test;

import SwagLabs.Base.BaseTest;
import SwagLabs.Page.CheckOutPage;
import SwagLabs.Page.HomePage;
import SwagLabs.Page.ProductsPage;
import SwagLabs.Page.SingleItemPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.plaf.TableHeaderUI;
import java.time.Duration;

public class AddToCardTest extends BaseTest {

    @BeforeMethod
    public void pageProductsSetUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(homeUrl);
        homePage = new HomePage();
        productsPage = new ProductsPage();
        singleItemPage = new SingleItemPage();
        checkOutPage = new CheckOutPage();
        footerTest = new FooterTest();

    }

    public void standardUserProductsPageLogin() {
        String validUsername = excelReader.getStringData("Login", 1, 0);
        String validPassword = excelReader.getStringData("Login", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
    }

    @Test(priority = 10)
    public void verifyThatStandardUserCanGoToProductsPage() throws InterruptedException {
        standardUserProductsPageLogin();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 1));
        Assert.assertTrue(productsPage.logoProducts.isDisplayed());
        Assert.assertTrue(productsPage.reactMenu.isDisplayed());
        Assert.assertTrue(productsPage.shoppingCart.isDisplayed());
        Assert.assertTrue(productsPage.title.isDisplayed());
    }

    @Test(priority = 20)
    public void verifyUserCanClickOnItem() throws InterruptedException {
        standardUserProductsPageLogin();
        productsPage.clickOnItem("Sauce Labs Backpack");
        waitForVisibility(singleItemPage.addToCardButton);
        Assert.assertTrue(singleItemPage.addToCardButton.isDisplayed());
        Assert.assertNotEquals(driver.getCurrentUrl(), homeUrl);
    }

    @Test(priority = 30)
    public void verifyThatStandardUserCanClickOnAddToCartButton() throws InterruptedException {
        standardUserProductsPageLogin();
        productsPage.clickOnItem("Sauce Labs Backpack");
        waitForVisibility(singleItemPage.addToCardButton);
        singleItemPage.clickOnAddToCartButton();
        String cart = singleItemPage.cartAfterAddingItem.getText();
        Assert.assertEquals(cart, "1");
    }

    @Test(priority = 40)
    public void verifyThatStandardUserCanRemoveItemFromCart() {
        standardUserProductsPageLogin();
        productsPage.clickOnItem("Sauce Labs Backpack");
        waitForVisibility(singleItemPage.addToCardButton);
        singleItemPage.clickOnAddToCartButton();
        singleItemPage.clickOnRemoveButton();
        Assert.assertTrue(singleItemPage.addToCardButton.isDisplayed());
    }

    @Test(priority = 50)
    public void verifyThatUserCanGoToCart() {
        standardUserProductsPageLogin();
        productsPage.clickOnCart();
        waitForVisibility(productsPage.titleCart);
        String expectedTitle = "YOUR CART";
        Assert.assertEquals(productsPage.titleCart.getText(), expectedTitle);
        Assert.assertTrue(productsPage.continueShopping.isDisplayed());
        Assert.assertTrue(productsPage.checkOutButton.isDisplayed());

    }

    @Test(priority = 60)
    public void verifyThatUserCanClickOnContinueShoppingButton() {
        standardUserProductsPageLogin();
        productsPage.clickOnCart();
        waitForVisibility(productsPage.titleCart);
        productsPage.clickOnContinueShoppingButton();
        Assert.assertTrue(productsPage.logoProducts.isDisplayed());
        Assert.assertTrue(productsPage.reactMenu.isDisplayed());
        Assert.assertTrue(productsPage.shoppingCart.isDisplayed());

    }

    @Test(priority = 70)
    public void verifyThatUserCanAddMoreItemsInCart() throws InterruptedException {
        standardUserProductsPageLogin();
        productsPage.addToCartSauceLabsBackpack.click();
        productsPage.addToCartSauceLabsBoltTShirt.click();
        productsPage.addToCartSauceLabsBikeLight.click();
        String cart = singleItemPage.cartAfterAddingItem.getText();
        Assert.assertEquals(cart, "3");

    }


    @AfterMethod
    public void closeProductsPageBrowser() {
        driver.quit();
    }
}
