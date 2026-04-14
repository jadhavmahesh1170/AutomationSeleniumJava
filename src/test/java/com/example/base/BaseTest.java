package com.example.base;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.example.utils.ConfigReader;
import com.example.utils.ExtentManager;
import com.example.utils.ScreenshotUtils;

public class BaseTest {

	// ThreadLocal ensures each thread has its own separate driver instance
	protected static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	
	protected static ExtentReports extentReport;

	protected static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
	
	// Helper method to get the correct driver instance for the current thread
	public WebDriver getDriver() {
		return driverThread.get();
	}

	// Helper method to get the correct test instance for the current thread
	public ExtentTest getTest() {
		return testThread.get();
	}
	
	public void setupDriver(String browser) {
        WebDriver driver = DriverFactory.createDriver(browser);
        driverThread.set(driver);
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.get("timeout"))));
        getDriver().manage().window().maximize();
    }

	@BeforeSuite
	public static void setupReport() {
		extentReport = ExtentManager.createInstance();
	}

	@BeforeMethod
	public void setUp(Method method) {
		ExtentTest extentTest = extentReport.createTest(method.getName());
		testThread.set(extentTest);
		System.out.println("Extent test intilaized successfully.");
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			
			// Capture screenshot and attach to ExtentReport
	        String base64Code = ScreenshotUtils.getBase64Screenshot(getDriver());

	        getTest().fail("Test failed... " + result.getThrowable(),
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build());
	        cleanup();
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			getTest().pass("Test passed");
			cleanup();
		} else if (result.getStatus() == ITestResult.SKIP) {
			getTest().skip("Test skipped");
			cleanup();
		}
	}

	@AfterSuite
	public static void flushReport() {
		extentReport.flush();
	}
	
	 private void cleanup() {
	        if (getDriver() != null) {
	            getDriver().quit(); // Final cleanup point
	        }
	    }
}
