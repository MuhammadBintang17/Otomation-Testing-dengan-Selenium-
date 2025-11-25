package com.praktikum.testing.otomation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Create destination path with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destination = System.getProperty("user.dir") +
                    "/src/test/resources/screenshots/" +
                    screenshotName + "_" + timestamp + ".png";

            // Create directory if not exists
            File destFile = new File(destination);
            destFile.getParentFile().mkdirs();

            // Copy file to destination
            FileUtils.copyFile(source, destFile);

            System.out.println("Screenshot saved: " + destination);
            return destination;

        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}