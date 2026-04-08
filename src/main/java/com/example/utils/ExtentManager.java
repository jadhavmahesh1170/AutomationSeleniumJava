package com.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extentReport;

    public static ExtentReports createInstance() {
        // Create a unique timestamp: e.g., 2026-03-18_22-50-15
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        //String fileName = "target/Reports/TestReport_" + timestamp + ".html";
        String fileName = "target/Reports/TestReport.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
        
        // Customizing Report Look
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Automation Report - " + timestamp);
        sparkReporter.config().setReportName("Execution Results");

        extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);
        
        // System metadata
        extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReport.setSystemInfo("OS", System.getProperty("os.name"));
        
        return extentReport;
    }
}
