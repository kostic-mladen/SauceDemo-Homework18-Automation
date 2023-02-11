package SwagLabs.Test;

import SwagLabs.Base.BaseTest;
import SwagLabs.Base.ExcelReader;
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

import java.io.IOException;
import java.time.Duration;

public class CheckOutTest extends BaseTest {

    @BeforeMethod
    public void pageCheckOutSetUp() throws IOException {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(homeUrl);
        excelReader = new ExcelReader("src/test/java/SwagLabs/TestData.xlsx");
        homePage = new HomePage();
        productsPage = new ProductsPage();
        singleItemPage = new SingleItemPage();
        checkOutPage = new CheckOutPage();
        footerTest = new FooterTest();
    }

    public void logIn() {
        String validUsername = excelReader.getStringData("Login", 1, 0);
        String validPassword = excelReader.getStringData("Login", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
    }

    public void goToCheckOutPage() {
        logIn();
        productsPage.clickOnCart();
    }

    @Test(priority = 10)
    public void verifyThatUserCanClickOnCheckOutButton() {
        goToCheckOutPage();
        productsPage.clickOnCheckOutButton();
        String expectedText = "CHECKOUT: YOUR INFORMATION";
        Assert.assertEquals(productsPage.titleCart.getText(), expectedText);
    }

    @Test(priority = 20)
    public void verifyThatUserCanInputCheckOutInformation() {
        goToCheckOutPage();
        productsPage.clickOnCheckOutButton();
        checkOutPage.firstNameField.sendKeys("Mladen");
        checkOutPage.lastNameField.sendKeys("Kostic");
        checkOutPage.zipField.sendKeys("11000");
        checkOutPage.clickOnContinueButton();
        waitForVisibility(checkOutPage.checkOutTitle);
        String expectedText = "CHECKOUT: OVERVIEW";
        Assert.assertEquals(checkOutPage.checkOutTitle.getText(), expectedText);
        Assert.assertTrue(checkOutPage.cancelButton.isDisplayed());
    }

    @Test(priority = 30)
    public void verifyThatUserCanNotFinishShoppingIfCartIsEmpty() {
        goToCheckOutPage();
        productsPage.clickOnCheckOutButton();
        checkOutPage.firstNameField.sendKeys("Mladen");
        checkOutPage.lastNameField.sendKeys("Kostic");
        checkOutPage.zipField.sendKeys("11000");
        checkOutPage.clickOnContinueButton();
        checkOutPage.clickOnFinishButton();
        String expectedText = "CHECKOUT: INCOMPLETE!";
        Assert.assertEquals(checkOutPage.checkOutTitle.getText(), expectedText);
        String expectedMessage = "Your cart is empty";
        Assert.assertEquals(checkOutPage.compliteMessage.getText(), expectedMessage);

    }

    @Test(priority = 40)
    public void verifyThatUserCanCancelOrder() {
        logIn();
        productsPage.clickOnItem("Sauce Labs Backpack");
        waitForVisibility(singleItemPage.addToCardButton);
        singleItemPage.clickOnAddToCartButton();
        productsPage.clickOnCart();
        productsPage.clickOnCheckOutButton();
        checkOutPage.firstNameField.sendKeys("Mladen");
        checkOutPage.lastNameField.sendKeys("Kostic");
        checkOutPage.zipField.sendKeys("11000");
        checkOutPage.clickOnContinueButton();
        checkOutPage.clickToCancelShopping();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 1));
        Assert.assertTrue(isDisplayed(productsPage.shoppingCart));
        Assert.assertTrue(isDisplayed(productsPage.reactMenu));
        Assert.assertTrue(isDisplayed(productsPage.logoProducts));
    }

    @Test(priority = 50)
    public void verifyThatUserCanFinishOrder() {
        logIn();
        productsPage.clickOnItem("Sauce Labs Backpack");
        waitForVisibility(singleItemPage.addToCardButton);
        singleItemPage.clickOnAddToCartButton();
        productsPage.clickOnCart();
        productsPage.clickOnCheckOutButton();
        checkOutPage.firstNameField.sendKeys("Mladen");
        checkOutPage.lastNameField.sendKeys("Kostic");
        checkOutPage.zipField.sendKeys("11000");
        checkOutPage.clickOnContinueButton();
        checkOutPage.finishButton.click();
        String expectedText = "CHECKOUT: COMPLETE!";
        Assert.assertEquals(checkOutPage.checkOutTitle.getText(), expectedText);
        String expectedMessage = "THANK YOU FOR YOUR ORDER";
        Assert.assertEquals(checkOutPage.compliteMessage.getText(), expectedMessage);

    }


    @AfterMethod
    public void closeLoginPageBrowser() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
