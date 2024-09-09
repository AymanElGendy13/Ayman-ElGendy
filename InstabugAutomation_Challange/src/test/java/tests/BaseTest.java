package tests;

import Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;


import java.time.Duration;
import org.testng.annotations.Listeners;
import Utils.utils;


public class BaseTest {

    public static WebDriver driver;
    public LoginPage loginPage;
    // Initialize the WebDriver based on the browser name from properties file
    public WebDriver initializeDriver(String browser)
    {
        switch (browser.toLowerCase())
        {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser type not supported");
        }
        return driver;
    }


    // This will be run before each test
    @BeforeMethod
    public void setUp()
    {
        // Initialize the browser (you can change the browser from the config file)
        driver = initializeDriver(utils.getProperty("browser"));

        // Navigate to the base URL
        driver.get(utils.getProperty("baseUrl"));

        // Maximize the browser window
        driver.manage().window().maximize();

        // Set an implicit wait timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(utils.getProperty("implicitWait"))));


    }

    // This will be run after each test
    @AfterMethod
    public void tearDown()
    {
        if (driver != null) {
            driver.quit();  // Close the browser and end the session
        }
    }

}