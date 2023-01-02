package com.boot.selenium.tests;

import com.boot.selenium.util.ConfigurationsUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;

import static com.boot.selenium.util.Constants.*;
import static org.junit.Assert.assertTrue;

public class SignInTest {
    private By userButton = By.id("userButton");
    private By signInButton = By.id("signIn");
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By userLoginButton = By.id("signInButton");
    private By product = By.id("product");
    private By errorMessageId = By.id("signInMsg");

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
        driver.findElement(signInButton).click();

        driver.findElement(emailInput).sendKeys(properties.getProperty(EXISTING_USERNAME));
        driver.findElement(passwordInput).sendKeys(properties.getProperty(EXISTING_USER_PASSWORD));
        driver.findElement(userLoginButton).submit();

        WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(properties.getProperty(SIGN_IN_TIMEOUT)));
        boolean isDisplayed = wait.until(
                ExpectedConditions.presenceOfElementLocated(product)).isDisplayed();
        assertTrue("Products not visible", isDisplayed);
        driver.quit();
    }

    @Test
    public void testWithInvalidPassword(){
        WebDriver driver = new ChromeDriver();
        driver.get(properties.getProperty(APPLICATION_URL));
        driver.findElement(userButton).click();
        driver.findElement(signInButton).click();

        driver.findElement(emailInput).sendKeys(properties.getProperty(EXISTING_USERNAME));
        driver.findElement(passwordInput).sendKeys("aWrongPassword");
        driver.findElement(userLoginButton).submit();
        WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(properties.getProperty(SIGN_IN_TIMEOUT)));
        boolean isDisplayed = wait.until(
                ExpectedConditions.textToBe(errorMessageId, "Invalid Email or Password!"));
        assertTrue("Error message visible", isDisplayed);
        driver.quit();
    }
}
