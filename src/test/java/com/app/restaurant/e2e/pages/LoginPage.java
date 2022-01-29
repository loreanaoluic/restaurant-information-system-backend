package com.app.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

    private WebDriver driver;

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(name = "submit")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameInput() {
        return Utilities.visibilityWait(driver, this.usernameInput, 10);
    }

    public void setUsernameInput(String value) {
        WebElement el = getUsernameInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getPasswordInput() {
        return Utilities.visibilityWait(driver, this.passwordInput, 10);
    }

    public void setPasswordInput(String value) {
        WebElement el = getPasswordInput();
        el.clear();
        el.sendKeys(value);
    }

    public void submitBtnClick() {
        Utilities.clickableWait(driver, this.submitButton, 10).click();
    }


}
