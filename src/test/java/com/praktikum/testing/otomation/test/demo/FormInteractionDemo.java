package com.praktikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select; // Import tambahan untuk Dropdown

public class FormInteractionDemo {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testSuccessfulLogin() {
        System.out.println("\n=== TEST: SUCCESSFUL LOGIN ===");

        // Navigate to login page
        driver.get("https://the-internet.herokuapp.com/login");
        System.out.println("Navigated to login page");

        // Find and interact with username field
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.clear(); // Always clear before entering text
        usernameField.sendKeys("tomsmith");
        System.out.println("Entered username: tomsmith");

        // Find and interact with password field
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys("SuperSecretPassword!");
        System.out.println("Entered password");

        // Find and click login button
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        System.out.println("Clicked login button");

        // Verify successful login
        WebElement successMessage = driver.findElement(By.id("flash"));
        String messageText = successMessage.getText();
        System.out.println("Success message: " + messageText);

        // Assertions
        Assert.assertTrue(messageText.contains("You logged into a secure area!"),
                "Success message should be displayed");

        Assert.assertTrue(driver.getCurrentUrl().contains("/secure"),
                "URL should change to secure page");

        System.out.println("Login test PASSED\n");
    }

    @Test
    public void testInvalidLogin() {
        System.out.println("\n=== TEST: INVALID LOGIN ===");

        driver.get("https://the-internet.herokuapp.com/login");

        // Enter invalid credentials
        driver.findElement(By.id("username")).sendKeys("invaliduser");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify error message
        WebElement errorMessage = driver.findElement(By.id("flash"));
        String errorText = errorMessage.getText();
        System.out.println("Error message: " + errorText);

        Assert.assertTrue(errorText.contains("Your username is invalid!"),
                "Error message should be displayed for invalid credentials");

        System.out.println("Invalid login test PASSED\n");
    }

    @Test
    public void testEmptyFormSubmission() {
        System.out.println("\n=== TEST: EMPTY FORM SUBMISSION ===");

        driver.get("https://the-internet.herokuapp.com/login");

        // Submit without entering credentials
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify error message
        WebElement errorMessage = driver.findElement(By.id("flash"));
        Assert.assertTrue(errorMessage.isDisplayed(),
                "Error message should be displayed");

        System.out.println("Empty form test PASSED\n");
    }

    @Test
    public void demonstrateFormElements() {
        System.out.println("\n=== DEMONSTRATING FORM ELEMENTS ===");

        // Navigate to form page with various elements
        driver.get("https://the-internet.herokuapp.com/inputs");

        // Working with input field
        WebElement inputField = driver.findElement(By.tagName("input"));

        // Enter numeric value
        inputField.sendKeys("12345");
        System.out.println("Entered number: 12345");

        // Clear and enter new value
        inputField.clear();
        inputField.sendKeys("67890");
        System.out.println("Cleared and entered new number: 67890");

        // Get input value
        String value = inputField.getAttribute("value");
        Assert.assertEquals(value, "67890", "Input value should match");
        System.out.println("Verified input value: " + value);

        System.out.println("Form elements test PASSED\n");
    }

    @Test
    public void demonstrateCheckboxes() {
        System.out.println("\n=== DEMONSTRATING CHECKBOXES ===");

        driver.get("https://the-internet.herokuapp.com/checkboxes");

        // Find all checkboxes
        WebElement checkbox1 = driver.findElement(By.xpath("(//input[@type='checkbox'])[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("(//input[@type='checkbox'])[2]"));

        // Check initial state
        System.out.println("Checkbox 1 selected: " + checkbox1.isSelected());
        System.out.println("Checkbox 2 selected: " + checkbox2.isSelected());

        // Toggle checkboxes
        if (!checkbox1.isSelected()) {
            checkbox1.click();
            System.out.println("Checked checkbox 1");
        }

        if (checkbox2.isSelected()) {
            checkbox2.click();
            System.out.println("Unchecked checkbox 2");
        }

        // Verify changes
        Assert.assertTrue(checkbox1.isSelected(), "Checkbox 1 should be selected");
        Assert.assertFalse(checkbox2.isSelected(), "Checkbox 2 should be unselected");

        System.out.println("Checkbox test PASSED\n");
    }

    @Test
    public void demonstrateDropdown() {
        System.out.println("\n=== DEMONSTRATING DROPDOWN ===");

        driver.get("https://the-internet.herokuapp.com/dropdown");

        // Import Select class
        WebElement dropdownElement = driver.findElement(By.id("dropdown"));
        Select dropdown = new Select(dropdownElement);

        // Select by visible text
        dropdown.selectByVisibleText("Option 1");
        System.out.println("Selected: Option 1");

        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Option 1");

        // Select by value
        dropdown.selectByValue("2");
        System.out.println("Selected: Option 2 (by value)");

        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Option 2");

        // Select by index
        dropdown.selectByIndex(1);
        System.out.println("Selected: Option 1 (by index)");

        // Get all options
        System.out.println("\nAll dropdown options:");
        dropdown.getOptions().forEach(option ->
                System.out.println(" - " + option.getText()));

        System.out.println("\nDropdown test PASSED\n");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}