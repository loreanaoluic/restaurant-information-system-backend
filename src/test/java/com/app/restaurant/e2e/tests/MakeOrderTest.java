package com.app.restaurant.e2e.tests;

import com.app.restaurant.e2e.pages.LoginPage;
import com.app.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MakeOrderTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() throws IOException {

        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        loginPage = PageFactory.initElements(driver,LoginPage.class);
        driver.manage().window().maximize();

    }

    @Test
    public void shouldPerformShowReportAction() throws InterruptedException {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("ana");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver,"http://localhost:4200/waiter/newOrder",10);
        assertEquals("http://localhost:4200/waiter/newOrder", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> makeOrder = driver.findElements(By.cssSelector("#makeOrderButton"));
        makeOrder.get(0).click();

        List<WebElement> orderButton = driver.findElements(By.cssSelector("#orderButton"));
        orderButton.get(0).click();

        List<WebElement> receiptStep = driver.findElements(By.className("mat-step-text-label"));
        receiptStep.get(2).click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement receiptDone = driver.findElement(By.cssSelector("#doneReceipt"));
        receiptDone.click();

    }

    @AfterMethod
    public void closeSelenium() {
        // Shutdown the browser
        //driver.quit();
    }
}
