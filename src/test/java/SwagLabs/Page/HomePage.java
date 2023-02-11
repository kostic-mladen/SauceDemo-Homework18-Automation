package SwagLabs.Page;

import SwagLabs.Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseTest {

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "login_logo")
    public WebElement logo;

    @FindBy(id = "user-name")
    public WebElement UsernameField;

    @FindBy(id = "password")
    public  WebElement PasswordField;

    @FindBy(id = "login-button")
    public  WebElement LogInButton;

    @FindBy (className = "bot_column")
    public WebElement bot;

    @FindBy (className = "error-button")
    public WebElement Notification;

    public String expectedNotification = "Epic sadface: Sorry, this user has been locked out.";

    public void inputUsername (String username){
        UsernameField.sendKeys(username);
    }

    public void inputPassword(String password){
        PasswordField.sendKeys(password);
    }

    public void clickOnLoginButton() {
        LogInButton.click();
    }
}
