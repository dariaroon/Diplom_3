package ru.roon.diplom3.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.roon.diplom3.pageobjects.MainPage;

import static ru.roon.diplom3.Constant.URL;

public class ConstructorTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get(URL);
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Проверка перехода в раздел 'Соусы'")
    @Description("Проверка успешного перехода в раздел 'Соусы'")
    public void checkActiveSauceInConstructor() {
        mainPage.clickSauce();
        Assert.assertTrue("Соусы не активны", mainPage.isSauceActive());
    }

    @Test
    @DisplayName("Проверка перехода в раздел 'Начинки'")
    @Description("Проверка успешного перехода в раздел 'Начинки'")
    public void checkActiveFillingInConstructor() {
        mainPage.clickFilling();
        Assert.assertTrue("Начинки не активны", mainPage.isFillingActive());
    }

    @Test
    @DisplayName("Проверка перехода в раздел 'Булки'")
    @Description("Проверка успешного перехода в раздел 'Булки'")
    public void checkActiveBunInConstructor() {
        mainPage.clickFilling();
        mainPage.clickBun();
        Assert.assertTrue("Булки не активны", mainPage.isBreadActive());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
