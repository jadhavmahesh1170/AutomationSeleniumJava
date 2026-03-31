package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import com.example.utils.ConfigReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
    	setupDriver("chrome");
        getDriver().get(ConfigReader.get("url"));
        getTest().info("Navigated to: " + ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        
        boolean isInventoryVisible = getDriver().getCurrentUrl().contains("inventory.html");
        
        if(isInventoryVisible) {
        	getTest().pass("Login verified successfully!");
        } else {
        	getTest().fail("Login failed: Inventory page not reached.");
        }

        assertTrue(isInventoryVisible);
    }
    
    @Test
    public void testUnsuccessfulLogin() {
    	setupDriver("edge");
        getDriver().get(ConfigReader.get("url"));
        getTest().info("Navigated to: " + ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        
        boolean isInventoryVisible = getDriver().getCurrentUrl().contains("inventory.html");
        
        if(isInventoryVisible) {
        	getTest().fail("Verification failed! - User is able to login.");
        } else {
        	getTest().pass("Verifiction passed - not able to loin");
        }

        assertFalse(isInventoryVisible,"Verification failed! - User is able to login.");
    }
}
