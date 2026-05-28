package com.banking.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.banking.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // Setup the HTML report file path location
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReports/BankingTestReport.html");
        sparkReporter.config().setReportName("Enterprise Banking Automation Results");
        sparkReporter.config().setDocumentTitle("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA - UAT");
        extent.setSystemInfo("Author", "TCS QA Team");
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test entry in the report when a test starts
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Case PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Case FAILED: " + result.getName());
        test.get().log(Status.FAIL, "Failure Reason: " + result.getThrowable());

        // CRITICAL: Capturing the live driver instance from the running test to take a screenshot
        try {
            Object testClass = result.getInstance();
            WebDriver driver = ((BaseTest) testClass).getDriver();

            if (driver != null) {
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                // Embed the screenshot directly into the HTML report inline
                test.get().addScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot");
            }
        } catch (Exception e) {
            System.out.println("Exception while capturing framework screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Write all logs into the final HTML report file
        if (extent != null) {
            extent.flush();
        }
    }
}