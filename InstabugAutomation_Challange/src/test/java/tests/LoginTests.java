package tests;


import Utils.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class LoginTests extends BaseTest {

    public LoginPage loginPage;

    @BeforeMethod
    public void setUp()
    {
        super.setUp();
        loginPage = new LoginPage(driver);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider()
    {
        return utils.getLoginData();  // Get the login data from the config file
    }

    @Test(dataProvider = "loginData")
    public void testLoginMultipleUsers(String username, String password)
    {

        // Set the test name dynamically using the username
        String testName = "Login Test for User: " + username;
        System.out.println("Running test: " + testName);

        // Perform the login with the provided username and password
        loginPage.login(username, password);

        // Fetch expected data from config
        String expectedUrl = utils.getProperty("loginUrl");
        String lockedOutMessage = utils.getProperty("lockedOutUserMessage");

        // Handle locked_out_user specific behavior
        if (username.equals("locked_out_user"))
        {
            String actualErrorMessage = driver.findElement(By.cssSelector(".error-message-container")).getText();
            Assert.assertEquals(actualErrorMessage, lockedOutMessage, "Error message does not match!");
        }
        else
        {
            // Handle performance_glitch_user specific behavior
            if (username.equals("performance_glitch_user"))
            {

                int waitTimeInSeconds = Integer.parseInt(utils.getProperty("explicitWait"));
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
                wait.until(ExpectedConditions.urlToBe(expectedUrl));

            }
            else
            {
                Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed for " + username);
            }
        }
    }

    @Test
    public void testInvalidLogin()
    {
        loginPage.login("invalid_user", "invalid_password");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void testEmptyUsername()
    {
        loginPage.login("", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(),"Epic sadface: Username is required");
    }

    @Test
    public void testEmptyPassword()
    {
        loginPage.login("standard_user", "");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(),"Epic sadface: Password is required");
    }
}
