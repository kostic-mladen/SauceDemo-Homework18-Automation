package SwagLabs.Base;

import SwagLabs.Page.CheckOutPage;
import SwagLabs.Page.HomePage;
import SwagLabs.Page.ProductsPage;
import SwagLabs.Page.SingleItemPage;
import SwagLabs.Test.FooterTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class BaseTest {

    public static WebDriver driver;
    public WebDriverWait wait;
    public HomePage homePage;
    public ProductsPage productsPage;

    public SingleItemPage singleItemPage;
    public CheckOutPage checkOutPage;
    public FooterTest footerTest;
    public ExcelReader excelReader;
    public String homeUrl;


    @BeforeClass
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();

        excelReader = new ExcelReader("src/test/java/SwagLabs/TestData.xlsx");
        homeUrl = excelReader.getStringData("URL", 1,0);

    }

    public void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForClickability(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean isDisplayed(WebElement element) {
        boolean webelement = false;
        try {
            webelement = element.isDisplayed();
        } catch (Exception e) {

        }
        return webelement;
    }

    public void selectByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectByText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public void selectByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }



    @AfterClass
    public void tearDown() {
        //  driver.quit();
    }

}
