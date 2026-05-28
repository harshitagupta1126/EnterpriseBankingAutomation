package com.banking.tests;

import com.banking.base.BaseTest;
import com.banking.pages.LoginPage;
import com.banking.utils.ExcelUtil;
import com.banking.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;

@Listeners(ExtentReportManager.class)
public class LoginTest extends BaseTest {

    /**
     * The DataProvider annotation fetches the array of data from ExcelUtil
     * and maps it dynamically to our test execution thread.
     */
    @DataProvider(name = "BankingLoginData")
    public Object[][] supplyData() throws IOException {
        String excelPath = "src/test/resources/TestData.xlsx";
        // "Sheet1" is the default name when you create an Excel sheet
        return ExcelUtil.getExcelData(excelPath, "Sheet1");
    }

    // We link the test to our DataProvider. TestNG will run this test automatically
    // as many times as there are data rows in your Excel file!
    @Test(dataProvider = "BankingLoginData")
    public void verifyInvalidLoginErrorMessage(String excelUsername, String excelPassword) {
        System.out.println(">>> Executing Data-Driven Run for User: " + excelUsername);

        // Force the driver to freshly load the page and clear any previous states
        driver.manage().deleteAllCookies();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        LoginPage loginPage = new LoginPage(driver);

        // Enter data from current Excel row
        loginPage.enterUsername(excelUsername);
        loginPage.enterPassword(excelPassword);
        loginPage.clickLogin();

        // Capture and validate the error text
        String actualErrorMessage = loginPage.getErrorMessageText();
        System.out.println("Successfully captured error for: " + excelUsername);

        String expectedErrorMessage = "The username and password could not be verified.";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message text mismatch!");
    }
}