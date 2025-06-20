package stepDefinitions;
import helper.Utility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;

import java.io.File;
import java.time.Duration;

public class LoginStep extends BaseSteps {
    //    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductPage productPage;
    private final By ADD_TO_CART_BUTTON = By.xpath("//a[contains(.,'Add to cart')]");

    @Given("user is on login page")
    public void userIsOnLoginPage() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demoblaze.com/");
        driver.manage().window().maximize();

        // Click login link
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        loginPage = new LoginPage(driver);
    }

    @When("user input to username with {string}")
    public void user_input_to_username_with(String username) {
        loginPage.inputUsername(username);
    }

    @And("user input to password with {string}")
    public void user_input_to_password_with(String password) {
        loginPage.inputPassword(password);
    }

    @And("user click login button")
    public void user_click_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("user will redirect to homepage after login")
    public void userWillRedirectToHomepageAfterLogin() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

            homePage = new HomePage(driver);
            boolean isHomepageLoaded = homePage.isWelcomeDisplayed() ||
                    homePage.isLogoutDisplayed() ||
                    driver.getCurrentUrl().equals("https://demoblaze.com/");
            Assert.assertTrue("User was not redirected to homepage", isHomepageLoaded);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @Then("A message appears {string}")
    public void a_message_appears(String expectedMessage) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            String actualMessage = alert.getText();
            System.out.println("Actual alert message: " + actualMessage);
            Assert.assertEquals(expectedMessage, actualMessage);

            alert.accept(); // Tutup alert setelah validasi
        } catch (TimeoutException | NoAlertPresentException e) {
            Assert.fail("Expected alert with message '" + expectedMessage + "' but no alert was present.");
        } finally {
            driver.quit();
        }
    }

    @Given("user is on the product page")
    public void userIsOnTheProductPage() {
        driver.get("https://demoblaze.com");
        productPage = new ProductPage(driver);
        Assert.assertTrue("Not on product page", productPage.isOnTheProductPage());
    }

    @When("user choose product {string}")
    public void userChooseProduct(String productName) throws InterruptedException {
        try {
            // Tambahkan delay untuk memastikan halaman sepenuhnya load
            Thread.sleep(2000); // Hanya untuk debugging, sebaiknya diganti dengan wait eksplisit

            // Verifikasi productPage sudah diinisialisasi
            Assert.assertNotNull("ProductPage belum diinisialisasi", productPage);

            // Pilih produk
            productPage.selectProduct(productName);


        } catch (Exception e) {
            System.err.println("Error saat memilih produk: " + e.getMessage());
            takeScreenshot("product_selection_error");
            throw e;
        }
    }

    private void takeScreenshot(String fileName) {
        try {
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenshots/" + fileName + ".png"));
        } catch (Exception e) {
            System.err.println("Gagal mengambil screenshot: " + e.getMessage());
        }
    }

    @And("user click Add to cart button")
    public void addToCart() {
        driver.get("https://www.demoblaze.com/prod.html?idp_=3");

        Utility.waitAndClickAddToCart(driver, ADD_TO_CART_BUTTON);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals("Product added", alert.getText());
        alert.accept();
    }

    @Then("user will redirect to homepage")
    public void userWillRedirectToHomepage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Timeout diperpanjang

        // Verifikasi multiple kondisi dengan pesan error yang spesifik
        try {
            // Verifikasi element 'nameofuser' terlihat dahulu
            WebElement userElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

            homePage = new HomePage(driver);

            // Verifikasi URL benar
            String expectedUrl = "https://demoblaze.com/";
            String actualUrl = driver.getCurrentUrl();
            Assert.assertEquals("URL tidak sesuai dengan homepage", expectedUrl, actualUrl);

            // Verifikasi minimal satu element homepage terlihat
            boolean isWelcomeVisible = homePage.isWelcomeDisplayed();
            boolean isLogoutVisible = homePage.isLogoutDisplayed();

            Assert.assertTrue("Tidak ada elemen welcome/logout yang terlihat di homepage",
                    isWelcomeVisible || isLogoutVisible);

        } catch (TimeoutException e) {
            throw new AssertionError("Element 'nameofuser' tidak muncul dalam 15 detik", e);
        }
    }
}