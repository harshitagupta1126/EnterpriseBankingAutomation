package com.banking.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ElementUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor initializes the driver and sets a default 15-second explicit wait timeout
    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        // Increasing timeout to 30 seconds to absorb cloud network latencies cleanly
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * An explicit wait method that pauses until an element is completely visible on the UI.
     * This eliminates flakiness caused by slow network or page loading speeds.
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * An explicit wait method that ensures a button is fully clickable before the robot attempts an action.
     */
    public void clickWhenReady(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Safely sends keys to a text box after ensuring it is visible on the screen.
     */
    public void sendKeysAfterWaiting(By locator, String text) {
        waitForElementToBeVisible(locator).sendKeys(text);
    }
}