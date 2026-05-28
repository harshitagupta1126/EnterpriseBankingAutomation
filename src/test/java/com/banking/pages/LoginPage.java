package com.banking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.banking.utils.ElementUtils;

public class LoginPage {

    private WebDriver driver;
    private ElementUtils elementUtils; // Declaring our utility object

    // Private Locators
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//input[@value='Log In']");
    private By errorMessage = By.className("error");

    // Constructor initializes both the driver and the element utilities
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
    }

    // Public Action Methods calling our smart wait utilities
    public void enterUsername(String username) {
        elementUtils.sendKeysAfterWaiting(usernameField, username);
    }

    public void enterPassword(String password) {
        elementUtils.sendKeysAfterWaiting(passwordField, password);
    }

    public void clickLogin() {
        elementUtils.clickWhenReady(loginButton);
    }

    public String getErrorMessageText() {
        return elementUtils.waitForElementToBeVisible(errorMessage).getText();
    }
}