package com.praktikum.testing.otomation.pages.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for Secure Page (after successful login)
 * URL: https://the-internet.herokuapp.com/secure
 */
public class SecurePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By pageHeading = By.tagName("h2");
    private By flashMessage = By.id("flash");
    private By logoutButton = By.cssSelector("a[href='/logout']");
    private By subheading = By.tagName("h4");

    // Constructor
    public SecurePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Page Actions
    public void clickLogoutButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        element.click();
    }

    // Verification methods
    public String getPageHeading() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
        return element.getText();
    }

    public String getFlashMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
        return element.getText();
    }

    public String getSubheading() {
        return driver.findElement(subheading).getText();
    }

    public boolean isLogoutButtonDisplayed() {
        return driver.findElement(logoutButton).isDisplayed();
    }

    public boolean isOnSecurePage() {
        return driver.getCurrentUrl().contains("/secure");
    }
}