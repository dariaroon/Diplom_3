package ru.roon.diplom3.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.roon.diplom3.Constant;

import static io.restassured.RestAssured.given;

public class UserApi {
    @Step("Удаление пользователя (DELETE /api/auth/user)")
    public static void deleteUser(String accessToken) {
        given()
            .header("Authorization", accessToken)
            .when()
            .delete(Constant.API_USER_DELETE);
    }

    @Step("Авторизация пользователя (POST /api/auth/login)")
    public static Response loginUser(UserPojo userPojo) {
        return given()
            .and()
            .body(userPojo)
            .when()
            .post(Constant.API_USER_AUTH);
    }
    @Step("Создание пользователя POST /api/auth/register")
    public static void createUser(UserPojo userPojo){
        given()
            .body(userPojo)
            .when()
            .post(Constant.API_USER_CREATE);
    }
}
