package com.example.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.example.base.BaseTest;
import com.example.base.ExcelUtils;
import com.example.pages.LoginPage;
import com.example.utils.ConfigReader;

public class ParallelExcelTest extends BaseTest {

    @ParameterizedTest(name = "Login row: {index}")
    @MethodSource("provideExcelData")
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

        assertTrue(isInventoryVisible);
    }
    
    static Stream<Arguments> provideExcelData() {
        return ExcelUtils.getExcelData("src/test/resources/TestData.xlsx", "LoginData");
    }
}
