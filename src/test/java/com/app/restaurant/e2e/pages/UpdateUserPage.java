package com.app.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateUserPage {
    private WebDriver driver;

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "name")
    private WebElement nameInput;

    @FindBy(name = "lastName")
    private WebElement lastnameInput;

    @FindBy(name = "emailAddress")
    private WebElement emailAddressInput;

    @FindBy(name = "salary")
    private WebElement salaryInput;

    @FindBy(name = "role")
    private WebElement roleInput;

    @FindBy(name = "saveChanges")
    public WebElement saveChangesButton;

    public UpdateUserPage(WebDriver driver) {
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

    public void saveChangesButtonClick() {
        Utilities.clickableWait(driver, this.saveChangesButton, 10).click();
    }
}
