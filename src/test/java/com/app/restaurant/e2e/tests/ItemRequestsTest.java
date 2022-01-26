package com.app.restaurant.e2e.tests;

import com.app.restaurant.e2e.pages.DrinkCardPage;
import com.app.restaurant.e2e.pages.LoginPage;
import com.app.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ItemRequestsTest {

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
    public void shouldPerformAcceptItemRequestAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver, "http://localhost:4200/manager/employees", 10);
        assertEquals("http://localhost:4200/manager/employees", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement itemRequests = driver.findElement(By.xpath("//a[@href='manager/itemRequests']"));
        itemRequests.click();

        Utilities.urlWait(driver,"http://localhost:4200/manager/itemRequests",10);
        assertEquals("http://localhost:4200/manager/itemRequests", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> buttons = driver.findElements(By.className("btn"));
        Utilities.clickableWait(driver, buttons.get(2), 10).click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> newButtons = driver.findElements(By.className("btn"));
        assertEquals(7, newButtons.size());
    }

    @Test
    public void shouldPerformDeclineItemRequestAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver, "http://localhost:4200/manager/employees", 10);
        assertEquals("http://localhost:4200/manager/employees", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement itemRequests = driver.findElement(By.xpath("//a[@href='manager/itemRequests']"));
        itemRequests.click();

        Utilities.urlWait(driver,"http://localhost:4200/manager/itemRequests",10);
        assertEquals("http://localhost:4200/manager/itemRequests", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> buttons = driver.findElements(By.className("btn"));
        Utilities.clickableWait(driver, buttons.get(3), 10).click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> newButtons = driver.findElements(By.className("btn"));
        assertEquals(4, newButtons.size());
    }

    @AfterMethod
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
