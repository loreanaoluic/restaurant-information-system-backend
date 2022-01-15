package com.app.restaurant.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class ActionsTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() throws IOException {

        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    //TEST METODA NIJE VEZANA ZA NAS PROJEKAT SAMO DA SVAKO POKRENE DA VIDI DA LI RADE DEPENDESI
    @Test
    public void shouldPerformCompositeAction() {

        driver.get("http://localhost:4200/login");

        WebElement username = driver.findElement(By.xpath("//*[@id='username']"));
        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));

        username.sendKeys("dusan");
        password.sendKeys("1234");
    }
}
