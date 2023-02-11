package SwagLabs.Test;

import SwagLabs.Base.BaseTest;
import SwagLabs.Base.ExcelReader;
import SwagLabs.Page.CheckOutPage;
import SwagLabs.Page.HomePage;
import SwagLabs.Page.ProductsPage;
import SwagLabs.Page.SingleItemPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class FooterTest extends BaseTest {
    public FooterTest() {
        PageFactory.initElements(driver, this);
    }

    @BeforeMethod
    public void pageFooterSetUp() throws IOException {

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
    public void logInforFooter() {
        String validUsername = excelReader.getStringData("Login", 1, 0);
        String validPassword = excelReader.getStringData("Login", 1, 1);
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnLoginButton();
    }

    @Test(priority = 10)
    public void verifyThatTwitterButtonIsClickable() throws InterruptedException {
        logInforFooter();
        scrollToElement(productsPage.twitterButton);
        String originalWindow = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 1;
        productsPage.clickOnTwitterButton();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        Assert.assertEquals((driver.getCurrentUrl()), excelReader.getStringData("URL", 1, 2));
    }

    @Test(priority = 20)
    public void verifyThatFacebookButtonIsClickable() throws InterruptedException {
        logInforFooter();
        scrollToElement(productsPage.facebookButton);
        String originalWindow = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 1;
        productsPage.clickOnFacebookButton();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        Assert.assertEquals((driver.getCurrentUrl()), excelReader.getStringData("URL", 1, 3));
    }

    @Test(priority = 30)
    public void verifyThatLinkedInButtonIsClickable() throws InterruptedException {
        logInforFooter();
        scrollToElement(productsPage.linkedIn);
        String originalWindow = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 1;
        productsPage.clickOnLinkedInButton();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        Assert.assertNotEquals((driver.getCurrentUrl()), excelReader.getStringData("URL", 1, 0));
    }

    @Test(priority = 40)
    public void verifyThatAboutButtonIsClickable() throws InterruptedException {
        logInforFooter();
        productsPage.clickOnReactMenu();
        waitForVisibility(productsPage.aboutButton);
        productsPage.clickOnAboutButton();
        Assert.assertEquals((driver.getCurrentUrl()), excelReader.getStringData("About", 1, 0));

    }

    @Test
    public void verityThatFooterCopyRightisVisibleAndCorrect(){
        logInforFooter();
        scrollToElement(productsPage.footer);
        String expectedText = "© 2023 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy";
        Assert.assertEquals(productsPage.footer.getText(), expectedText);
    }
    @AfterMethod
    public void closeLoginPageBrowser() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

}
