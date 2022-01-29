package com.app.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MealSuggestionPage {
    private WebDriver driver;

    @FindBy(name = "itemName")
    private WebElement itemNameInput;

    @FindBy(name = "ingredients")
    private WebElement ingredientsInput;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(name = "price")
    private WebElement priceInput;

    @FindBy(name = "preparationTime")
    private WebElement preparationTimeInput;

    @FindBy(name = "submit")
    public WebElement submitBtn;

    public MealSuggestionPage(WebDriver driver) {
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

    public WebElement getPreparationTimeInput() {
        return Utilities.visibilityWait(driver, this.preparationTimeInput, 10);
    }

    public void setPreparationTimeInput(int value) {
        WebElement el = getPreparationTimeInput();
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

    public void submitBtnClick() {
        Utilities.clickableWait(driver, this.submitBtn, 10).click();
    }

}
