package stepDefinitions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

public class BaseSteps {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @Before
    public void setup() {
        System.out.println("Setting up driver..");
        WebDriverManager.chromedriver().clearDriverCache().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        System.out.println("Driver setup completed");
    }

    @After
    public void tearDown() {
        try {
            if (driver !=null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Error closing browser: " + e.getMessage());
        }

        }
}
