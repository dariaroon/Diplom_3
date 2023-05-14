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
import ru.roon.diplom3.pageobjects.RegistrationPage;
import ru.roon.diplom3.pageobjects.ResetPasswordPage;

import java.util.UUID;

import static ru.roon.diplom3.Constant.URL;
import static ru.roon.diplom3.Constant.URL_FORGOT_PASSWORD;
import static ru.roon.diplom3.Constant.URL_REGISTER;

public class LoginTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private ResetPasswordPage resetPasswordPage;
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
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        resetPasswordPage = new ResetPasswordPage(driver);
        //создаем нового пользователя
        String name = UUID.randomUUID().toString();
        email = name + "@yandex.ru";
        password = "123456";
        UserPojo userPojo = new UserPojo(email, password, name);
        UserApi.createUser(userPojo);
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    @Description("Проверка успешного входа по кнопке «Войти в аккаунт» на главной странице")
    public void testLoginOnMainPage() {
        driver.get(URL);
        mainPage.clickLogin();
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    @Description("Проверка успешного входа через кнопку «Личный кабинет»")
    public void checkLoginByPersonalButton() {
        driver.get(URL);
        mainPage.clickPersonalAccount();
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка успешного входа через кнопку в форме регистрации")
    public void checkLoginOnRegistrationPage() {
        driver.get(URL_REGISTER);
        registrationPage.waitForLoad();
        registrationPage.clickLogin();
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка успешного входа через кнопку в форме восстановления пароля")
    public void testLoginOnResetPasswordPage() {
        driver.get(URL_FORGOT_PASSWORD);
        resetPasswordPage.waitForLoad();
        resetPasswordPage.clickLogin();
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
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
