package ru.roon.diplom3.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResetPasswordPage {
    private final WebDriver driver;
    private final By loginHref = By.className("Auth_link__1fOlj");
    private final By resetPasswordLabel = By.xpath("//h2[text()='Восстановление пароля']");
    public ResetPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLogin() {
        driver.findElement(loginHref).click();
    }

    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(resetPasswordLabel));
    }
}
