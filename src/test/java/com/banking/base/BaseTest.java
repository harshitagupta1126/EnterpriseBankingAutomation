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

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        // This checks if the code is running inside GitHub Actions cloud
        // If it finds the cloud environment, it automatically forces Chrome to run invisibly!
        if (System.getenv("GITHUB_ACTIONS") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            // Forces Chrome to render a standard desktop window layout in memory
            options.addArguments("--window-size=1920,1080");
            System.out.println(">>> Cloud Environment Detected: Running Chrome in Headless Mode.");
        }

        driver = new ChromeDriver(options);
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