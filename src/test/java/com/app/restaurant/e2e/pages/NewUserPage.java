package com.app.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewUserPage {

    private WebDriver driver;

    @FindBy(name = "usernameModal")
    private WebElement usernameInput;

    @FindBy(name = "nameModal")
    private WebElement nameInput;

    @FindBy(name = "lastNameModal")
    private WebElement lastnameInput;

    @FindBy(name = "emailAddressModal")
    private WebElement emailAddressInput;

    @FindBy(name = "salaryModal")
    private WebElement salaryInput;

    @FindBy(name = "roleModal")
    private WebElement roleInput;

    @FindBy(name = "passwordModal")
    private WebElement passwordInput;

    @FindBy(name = "password2Modal")
    private WebElement password2Input;

    @FindBy(name = "saveChangesModal")
    public WebElement saveChangesButton;

    public NewUserPage(WebDriver driver) {
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

    public WebElement getNameInput() {
        return Utilities.visibilityWait(driver, this.nameInput, 10);
    }

    public void setNameInput(String value) {
        WebElement el = getNameInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getLastnameInput() {
        return Utilities.visibilityWait(driver, this.lastnameInput, 10);
    }

    public void setLastnameInput(String value) {
        WebElement el = getLastnameInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getEmailAddressInput() {
        return Utilities.visibilityWait(driver, this.emailAddressInput, 10);
    }

    public void setEmailAddressInput(String value) {
        WebElement el = getEmailAddressInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getSalaryInput() {
        return Utilities.visibilityWait(driver, this.salaryInput, 10);
    }

    public void setSalaryInput(String value) {
        WebElement el = getSalaryInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getRoleInput() {
        return Utilities.visibilityWait(driver, this.roleInput, 10);
    }

    public void setRoleInput(String value) {
        WebElement el = getRoleInput();
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

    public WebElement getPassword2Input() {
        return Utilities.visibilityWait(driver, this.password2Input, 10);
    }

    public void setPassword2Input(String value) {
        WebElement el = getPassword2Input();
        el.clear();
        el.sendKeys(value);
    }

    public void saveChangesButtonClick() {
        Utilities.clickableWait(driver, this.saveChangesButton, 10).click();
    }
}
