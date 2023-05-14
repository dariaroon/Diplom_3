package ru.roon.diplom3.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private static final String CLASS_ACTIVE_BUTTON = "tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect";
    private final WebDriver driver;
    private final By parent = By.xpath("..");
    //Кнопка "Войти в аккаунт"
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    //Кнопка "Личный кабинет"
    private final By personalAccountButton = By.linkText("Личный Кабинет");
    //Кнопка "Булки"
    private final By breadButton = By.xpath("//span[text()='Булки']");
    //Кнопка "Начинки"
    private final By fillingButton = By.xpath("//span[text()='Начинки']");
    //Кнопка "Соусы"
    private final By sauceButton = By.xpath("//span[text()='Соусы']");
    //Надпись Соберите бургер
    private final By makeBurgerLabel = By.tagName("h1");
    //Кнопка Оформить заказ
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по кнопке 'Войти в аккаунт' на главной странице")
    public void clickLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }

    @Step("Ожидание загрузки главной страницы")
    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(makeBurgerLabel));
    }

    @Step("Проверка видимости кнопки 'Оформить заказ'")
    public boolean isOrderButtonVisible() {
        return driver.findElement(orderButton).isDisplayed();
    }

    @Step("Клик на кнопку 'Личный кабинет'")
    public void clickPersonalAccount() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Клик на 'Соусы'")
    public void clickSauce() {
        driver.findElement(sauceButton).click();
    }

    @Step("Проверка активности кнопки 'Соусы'")
    public boolean isSauceActive() {
        return driver.findElement(sauceButton).findElement(parent).getAttribute("class").equals(CLASS_ACTIVE_BUTTON);
    }

    @Step("Клик на 'Начинки'")
    public void clickFilling() {
        driver.findElement(fillingButton).click();
    }

    @Step("Проверка активности кнопки 'Начинки'")
    public boolean isFillingActive() {
        return driver.findElement(fillingButton).findElement(parent).getAttribute("class").equals(CLASS_ACTIVE_BUTTON);
    }

    @Step("Клик на 'Булки'")
    public void clickBun() {
        driver.findElement(breadButton).click();
    }

    @Step("Проверка активности кнопки 'Булки'")
    public boolean isBreadActive() {
        return driver.findElement(breadButton).findElement(parent).getAttribute("class").equals(CLASS_ACTIVE_BUTTON);
    }

}
