package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class untuk semua Page Objects
 * Mengandung reusable methods yang dipakai semua page
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor untuk inisialisasi
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this); // Wajib untuk @FindBy
    }

    // Method untuk menunggu elemen bisa diklik
    protected void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Method untuk menunggu elemen terlihat
    protected void waitForVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Method untuk mengisi text field
    protected void enterText(WebElement element, String text) {
        waitForVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    // Method untuk klik elemen
    protected void click(WebElement element) {
        waitForClickable(element);
        element.click();
    }

    // Method untuk mendapatkan text dari elemen
    protected String getText(WebElement element) {
        waitForVisible(element);
        return element.getText();
    }

    // Method untuk cek elemen ditampilkan
    protected boolean isDisplayed(WebElement element) {
        try {
            waitForVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method untuk navigasi ke URL - TAMBAHKAN INI
    protected void navigateTo(String url) {
        driver.get(url);
    }

    // Alias methods untuk konsistensi - TAMBAHKAN INI
    protected void clickElement(WebElement element) {
        click(element);
    }

    protected String getElementText(WebElement element) {
        return getText(element);
    }

    protected boolean isElementDisplayed(WebElement element) {
        return isDisplayed(element);
    }
}