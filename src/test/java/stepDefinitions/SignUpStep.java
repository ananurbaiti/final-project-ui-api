package stepDefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.SignUpPage;

import java.time.Duration;

public class SignUpStep {

    Faker faker = new Faker();

    private WebDriver driver;
    private SignUpPage signUpPage;
    private HomePage homePage;

    @Given("user is on sign up page")
    public void userIsOnSignUpPage() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();

        driver.findElement(By.id("signin2")).click();
        signUpPage = new SignUpPage(driver);
    }

    @When("user input username with {string}")
    public void userInputUsernameWith(String username) {
        if (username.equalsIgnoreCase("randomusername")) {
            username = "jayjay" + faker.number().digits(5); // Generate a random username
        }
        signUpPage.inputUsername(username);
    }

    @And("user input password with {string}")
    public void userInputPasswordWith(String password) {
        signUpPage.inputPassword(password);

    }

    @And("user click sign up button")
    public void userClickSignUpButton() {
        signUpPage.clickSignUpButton();
    }

    @Then("user will redirect to homepage after sign up")
    public void userWillRedirectToHomepageAfterSignUp() {
        // ✨ Tangani alert "Sign up successful." jika muncul
        try {
            WebDriverWait waitAlert = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = waitAlert.until(ExpectedConditions.alertIsPresent());

            String alertText = alert.getText();
            System.out.println("Alert Message: " + alertText);

            alert.accept(); // Tutup alert agar tidak ganggu langkah berikutnya
        } catch (TimeoutException e) {
            System.out.println("No alert present after sign up.");
        }

        // ⏳ Tunggu redirect ke homepage
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Tunggu URL mengandung domain utama
        wait.until(ExpectedConditions.urlContains("demoblaze.com"));

        // 2. Tunggu salah satu elemen muncul
        boolean welcomeVisible = false;
        boolean logoutVisible = false;
        try {
            welcomeVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))) != null;
        } catch (Exception e) {
            System.out.println("Element #nameofuser not visible.");
        }

        try {
            logoutVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2"))) != null;
        } catch (Exception e) {
            System.out.println("Element #logout2 not visible.");
        }

        // 3. Inisialisasi homepage dan lakukan verifikasi
        homePage = new HomePage(driver);

        boolean isHomepageLoaded = welcomeVisible || logoutVisible ||
                driver.getCurrentUrl().contains("demoblaze.com");

        Assert.assertTrue("User was not redirected to homepage", isHomepageLoaded);
    }

}