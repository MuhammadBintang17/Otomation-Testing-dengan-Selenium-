package com.praktikum.testing.otomation.pages.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for Login Page
 * URL: https://the-internet.herokuapp.com/login
 */
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By flashMessage = By.id("flash");
    private By pageHeading = By.tagName("h2");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Page Actions
    public void navigateToLoginPage() {
        driver.get("https://the-internet.herokuapp.com/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
    }

    public void enterUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        element.clear();
        element.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
    }

    // Combined action method
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // Verification methods
    public String getFlashMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
        return element.getText();
    }

    public String getPageHeading() {
        return driver.findElement(pageHeading).getText();
    }

    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}