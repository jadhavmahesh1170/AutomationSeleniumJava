package com.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	public static WebDriver createDriver(String browser) {

		WebDriver driver;

		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--headless=new"); // Run without a GUI
			options.addArguments("--no-sandbox"); // Bypass OS security model
			options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
			options.addArguments("--remote-allow-origins=*"); // Allow connections
			driver = new EdgeDriver(options);

			break;
		default:
			driver = new ChromeDriver();
			System.out.println("Since no browser specified, defaulting to Chrome browser");
			break;
		}

		return driver;
	}

}
