package com.praktikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;

public class WaitStrategiesDemo {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void demonstrateImplicitWait() {
        System.out.println("\n=== IMPLICIT WAIT DEMO ===");

        // Set implicit wait - applies to all findElement calls
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("   Set implicit wait: 10 seconds");

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // Click start button
        driver.findElement(By.cssSelector("#start button")).click();
        System.out.println("   Clicked start button");

        // PERBAIKAN: Gunakan explicit wait untuk visibility, bukan hanya presence
        // Implicit wait hanya memastikan element ADA di DOM, tapi tidak memastikan VISIBLE
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement finishText = explicitWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        System.out.println("   Found finish element: " + finishText.getText());
        Assert.assertEquals(finishText.getText(), "Hello World!");

        // Reset implicit wait to avoid affecting other tests
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        System.out.println("\n   Implicit wait test PASSED\n");
    }

    @Test
    public void demonstrateExplicitWait() {
        System.out.println("\n=== EXPLICIT WAIT DEMO ===");

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // Click start button
        driver.findElement(By.cssSelector("#start button")).click();
        System.out.println("   Clicked start button");

        // Create WebDriverWait instance
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("   Created explicit wait: 10 seconds");

        // Wait for element to be visible
        WebElement finishText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        System.out.println("   Element became visible: " + finishText.getText());

        Assert.assertEquals(finishText.getText(), "Hello World!");

        System.out.println("\n   Explicit wait test PASSED\n");
    }

    @Test
    public void demonstrateExplicitWaitConditions() {
        System.out.println("\n=== VARIOUS WAIT CONDITIONS ===");

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click start
        driver.findElement(By.cssSelector("#start button")).click();

        // 1. Wait for element to be present in DOM
        System.out.println("\n1. Waiting for element presence:");
        WebElement element1 = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("finish")));
        System.out.println("    Element present in DOM");

        // 2. Wait for element to be visible
        System.out.println("\n2. Waiting for element visibility:");
        WebElement element2 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        System.out.println("    Element visible: " + element2.getText());

        // PERBAIKAN: Navigate properly and wait for page load
        System.out.println("\n3. Navigating to login page:");
        driver.get("https://the-internet.herokuapp.com/login");

        // Wait for page to load completely
        wait.until(ExpectedConditions.titleContains("The Internet"));

        // 3. Wait for element to be clickable
        System.out.println("4. Waiting for element to be clickable:");
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        System.out.println("    Element clickable");

        // 4. Wait for specific text in title (PERBAIKAN: gunakan text yang benar)
        System.out.println("\n5. Waiting for title:");
        boolean titleContains = wait.until(
                ExpectedConditions.titleContains("The Internet"));
        // Bukan "Login"
        System.out.println("    Title contains 'The Internet': " + titleContains);

        // 5. Wait for URL to contain text
        System.out.println("\n6. Waiting for URL:");
        boolean urlContains = wait.until(
                ExpectedConditions.urlContains("login"));
        System.out.println("    URL contains 'login': " + urlContains);

        // 6. Wait for text to be present in element
        System.out.println("\n7. Waiting for text to be present:");
        boolean textPresent = wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("h2"), "Login Page"));
        System.out.println("    Text present: " + textPresent);

        // 7. Additional: Wait for element to be enabled
        System.out.println("\n8. Waiting for element to be enabled:");
        WebElement usernameField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("username")));
        System.out.println("    Username field enabled");

        System.out.println("\n   Wait conditions test PASSED\n");
    }

    @Test
    public void demonstrateFluentWait() {
        System.out.println("\n=== FLUENT WAIT DEMO ===");

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // Click start
        driver.findElement(By.cssSelector("#start button")).click();

        // Create FluentWait with custom polling and exceptions to ignore
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(org.openqa.selenium.StaleElementReferenceException.class);

        System.out.println("   Created fluent wait:");
        System.out.println("   - Timeout: 10 seconds");
        System.out.println("   - Polling interval: 500ms");
        System.out.println("   - Ignoring: NoSuchElementException, StaleElementReferenceException");

        // Wait for element with custom condition
        WebElement finishText = fluentWait.until(driver1 -> {
            WebElement element = driver1.findElement(By.id("finish"));
            if (element.isDisplayed() && !element.getText().isEmpty()) {
                System.out.println("    Polling... element displayed with text: " + element.getText());
                return element;
            }
            return null;
        });

        System.out.println("   Element found: " + finishText.getText());
        Assert.assertEquals(finishText.getText(), "Hello World!");

        System.out.println("\n   Fluent wait test PASSED\n");
    }

    @Test
    public void demonstrateFluentWaitWithExpectedConditions() {
        System.out.println("\n=== FLUENT WAIT WITH EXPECTED CONDITIONS ===");

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");

        // Click start
        driver.findElement(By.cssSelector("#start button")).click();

        // FluentWait bisa juga dikombinasikan dengan ExpectedConditions
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);

        // Use ExpectedConditions dengan FluentWait
        WebElement element = fluentWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        System.out.println("   Element found with FluentWait + ExpectedConditions: " + element.getText());
        Assert.assertEquals(element.getText(), "Hello World!");

        System.out.println("\n   FluentWait with ExpectedConditions test PASSED\n");
    }

    @Test
    public void compareWaitStrategies() {
        System.out.println("\n=== COMPARING WAIT STRATEGIES ===");

        System.out.println("\n1. IMPLICIT WAIT:");
        System.out.println("   Pros: Simple to implement, applies globally");
        System.out.println("   Cons: Less flexible, can slow down negative tests");
        System.out.println("   Use when: Quick prototyping, simple tests");
        System.out.println("   Warning: Only waits for presence, not visibility!");

        System.out.println("\n2. EXPLICIT WAIT:");
        System.out.println("   Pros: Flexible, specific conditions, better for complex scenarios");
        System.out.println("   Cons: More verbose, need to create for each wait");
        System.out.println("   Use when: Dynamic content, AJAX calls, specific conditions needed");
        System.out.println("   Recommended for most cases");

        System.out.println("\n3. FLUENT WAIT:");
        System.out.println("   Pros: Most flexible, custom polling, ignore exceptions");
        System.out.println("   Cons: Most complex to implement");
        System.out.println("   Use when: Complex conditions, custom polling needed");
        System.out.println("   Advanced scenarios only");

        System.out.println("\n   BEST PRACTICE: Use Explicit Wait (WebDriverWait) for 90% of cases");
        System.out.println("   Combine with ExpectedConditions for robust waiting");

        System.out.println("\n   Wait strategies comparison completed\n");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}