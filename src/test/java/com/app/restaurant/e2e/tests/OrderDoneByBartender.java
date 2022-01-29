package com.app.restaurant.e2e.tests;

import com.app.restaurant.e2e.pages.LoginPage;
import com.app.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class OrderDoneByBartender {
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
    public void shouldPerformShowReportAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("milica");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver,"http://localhost:4200/bartender/orders",10);
        assertEquals("http://localhost:4200/bartender/orders", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> buttonDone = driver.findElements(By.cssSelector("#buttonDone"));
        buttonDone.get(0).click();

    }

    @AfterMethod
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
