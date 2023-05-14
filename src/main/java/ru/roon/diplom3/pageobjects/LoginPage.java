package ru.roon.diplom3.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    //Форма логина
    private final By authForm = By.cssSelector(".Auth_form__3qKeq");

    //Надпись Вход
    private final By enterLabel = By.xpath("//h2[text()='Вход']");
    //Поле Email
    private final By emailField = By.xpath(".//label[text()='Email']/parent::div/input");
    //Поле Пароль
    private final By passwordField = By.xpath(".//input[@type='password']");
    //Кнопка Войти
    private final By loginButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;

    }

    @Step("Ожидание загрузки страницы авторизации")
    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(authForm));
    }

    @Step("Заполнение формы авторизации")
    public void fillLoginForm(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик на кнопку 'Войти'")
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    @Step("Проверка видимости надписи 'Войти'")
    public boolean isEnterLabelVisible() {
        return driver.findElement(enterLabel).isDisplayed();
    }
}
