package ru.roon.diplom3.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.roon.diplom3.api.UserApi;
import ru.roon.diplom3.api.UserPojo;
import ru.roon.diplom3.pageobjects.LoginPage;
import ru.roon.diplom3.pageobjects.MainPage;
import ru.roon.diplom3.pageobjects.PersonalPage;

import java.util.UUID;

import static ru.roon.diplom3.Constant.URL;

public class PersonalAccountTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private PersonalPage personalPage;
    private String email;
    private String password;

    @Before
    public void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri(URL)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get(URL);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        personalPage = new PersonalPage(driver);

        //создаем нового пользователя
        String name = UUID.randomUUID().toString();
        email = name + "@yandex.ru";
        password = "123456";
        UserPojo userPojo = new UserPojo(email, password, name);
        UserApi.createUser(userPojo);

        //Выполняем вход
        mainPage.clickLogin();
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
    }

    @Test
    @DisplayName("Перехо∂д по клику на «Личный кабинет».")
    @Description("Проверка успешного перехо∂да по клику на «Личный кабинет».")
    public void testEnterPersonalAccount() {
        mainPage.clickPersonalAccount();
        personalPage.waitForLoad();
        Assert.assertTrue("Вход в личный кабинет не выполнен", personalPage.isProfileButtonVisible());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")
    @Description("Проверка успешного перехо∂да из личного кабинета в конструктор по клику на «Конструктор»")
    public void testEnterConstructorByConstructorButton() {
        mainPage.clickPersonalAccount();
        personalPage.waitForLoad();
        personalPage.clickConstructor();
        mainPage.waitForLoad();
        Assert.assertTrue("Переход в конструктор не произошел", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    @Description("Проверка успешного перехо∂да из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void testEnterConstructorByLogo() {
        mainPage.clickPersonalAccount();
        personalPage.waitForLoad();
        personalPage.clickLogo();
        mainPage.waitForLoad();
        Assert.assertTrue("Переход в конструктор не произошел", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка выхода по кнопке Выйти")
    @Description("Проверка успешного выхода из личного кабинета по кнопке Выйти")
    public void testLogoutByButtonLogout() {
        mainPage.clickPersonalAccount();
        personalPage.waitForLoad();
        personalPage.clickLogout();
        loginPage.waitForLoad();
        Assert.assertTrue("Выход не произошел", loginPage.isEnterLabelVisible());
    }

    @After
    public void tearDown() {
        String accessToken = UserApi.loginUser(new UserPojo(email, password))
            .then()
            .extract()
            .path("accessToken");
        if (accessToken != null) {
            UserApi.deleteUser(accessToken);
        }
        driver.quit();
    }
}
