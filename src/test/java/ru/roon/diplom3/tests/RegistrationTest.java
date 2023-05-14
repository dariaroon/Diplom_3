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

import java.util.UUID;

import static ru.roon.diplom3.Constant.URL;
import static ru.roon.diplom3.Constant.URL_LOGIN;
import static ru.roon.diplom3.Constant.URL_REGISTER;

public class RegistrationTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private String name;
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
        driver.get(URL_REGISTER);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации")
    public void testRegisterNewUser() {
        name = UUID.randomUUID().toString();
        email = name + "@yandex.ru";
        password = "123456";
        registrationPage.waitForLoad();
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.clickRegister();
        loginPage.waitForLoad();
        driver.get(URL_LOGIN);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
        Assert.assertTrue("Регистрация не произошла", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Регистрация с неккоректным паролем")
    @Description("Проверка ввода некорректного пароля")
    public void testErrorWrongPassword() {
        name = UUID.randomUUID().toString();
        email = name + "@yandex.ru";
        password = "12345";
        registrationPage.waitForLoad();
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.clickRegister();
        Assert.assertTrue("Ошибка о некорректном пароле не появилась",
            registrationPage.isIncorrectPasswordLabelVisible());
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
