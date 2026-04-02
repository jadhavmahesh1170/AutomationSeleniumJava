package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import com.example.utils.ConfigReader;


public class LoginDataDrivenTest extends BaseTest {

    @Test(dataProvider ="loginDataProvider")
    public void testLoginParallel(String browser, String username, String password) {
    	setupDriver(browser);
    	getDriver().get(ConfigReader.get("url"));
        getTest().info("Navigated to: " + ConfigReader.get("url"));
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);
        // Your Selenium logic here
        System.out.println("Running test for: " + username + " on thread: " + Thread.currentThread().getId());
 
        boolean isInventoryVisible = getDriver().getCurrentUrl().contains("inventory.html");
        
        if(isInventoryVisible) {
            getTest().pass("Login verified successfully!");
        } else {
            getTest().fail("Login failed: Inventory page not reached.");
        }

        Assert.assertTrue(isInventoryVisible);
    }

    // This is your data source (could be from Excel/JSON later)
    @DataProvider(name ="loginDataProvider")
    static Object[][] loginDataProvider() {
    	return new Object[][] {
            {"chrome", "standard_user", "secret_sauce"},  // Row 1: Valid user
            {"edge", "locked_out_user", "secret_sauce"},  // Row 1: Invalid locked-out user
        };
    }
}
