package TestComponents;

import PageObjects.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public MainPage mainPage;
    public static final Logger logger = LogManager.getLogger(BaseTest.class);

    public WebDriver initializeDriver() {
       // WebDriverManager.chromedriver().setup();
        WebDriverManager.chromedriver().browserVersion("125.0.6422.113").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public MainPage launchGunfirePage() {
        logger.info("Test starts");
        driver = initializeDriver();
        driver.get("https://gunfire.com/pl/");
        mainPage = new MainPage(driver);
        //driver.findElement(By.xpath("//a[@href='#acceptSelected']")).click();
        driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
        return mainPage;
    }

    @AfterMethod
    public void closeDriver() throws InterruptedException {
        logger.info("Test ends");
        Thread.sleep(3000);
        driver.quit();
    }
}
