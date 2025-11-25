package com.praktikum.testing.otomation.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.praktikum.testing.otomation.utils.ExtentReportManager;
import com.praktikum.testing.otomation.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void setUpSuite() {
        extent = ExtentReportManager.createInstance("test-output/ExtentReport.html");
        System.out.println("=== TEST SUITE STARTED ===\n");
    }

    @BeforeMethod
    public void setUp(Method method) {
        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Create test in report
        test = extent.createTest(method.getName());
        test.log(Status.INFO, "Test started: " + method.getName());
        test.log(Status.INFO, "Browser launched: Chrome");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log test result
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());

            // Capture screenshot on failure
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName() + "_FAILED");

            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        } else {
            test.log(Status.PASS, "Test Passed");
        }

        // Quit browser
        if (driver != null) {
            driver.quit();
            test.log(Status.INFO, "Browser closed");
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
        System.out.println("\n=== TEST SUITE COMPLETED ===");
        System.out.println("Report generated: test-output/ExtentReport.html\n");
    }
}