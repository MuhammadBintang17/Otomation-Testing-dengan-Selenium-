package com.praktikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Set;

public class NavigationAndWindowDemo {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void demonstrateNavigation() {
        System.out.println("\n=== NAVIGATION COMMANDS ===");

        driver.get("https://the-internet.herokuapp.com/");
        System.out.println("Navigated to homepage");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        driver.findElement(By.linkText("Form Authentication")).click();
        System.out.println("Clicked Form Authentication link");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        driver.navigate().back();
        System.out.println("Navigated back");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        driver.navigate().forward();
        System.out.println("Navigated forward");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        driver.navigate().refresh();
        System.out.println("Page refreshed");

        driver.navigate().to("https://the-internet.herokuapp.com/dropdown");
        System.out.println("Navigated to dropdown page");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        System.out.println("\nNavigation test PASSED\n");
    }

    @Test
    public void demonstrateMultipleWindows() {
        System.out.println("\n=== MULTIPLE WINDOWS HANDLING ===");

        driver.get("https://the-internet.herokuapp.com/windows");

        String originalWindow = driver.getWindowHandle();
        System.out.println("Original window handle: " + originalWindow);

        Assert.assertEquals(driver.getWindowHandles().size(), 1);

        driver.findElement(By.linkText("Click Here")).click();
        System.out.println("Clicked to open new window");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("Total windows open: " + allWindows.size());

        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                System.out.println("Switched to new window");
                break;
            }
        }

        String newWindowText = driver.findElement(By.tagName("h3")).getText();
        System.out.println("New window heading: " + newWindowText);
        Assert.assertEquals(newWindowText, "New Window");

        driver.close();
        System.out.println("Closed new window");

        driver.switchTo().window(originalWindow);
        System.out.println("Switched back to original window");

        String originalHeading = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(originalHeading, "Opening a new window");

        System.out.println("\nMultiple windows test PASSED\n");
    }

    @Test
    public void demonstrateIframes() {
        System.out.println("\n=== IFRAME HANDLING ===");

        driver.get("https://the-internet.herokuapp.com/iframe");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Masuk ke iframe
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mce_0_ifr")));
        driver.switchTo().frame(iframe);
        System.out.println("Switched to iframe");

        // Pastikan body editor muncul
        WebElement editorBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

        // HAPUS isi dan isi baru menggunakan JS (lebih stabil daripada sendKeys)
        js.executeScript("arguments[0].innerHTML='Testing iframe interaction with Selenium!';", editorBody);
        System.out.println("Entered new text to TinyMCE using JavaScript");

        // Ambil teks setelah update
        String updatedText = (String) js.executeScript("return arguments[0].innerText;", editorBody);

        // Verifikasi
        Assert.assertEquals(updatedText.trim(), "Testing iframe interaction with Selenium!");

        driver.switchTo().defaultContent();
        System.out.println("Switched back to main content");

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));
        Assert.assertEquals(heading.getText(), "An iFrame containing the TinyMCE WYSIWYG Editor");

        System.out.println("\nIframe test PASSED\n");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
