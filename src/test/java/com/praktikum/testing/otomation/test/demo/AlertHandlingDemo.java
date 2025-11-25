package com.praktikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AlertHandlingDemo {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void demonstrateSimpleAlert() {
        System.out.println("\n=== SIMPLE ALERT DEMO ===");

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // Click button to trigger alert
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        System.out.println("  Clicked JS Alert button");

        // Wait for alert to be present
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("  Alert appeared");

        // Get alert text
        String alertText = alert.getText();
        System.out.println("  Alert text: " + alertText);
        Assert.assertEquals(alertText, "I am a JS Alert");

        // Accept (OK) alert
        alert.accept();
        System.out.println("  Alert accepted");

        // Verify result
        String result = driver.findElement(By.id("result")).getText();
        System.out.println("  Result: " + result);
        Assert.assertEquals(result, "You successfully clicked an alert");

        System.out.println("\n  Simple alert test PASSED\n");
    }

    @Test
    public void demonstrateConfirmAlert() {
        System.out.println("\n=== CONFIRM ALERT DEMO ===");

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // Test ACCEPT confirm
        System.out.println("\nTest 1: Accept Confirm");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("  Confirm alert text: " + confirmAlert.getText());

        confirmAlert.accept();
        String acceptResult = driver.findElement(By.id("result")).getText();
        System.out.println("  Result after Accept: " + acceptResult);
        Assert.assertEquals(acceptResult, "You clicked: Ok");

        // Test DISMISS confirm
        System.out.println("\nTest 2: Dismiss Confirm");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert confirmAlert2 = wait.until(ExpectedConditions.alertIsPresent());

        confirmAlert2.dismiss();
        String dismissResult = driver.findElement(By.id("result")).getText();
        System.out.println("  Result after Dismiss: " + dismissResult);
        Assert.assertEquals(dismissResult, "You clicked: Cancel");

        System.out.println("\n  Confirm alert test PASSED\n");
    }

    @Test
    public void demonstratePromptAlert() {
        System.out.println("\n=== PROMPT ALERT DEMO ===");

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // Click prompt button
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        System.out.println("  Clicked JS Prompt button");

        // Wait for alert
        Alert promptAlert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("  Prompt alert text: " + promptAlert.getText());

        // Enter text in prompt
        String inputText = "Selenium WebDriver Test";
        promptAlert.sendKeys(inputText);
        System.out.println("  Entered text: " + inputText);

        // Accept prompt
        promptAlert.accept();
        System.out.println("  Prompt accepted");

        // Verify result
        String result = driver.findElement(By.id("result")).getText();
        System.out.println("  Result: " + result);
        Assert.assertEquals(result, "You entered: " + inputText);

        System.out.println("\n  Prompt alert test PASSED\n");
    }

    @Test
    public void demonstrateAlertMethods() {
        System.out.println("\n=== ALL ALERT METHODS ===");

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // Trigger alert
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        System.out.println("\nAvailable Alert Methods:");
        System.out.println("1. getText() - Get alert message: " + alert.getText());
        System.out.println("2. accept() - Click OK button");
        System.out.println("3. dismiss() - Click Cancel button (for confirm/prompt)");
        System.out.println("4. sendKeys(text) - Enter text (for prompt only)");

        alert.accept();

        System.out.println("\n  Alert methods demonstration PASSED\n");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}