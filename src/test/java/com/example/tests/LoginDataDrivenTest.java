package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import com.example.utils.ConfigReader;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.*;

public class LoginDataDrivenTest extends BaseTest {

    @ParameterizedTest(name = "Login Test with user: {0}")
    @MethodSource("loginDataProvider")
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

        assertTrue(isInventoryVisible);
    }

    // This is your data source (could be from Excel/JSON later)
    static Stream<Arguments> loginDataProvider() {
        return Stream.of(
            arguments("chrome","standard_user", "secret_sauce"),
            arguments("edge", "locked_out_user", "secret_sauce"),
            arguments("chrome","problem_user", "secret_sauce"),
            arguments("edge","performance_glitch_user", "secret_sauce")
        );
    }
}
