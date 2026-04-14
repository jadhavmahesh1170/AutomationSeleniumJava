package com.example.base;

import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	public static WebDriver createDriver(String browser) {

		WebDriver driver;

		switch (browser.toLowerCase()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			// Excludes the 'enable-automation' switch
			chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			// Prevents the "use automation extension" popup
			chromeOptions.setExperimentalOption("useAutomationExtension", false);
			
			driver = new ChromeDriver(chromeOptions);
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			//options.addArguments("--headless=new"); // Run without a GUI
			edgeOptions.addArguments("--no-sandbox"); // Bypass OS security model
			edgeOptions.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
			edgeOptions.addArguments("--remote-allow-origins=*"); // Allow connections
			// Add this line specifically for Edge in Jenkins
			edgeOptions.addArguments("--remote-debugging-port=9222");
			edgeOptions.addArguments("--disable-gpu");
			//Disable popup - Browser is being controlled by automated test software
			edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			edgeOptions.setExperimentalOption("useAutomationExtension", false);
			driver = new EdgeDriver(edgeOptions);

			break;
		default:
			driver = new ChromeDriver();
			System.out.println("Since no browser specified, defaulting to Chrome browser");
			break;
		}

		return driver;
	}

}
