package com.example.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.base.BaseTest;
import com.example.base.ExcelReader;
import com.example.pages.LoginPage;
import com.example.utils.ConfigReader;

public class ParallelExcelTest extends BaseTest {

    
    @Test(dataProvider = "excelLoginData")
    public void testLoginWithExcel(String browser, String username, String password) {
    	getTest().info("Starting test with Data: [User: " + username + ", Pass: " + password + "]");
        setupDriver(browser);
    	getDriver().get("https://www.saucedemo.com");
        // Your login steps here
        System.out.println("Testing " + username + " on thread " + Thread.currentThread().getId());
        getTest().info("Navigated to: " + ConfigReader.get("url"));
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);
        // Your Selenium logic here
        System.out.println("Running test for: " + username + " on thread: " + Thread.currentThread().getId());
 
        boolean isInventoryVisible = getDriver().getCurrentUrl().contains("inventory.html");
        
        if(isInventoryVisible) {
            getTest().pass("Login verified successfully for "+ "[User: " + username+"]");
        } else {
            getTest().fail("Login failed for "+"[User: "+username+"]");
        }

        Assert.assertTrue(isInventoryVisible);
    }
    
    // 1. Define the DataProvider with a specific name
    @DataProvider(name = "staticLoginData")
    public Object[][] createStaticLoginData() {
        return new Object[][] {
            {"chrome", "standard_user", "secret_sauce" },  // Row 1: Valid user
            {"edge", "locked_out_user", "secret_sauce" },  // Row 1: Invalid locked-out user
        };
    }
    
 // 1. Define the DataProvider with a specific name
    @DataProvider(name = "excelLoginData")
    public Object[][] readLoginData() throws IOException {
        return ExcelReader.getTestData("src/test/resources/TestData.xlsx", "LoginData");
        
        }
 
}
