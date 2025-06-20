package helper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.BaseSteps;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility extends BaseSteps {

    public static File getJSONSchemaFile(String JSONFile) {
        return new File("src/test/java/helper/JSONSchemaData/" + JSONFile);
    }

    public static String generateRandomEmail() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "_-.";
        String email = "";
        String temp = RandomStringUtils.randomAlphanumeric(10);
        email = temp + "@testdata.com";
        return email;
    }

    public static boolean waitAndClickAddToCart(WebDriver driver, By locator) {
        // Menunggu maksimal 10 detik sampai elemen muncul dan bisa diklik
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void contohMethod() {
        WebDriver driver = BaseSteps.driver;//akses static driver dari langsung dari class
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static WebDriverWait getWait(WebDriver i) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void takeScreenshot(WebDriver driver, String name) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String path = "target/screenshots/" + name + "_" + timestamp + ".png";
            FileUtils.copyFile(screenshot, new File(path));
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

}
