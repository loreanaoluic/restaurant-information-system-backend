package com.app.restaurant.e2e.tests;

import com.app.restaurant.e2e.pages.LoginPage;
import com.app.restaurant.e2e.pages.NewUserPage;
import com.app.restaurant.e2e.pages.UpdateUser;
import com.app.restaurant.e2e.pages.Utilities;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

public class managerHomePage {

    WebDriver driver;
    NewUserPage newUserPage;
    LoginPage loginPage;
    UpdateUser updateUser;

    @BeforeMethod
    public void setup() throws IOException {

        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        newUserPage= PageFactory.initElements(driver,NewUserPage.class);
        loginPage= PageFactory.initElements(driver,LoginPage.class);
        updateUser= PageFactory.initElements(driver,UpdateUser.class);
    }

    @Test
    public void shouldPerformAddUserAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        Utilities.urlWait(driver,"http://localhost:4200/manager/employees",10);
        assertEquals("http://localhost:4200/manager/employees", driver.getCurrentUrl());

        WebElement newEmployeeBtn = driver.findElement(By.name("newEmployeeBtn"));
        Utilities.clickableWait(driver, newEmployeeBtn, 10).click();

        newUserPage.setUsernameInput("mirko");
        newUserPage.setNameInput("mirko");
        newUserPage.setLastnameInput("zmirko");
        newUserPage.setEmailAddressInput("mirko@gmail.com");
        newUserPage.setSalaryInput("21000");
        newUserPage.setRoleInput("Waiter");
        newUserPage.setPasswordInput("1234");
        newUserPage.setPassword2Input("1234");

        newUserPage.saveChangesButtonClick();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        List<WebElement> myAccountLink =driver.findElements(By.name("UsernameInTable"));
        Assert.assertTrue(myAccountLink.stream().anyMatch(e->e.getText().trim().equals("mirko")));

    }

    @Test
    public void shouldPerformDeleteUserAction() {
        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement deleteButton=driver.findElement(By.xpath("//*[@id='employeeTable']/td[@name='UsernameInTable'][.='mirko']/following-sibling::td[6]//button"));
        deleteButton.click();

        List<WebElement> left = driver.findElements(By.xpath("//*[@id='employeeTable']/td[@name='UsernameInTable']"));
        assertThat(left.size()==6);
    }

    @Test
    public void shouldPerformUpdateUserAction() {

        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        //isto samo sa xPath-om
        //WebElement UpdateEmployeeBtn=driver.findElement(By.xpath("//*[@id='employeeTable']/td[@name='UsernameInTable'][.='ana']/following-sibling::td[5]//button"));
        //UpdateEmployeeBtn.click();
        List<WebElement> UpdateEmployeeBtn = driver.findElements(By.cssSelector("#update"));
        Utilities.clickableWait(driver, UpdateEmployeeBtn.get(UpdateEmployeeBtn.size()-1), 10).click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        updateUser.setUsernameInput("ana");
        updateUser.setNameInput("ana");
        updateUser.setLastnameInput("mitrovic");
        updateUser.setEmailAddressInput("ana@gmail.com");
        updateUser.setSalaryInput("29000");
        updateUser.setRoleInput("Waiter");

        newUserPage.saveChangesButtonClick();
    }

    @Test
    public void shouldPerformSearchUserAction() {
        driver.get("http://localhost:4200/login");
        loginPage.setUsernameInput("dusan");
        loginPage.setPasswordInput("1234");

        loginPage.submitBtnClick();
        loginPage.submitBtnClick();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement search = driver.findElement(By.cssSelector("#search"));
        search.sendKeys("loreana");

        List<WebElement> searchResults = driver.findElements(By.name("UsernameInTable"));

        assertThat(searchResults.size()==1);
    }

    @AfterMethod
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
