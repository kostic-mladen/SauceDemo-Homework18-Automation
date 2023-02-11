package SwagLabs.Test;

import SwagLabs.Base.BaseTest;
import SwagLabs.Base.ExcelReader;
import SwagLabs.Page.CheckOutPage;
import SwagLabs.Page.HomePage;
import SwagLabs.Page.ProductsPage;
import SwagLabs.Page.SingleItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void pageLoginSetUp() throws IOException {

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
    public void validLogIn() {
        String validUsername = excelReader.getStringData("Login", 1, 0);
        String validPassword = excelReader.getStringData("Login", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
    }


    @Test(priority = 10)
    public void verifyThatStandardUserCanLogInWithValidInputs() throws InterruptedException {
        validLogIn();
        Assert.assertFalse(isDisplayed(homePage.UsernameField));
        Assert.assertFalse(isDisplayed(homePage.PasswordField));
        String expectedURL = excelReader.getStringData("URL", 1, 1);
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(isDisplayed(productsPage.shoppingCart));
        Assert.assertTrue(isDisplayed(productsPage.reactMenu));
        Assert.assertTrue(isDisplayed(productsPage.logoProducts));
    }

    @Test(priority = 20)
    public void verifyThatStandardUserCanLogout() throws InterruptedException {
        validLogIn();
        productsPage.clickOnReactMenu();
        waitForVisibility(productsPage.LogoutButton);
        productsPage.clickOnLogoutButton();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
        Assert.assertTrue(homePage.UsernameField.isDisplayed());
        Assert.assertTrue(homePage.PasswordField.isDisplayed());
        Assert.assertTrue(homePage.logo.isDisplayed());
        Assert.assertTrue(homePage.bot.isDisplayed());
    }

    @Test(priority = 30)
    public void verifyThatStandardUserCanNotLogInWithInvalidUsername() {
        for (int i = 1; i <= excelReader.getLastRow("Standard"); i++) {
            String invalidUsername = excelReader.getStringData("Standard", i, 2);
            String validPassword = excelReader.getStringData("Standard", 1, 1);
            homePage.inputUsername(invalidUsername);
            homePage.inputPassword(validPassword);
            homePage.clickOnLoginButton();Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));

            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }

    }

    @Test(priority = 40)
    public void verifyThatStandardUserCanNotLogInWithInvalidPassword() {
        for (int i = 1; i <= excelReader.getLastRow("Standard"); i++) {
            String validUsername = excelReader.getStringData("Standard", 1, 0);
            String invalidPassword = excelReader.getStringData("Standard", i, 3);
            homePage.inputUsername(validUsername);
            homePage.inputPassword(invalidPassword);
            homePage.clickOnLoginButton();
            Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }

    }

    @Test(priority = 50)
    public void verifyThatLockedOutUserCanNotLogin() {
        String validLockedUser = excelReader.getStringData("Locked", 1, 0);
        String validPassword = excelReader.getStringData("Locked", 1, 1);
        homePage.inputUsername(validLockedUser);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
        Assert.assertTrue(homePage.UsernameField.isDisplayed());
        Assert.assertTrue(homePage.PasswordField.isDisplayed());
        Assert.assertTrue(homePage.logo.isDisplayed());
        Assert.assertTrue(homePage.bot.isDisplayed());
        Assert.assertTrue(homePage.Notification.isDisplayed());
        WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
        String expectedNotification = "Epic sadface: Sorry, this user has been locked out.";
        Assert.assertEquals(notification.getText(), expectedNotification);
    }

    @Test(priority = 60)
    public void verifyThatProblemUserCanLoginWithValidInput() {
        String validUsername = excelReader.getStringData("Problem", 1, 0);
        String validPassword = excelReader.getStringData("Problem", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 1));
        Assert.assertTrue(productsPage.logoProducts.isDisplayed());
        Assert.assertTrue(productsPage.reactMenu.isDisplayed());
        Assert.assertTrue(productsPage.shoppingCart.isDisplayed());
    }

    @Test(priority = 70)
    public void verifyThatPerformanceGlitchUserCanLogin() {
        String validUsername = excelReader.getStringData("Performance", 1, 0);
        String validPassword = excelReader.getStringData("Performance", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 1));
        Assert.assertTrue(productsPage.logoProducts.isDisplayed());
        Assert.assertTrue(productsPage.reactMenu.isDisplayed());
        Assert.assertTrue(productsPage.shoppingCart.isDisplayed());
    }

    @Test(priority = 80)
    public void verifyThatProblemUserCanNotLogInWithInvalidUsername() {
        for (int i = 1; i <= excelReader.getLastRow("Problem"); i++) {
            String invalidUsername = excelReader.getStringData("Problem", i, 2);
            String validPassword = excelReader.getStringData("Problem", 1, 1);
            homePage.inputUsername(invalidUsername);
            homePage.inputPassword(validPassword);
            homePage.clickOnLoginButton();
            Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }

    }

    @Test (priority = 90)
    public void verifyThatProblemUserCanNotLoginWithInvalidPassword() {
        for (int i = 1; i <= excelReader.getLastRow("Problem"); i++) {
            String validUsername = excelReader.getStringData("Problem", 1, 0);
            String invalidPassword = excelReader.getStringData("Problem", i, 3);
            homePage.inputUsername(validUsername);
            homePage.inputPassword(invalidPassword);
            homePage.clickOnLoginButton();
            Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }
    }

    @Test(priority = 100)
    public void verifyThatPerformanceGlitchUserCanNotLogInWithInvalidUsername() {
        for (int i = 1; i <= excelReader.getLastRow("Performance"); i++) {
            String invalidUsername = excelReader.getStringData("Performance", i, 2);
            String validPassword = excelReader.getStringData("Performance", 1, 1);
            homePage.inputUsername(invalidUsername);
            homePage.inputPassword(validPassword);
            homePage.clickOnLoginButton();
            Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }

    }

    @Test (priority = 110)
    public void verifyThatPerformanceGlitchUserCanNotLoginWithInvalidPassword() {
        for (int i = 1; i <= excelReader.getLastRow("Performance"); i++) {
            String validUsername = excelReader.getStringData("Performance", 1, 0);
            String invalidPassword = excelReader.getStringData("Performance", i, 3);
            homePage.inputUsername(validUsername);
            homePage.inputPassword(invalidPassword);
            homePage.clickOnLoginButton();
            Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }
    }
    @Test(priority = 120)
    public void verifyThatLockedOutUserCanNotLogInWithInvalidUsername() {
        for (int i = 1; i <= excelReader.getLastRow("Locked"); i++) {
            String invalidUsername = excelReader.getStringData("Locked", i, 2);
            String validPassword = excelReader.getStringData("Locked", 1, 1);
            homePage.inputUsername(invalidUsername);
            homePage.inputPassword(validPassword);
            homePage.clickOnLoginButton();
            Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }



    }

    @Test (priority = 130)
    public void verifyThatLockedOutUserCanNotLoginWithInvalidPassword() {
        for (int i = 1; i <= excelReader.getLastRow("Locked"); i++) {
            String validUsername = excelReader.getStringData("Locked", 1, 0);
            String invalidPassword = excelReader.getStringData("Locked", i, 3);
            homePage.inputUsername(validUsername);
            homePage.inputPassword(invalidPassword);
            homePage.clickOnLoginButton();
            Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
            Assert.assertTrue(homePage.UsernameField.isDisplayed());
            Assert.assertTrue(homePage.PasswordField.isDisplayed());
            Assert.assertTrue(homePage.logo.isDisplayed());
            Assert.assertTrue(homePage.bot.isDisplayed());
            WebElement notification = driver.findElement(By.cssSelector(".error-message-container.error"));
            String expectedNotification = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(notification.getText(), expectedNotification);
            driver.navigate().refresh();
        }
    }

    @Test(priority = 140)
    public void verifyThatProblemUserCanLogout() throws InterruptedException {
        String validUsername = excelReader.getStringData("Problem", 1, 0);
        String validPassword = excelReader.getStringData("Problem", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
        productsPage.clickOnReactMenu();
        waitForVisibility(productsPage.LogoutButton);
        productsPage.clickOnLogoutButton();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
        Assert.assertTrue(homePage.UsernameField.isDisplayed());
        Assert.assertTrue(homePage.PasswordField.isDisplayed());
        Assert.assertTrue(homePage.logo.isDisplayed());
        Assert.assertTrue(homePage.bot.isDisplayed());
    }

    @Test(priority = 150)
    public void verifyThatPerformanceGlitchUserCanLogout() throws InterruptedException {
        String validUsername = excelReader.getStringData("Performance", 1, 0);
        String validPassword = excelReader.getStringData("Performance", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
        productsPage.clickOnReactMenu();
        waitForVisibility(productsPage.LogoutButton);
        productsPage.clickOnLogoutButton();
        Assert.assertEquals(driver.getCurrentUrl(), excelReader.getStringData("URL", 1, 0));
        Assert.assertTrue(homePage.UsernameField.isDisplayed());
        Assert.assertTrue(homePage.PasswordField.isDisplayed());
        Assert.assertTrue(homePage.logo.isDisplayed());
        Assert.assertTrue(homePage.bot.isDisplayed());
    }

    @AfterMethod
    public void closeLoginPageBrowser() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
