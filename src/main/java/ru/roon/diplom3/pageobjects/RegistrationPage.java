package ru.roon.diplom3.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    //Надпись Регистрация
    private final By registerLabel = By.xpath("//h2[text()='Регистрация']");
    //Поле Имя
    private final By nameField = By.xpath("//label[text()='Имя']/../input");
    //Поле Email
    private final By emailField = By.xpath("//label[text()='Email']/../input");
    //Поле Пароль
    private final By passwordField = By.xpath("//label[text()='Пароль']/../input");
    //Кнопка Зарегистрироваться
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    //Кнопка Войти
    private final By loginButton = By.xpath("//a[@href='/login']");
    //Надпись Некорректный пароль
    private final By incorrectPasswordLabel = By.xpath("//p[@class='input__error text_type_main-default']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки страницы Регистрации")
    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(registerLabel));
    }

    @Step("Заполнение формы регистрации")
    public void fillRegistrationForm(String name, String email, String password) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    @Step("Проверка видимости надписи 'Неправильный пароль'")
    public boolean isIncorrectPasswordLabelVisible() {
        return driver.findElement(incorrectPasswordLabel).isDisplayed();
    }

    @Step("Клик на кнопку 'Войти'")
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}

