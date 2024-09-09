package Pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    private WebDriverWait wait;

    @FindBy(
            id = "user-name"
    )
    private WebElement usernameField;
    @FindBy(
            id = "password"
    )
    private WebElement passwordField;
    @FindBy(
            id = "login-button"
    )
    private WebElement loginButton;
    @FindBy(
            css = "div.error-message-container.error"
    )
    private WebElement errorMessage;

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10L));
    }

    public void setUsername(String username)
    {
        this.wait.until(ExpectedConditions.visibilityOf(this.usernameField));
        this.usernameField.clear();
        this.usernameField.sendKeys(new CharSequence[]{username});
    }

    public void setPassword(String password)
    {
        this.wait.until(ExpectedConditions.visibilityOf(this.passwordField));
        this.passwordField.clear();
        this.passwordField.sendKeys(new CharSequence[]{password});
    }

    public void clickLoginButton()
    {
        this.wait.until(ExpectedConditions.elementToBeClickable(this.loginButton));
        this.loginButton.click();
    }

    public String getErrorMessage()
    {
        this.wait.until(ExpectedConditions.visibilityOf(this.errorMessage));
        return this.errorMessage.getText();
    }

    public void login(String username, String password)
    {
        this.setUsername(username);
        this.setPassword(password);
        this.clickLoginButton();
    }

    public boolean isErrorMessageDisplayed()
    {
        return this.errorMessage.isDisplayed();
    }
}
