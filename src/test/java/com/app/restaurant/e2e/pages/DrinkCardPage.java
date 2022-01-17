package com.app.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DrinkCardPage {
    private WebDriver driver;

    @FindBy(name = "itemName")
    private WebElement itemNameInput;

    @FindBy(name = "ingredients")
    private WebElement ingredientsInput;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(name = "price")
    private WebElement priceInput;

    @FindBy(name = "saveChanges")
    public WebElement saveChangesButton;

    @FindBy(name = "deleteCard")
    public WebElement deleteCardButton;

    public DrinkCardPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getItemNameInput() {
        return Utilities.visibilityWait(driver, this.itemNameInput, 10);
    }

    public void setItemNameInput(String value) {
        WebElement el = getItemNameInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getDescriptionInput() {
        return Utilities.visibilityWait(driver, this.descriptionInput, 10);
    }

    public void setDescriptionInput(String value) {
        WebElement el = getDescriptionInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getPriceInput() {
        return Utilities.visibilityWait(driver, this.priceInput, 10);
    }

    public void setPriceInput(int value) {
        WebElement el = getPriceInput();
        el.clear();
        el.sendKeys(String.valueOf(value));
    }

    public WebElement getIngredientsInput() {
        return Utilities.visibilityWait(driver, this.ingredientsInput, 10);
    }

    public void setIngredientsInput(String value) {
        WebElement el = getIngredientsInput();
        el.clear();
        el.sendKeys(value);
    }

    public void saveChangesButtonClick() {
        Utilities.clickableWait(driver, this.saveChangesButton, 10).click();
    }

    public void deleteCardButtonClick() {
        Utilities.clickableWait(driver, this.deleteCardButton, 10).click();
    }
}
