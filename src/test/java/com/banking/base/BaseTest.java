package com.banking.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public class BaseTest {

    // Protected allows child Test classes to access this driver instance directly
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.out.println(">>> Setting up the browser environment...");

        // Advanced Option: Configuring Chrome to avoid bot detection and popups
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        // Initializing the ChromeDriver instance using Selenium 4 native management
        driver = new ChromeDriver(options);

        // Smart Implicit Wait: Fallback layer for element synchronization
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        System.out.println(">>> Tearing down the browser environment...");
        if (driver != null) {
            driver.quit(); // Closes all browser windows and safely terminates the session
        }
    }

    // Public getter method so the Extent Listener can safely borrow the active browser instance for screenshots
    public WebDriver getDriver() {
        return this.driver;
    }
}