package com.praktikum.testing.otomation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);

        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Selenium UI Testing Report");
        sparkReporter.config().setReportName("Practicum Test Results");
        sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Selenium Version", "4.15.0");
        extent.setSystemInfo("Tested By", "QA Tester");

        return extent;
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance("test-output/ExtentReport.html");
        }
        return extent;
    }
}