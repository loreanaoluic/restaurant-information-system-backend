package com.app.restaurant.e2e.tests;

import com.app.restaurant.e2e.pages.LoginPage;
import com.app.restaurant.e2e.pages.Utilities;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ActionsTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() throws IOException {

        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage= PageFactory.initElements(driver,LoginPage.class);
    }

    @Test
    public void shouldPerformLogInAction() {

        driver.get("http://localhost:4200/login");

        assertEquals("http://localhost:4200/login",
                driver.getCurrentUrl());

        loginPage.submitBtnClick();
        //potrebno je uhvatiti toast i provjeriti da li se pojavio ili provjeriti status odgovora a ne ovako :D
        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());

        loginPage.setUsernameInput("dusan antic");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        //potrebno je uhvatiti toast i provjeriti da li se pojavio ili provjeriti status odgovora a ne ovako :D
        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());


        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver,"http://localhost:4200/manager/employees",10);
        assertEquals("http://localhost:4200/manager/employees", driver.getCurrentUrl());

    }

    @AfterTest
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
