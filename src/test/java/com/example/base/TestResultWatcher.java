package com.example.base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.example.utils.ScreenshotUtils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;


public class TestResultWatcher implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // Get the driver instance from the BaseTest class
        Object testInstance = context.getRequiredTestInstance();
        BaseTest baseTest = (BaseTest) testInstance;

        // Capture screenshot and attach to ExtentReport
        String base64Code = ScreenshotUtils.getBase64Screenshot(baseTest.getDriver());
        baseTest.getTest().fail("Test Failed: " + cause.getMessage(),
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build());
        cleanup(baseTest);
    }
    
    @Override
    public void testSuccessful(ExtensionContext context) {
        BaseTest baseTest = (BaseTest) context.getRequiredTestInstance();
        baseTest.getTest().pass("Test Passed");
        cleanup(baseTest);
    }

    private void cleanup(BaseTest baseTest) {
        if (baseTest.getDriver() != null) {
            baseTest.getDriver().quit(); // Final cleanup point
        }
    }
}
