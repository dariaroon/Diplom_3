package ru.roon.diplom3.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalPage {
    private final WebDriver driver;
    //Кнопка Конструктор
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    //Логотип
    private final By logoButton = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']/a");
    //Кнопка Профиль
    private final By profileButton = By.xpath("//a[text()='Профиль']");
    //Кнопка Выход
    private final By logoutButton = By.xpath("//li[@class='Account_listItem__35dAP']/button");

    public PersonalPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки главной страницы")
    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(profileButton));
    }

    @Step("Проверка видимости кнопки 'Профиль'")
    public boolean isProfileButtonVisible() {
        return driver.findElement(profileButton).isDisplayed();
    }

    @Step("Клик на кнопку 'Конструктор'")
    public void clickConstructor() {
        driver.findElement(constructorButton).click();
    }

    @Step("Клик на логотип")
    public void clickLogo() {
        driver.findElement(logoButton).click();
    }

    @Step("Клик на 'Выйти'")
    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }
}
