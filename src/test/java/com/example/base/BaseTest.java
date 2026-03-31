package com.example.base;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.example.utils.ConfigReader;
import com.example.utils.ExtentManager;

// This line connects the watcher to all your tests
@ExtendWith(TestResultWatcher.class)
public class BaseTest {

	// ThreadLocal ensures each thread has its own separate driver instance
	protected static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

	protected static ExtentReports extentReport;
	protected static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

	// Added getter so the Watcher can access the driver
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

	@BeforeAll
	public static void setupReport() {
		extentReport = ExtentManager.createInstance();
	}

	@BeforeEach
	public void setUp(TestInfo testInfo) {
		ExtentTest extentTest = extentReport.createTest(testInfo.getDisplayName());
		testThread.set(extentTest);
	}

	@AfterEach
	public void tearDown() {

		/*
		 * if (getDriver() != null) { getDriver().quit();
		 * System.out.println("Driver.quit() has been called."); }
		 */

	}

	@AfterAll
	public static void flushReport() {
		extentReport.flush();
	}
}
