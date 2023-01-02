package com.boot.selenium.tests;

import com.boot.selenium.util.ConfigurationsUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static com.boot.selenium.util.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SignUpTest {
    private By userButton = By.id("userButton");
    private By signUp = By.id("signUp");
    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By email = By.id("email");
    private By phoneNumber = By.id("phoneNumber");
    private By deliveryAddress = By.id("deliveryAddress");
    private By password = By.id("password");
    private By confirmPassword = By.id("confirmPassword");
    private By signUpButton = By.id("signUpButton");
    private By signUpMsg = By.id("signUpMsg");
    private By emailErrorMsg = By.id("email-helper-text");
    private By phoneErrorMsg = By.id("phoneNumber-helper-text");
    private By confirmPassErrorMsg = By.id("confirmPassword-helper-text");

    private static Properties properties;

    @BeforeClass
    public static void setupClass() throws IOException {
        properties = new ConfigurationsUtil().getConfigProperties();
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void successTest(){
        WebDriver driver = new ChromeDriver();
        driver.get(properties.getProperty(APPLICATION_URL));
        driver.findElement(userButton).click();
        driver.findElement(signUp).click();

        driver.findElement(firstName).sendKeys("Test-FirstName");
        driver.findElement(lastName).sendKeys("Test-LastName");
        driver.findElement(email).sendKeys("test1234@test.com");
        driver.findElement(phoneNumber).sendKeys("0720123456");
        driver.findElement(deliveryAddress).sendKeys("Test Street nr x");
        driver.findElement(password).sendKeys("test1234");
        driver.findElement(confirmPassword).sendKeys("test1234");
        driver.findElement(signUpButton).submit();

        WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(properties.getProperty(SIGN_IN_TIMEOUT)));
        boolean isDisplayed = wait.until(
                ExpectedConditions.textToBe(signUpMsg, "Succesfully signed Up! Please confirm your Email!"));
        assertTrue("Succesfully signed Up visible", isDisplayed);
        driver.quit();
    }

    @Test
    public void failingTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(properties.getProperty(APPLICATION_URL));
        driver.findElement(userButton).click();
        driver.findElement(signUp).click();

        driver.findElement(firstName).sendKeys("Test-FirstName");
        driver.findElement(lastName).sendKeys("Test-LastName");
        driver.findElement(email).sendKeys("test1234");
        driver.findElement(phoneNumber).sendKeys("abcde");
        driver.findElement(deliveryAddress).sendKeys("Test Street nr x");
        driver.findElement(password).sendKeys("test1234");
        driver.findElement(confirmPassword).sendKeys("test12345");
        driver.findElement(signUpButton).submit();

        assertEquals("Invalid Email!", driver.findElement(emailErrorMsg).getText());
        assertEquals("Invalid Phone Number!", driver.findElement(phoneErrorMsg).getText());
        assertEquals("Password does not match!", driver.findElement(confirmPassErrorMsg).getText());
        driver.quit();
    }
}
