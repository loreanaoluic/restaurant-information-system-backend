package com.app.restaurant.e2e.tests;

import com.app.restaurant.e2e.pages.FoodMenuPage;
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

public class FoodMenuTest {


    WebDriver driver;
    LoginPage loginPage;
    FoodMenuPage foodMenuPage;

    @BeforeMethod
    public void setup() throws IOException {

        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        loginPage= PageFactory.initElements(driver,LoginPage.class);
        foodMenuPage= PageFactory.initElements(driver,FoodMenuPage.class);
        driver.manage().window().maximize();

    }

    @Test
    public void shouldPerformUpdateFoodIteamAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver,"http://localhost:4200/manager/employees",10);
        assertEquals("http://localhost:4200/manager/employees", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement foodCard=driver.findElement(By.xpath("//a[@href='manager/menu']"));
        foodCard.click();

        Utilities.urlWait(driver,"http://localhost:4200/manager/menu",10);
        assertEquals("http://localhost:4200/manager/menu", driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        Actions actions = new Actions(driver);
        WebElement target = driver.findElement(By.cssSelector("#productImage"));
        actions.moveToElement(target).perform();

        List<WebElement> editButtons = driver.findElements(By.cssSelector("#editFood"));
        Utilities.clickableWait(driver, editButtons.get(0), 10).click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        foodMenuPage.setItemNameInput("Carbonara paste");
        foodMenuPage.setDescriptionInput("description2");
        foodMenuPage.setIngredientsInput("ingridient");
        foodMenuPage.setPriceInput(1400);
        foodMenuPage.setPreparationTimeInput(120);

        foodMenuPage.saveChangesButtonClick();
    }

    @AfterMethod
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
