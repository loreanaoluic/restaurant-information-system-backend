package com.app.restaurant.e2e.tests;

import com.app.restaurant.e2e.pages.DrinkSuggestionPage;
import com.app.restaurant.e2e.pages.LoginPage;
import com.app.restaurant.e2e.pages.MealSuggestionPage;
import com.app.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DrinkSuggestionTest {
    WebDriver driver;
    LoginPage loginPage;
    DrinkSuggestionPage drinkSuggestion;

    @BeforeMethod
    public void setup() throws IOException {

        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        loginPage = PageFactory.initElements(driver,LoginPage.class);
        driver.manage().window().maximize();
        drinkSuggestion = PageFactory.initElements(driver,DrinkSuggestionPage.class);

    }

    @Test
    public void shouldPerformshouldPerformCreateNewDrinkSuggestionActionAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("nikola");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver,"http://localhost:4200/head-bartender/drinkSuggestion",10);
        assertEquals("http://localhost:4200/head-bartender/drinkSuggestion", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        drinkSuggestion.setItemNameInput("mlijeko");
        drinkSuggestion.setIngredientsInput("");
        drinkSuggestion.setDescriptionInput("2,8 % masti");
        drinkSuggestion.setPreparationTimeInput(2);
        drinkSuggestion.setPriceInput(150);

        drinkSuggestion.submitBtn.click();
    }

    @AfterMethod
    public void closeSelenium() {
        // Shutdown the browser
        //driver.quit();
    }
}
