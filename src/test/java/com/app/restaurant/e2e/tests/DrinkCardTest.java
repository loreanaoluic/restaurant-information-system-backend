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

public class DrinkCardTest {

    WebDriver driver;
    LoginPage loginPage;
    DrinkCardPage drinkCardPage;

    @BeforeMethod
    public void setup() throws IOException {

        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        loginPage = PageFactory.initElements(driver,LoginPage.class);
        drinkCardPage = PageFactory.initElements(driver,DrinkCardPage.class);
        driver.manage().window().maximize();

    }

    @Test
    public void shouldPerformUpdateDrinkCardAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver,"http://localhost:4200/manager/employees",10);
        assertEquals("http://localhost:4200/manager/employees", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement drinkCard=driver.findElement(By.xpath("//a[@href='manager/drinkCard']"));
        drinkCard.click();

        Utilities.urlWait(driver,"http://localhost:4200/manager/drinkCard",10);
        assertEquals("http://localhost:4200/manager/drinkCard", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        Actions actions = new Actions(driver);
        WebElement target = driver.findElement(By.cssSelector("#productImage"));
        actions.moveToElement(target).perform();

        List<WebElement> editButtons = driver.findElements(By.cssSelector("#editDrink"));
        Utilities.clickableWait(driver, editButtons.get(0), 10).click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        drinkCardPage.setItemNameInput("Espresso");
        drinkCardPage.setDescriptionInput("description2");
        drinkCardPage.setIngredientsInput("ingridient");
        drinkCardPage.setPriceInput(120);

        drinkCardPage.saveChangesButtonClick();
    }

    @AfterMethod
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
