package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class BaseTest {

    protected static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    protected String BASE_URL = "https://magento.softwaretestingboard.com/";
    protected int TIMEOUT = 10;
    protected SoftAssert softAssert;

    public WebDriver getDriver() {
        return threadDriver.get();
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        threadDriver.set(new FirefoxDriver());
        getDriver().manage().window().maximize();
        getDriver().get(BASE_URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().manage().deleteAllCookies();
            getDriver().quit();
            threadDriver.remove();
        }
        softAssert.assertAll();
    }
}