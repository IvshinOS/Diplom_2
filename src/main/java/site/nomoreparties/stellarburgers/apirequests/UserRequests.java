package site.nomoreparties.stellarburgers.apirequests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.pojo.User;

import static io.restassured.RestAssured.given;

public class UserRequests extends ApiBase{

    private final static String ENDPOINT_PATH = "/api/auth/";

    @Step("Создание пользователя")
    public Response createUser(User user){
        return given()
                .header("Content-type", "application/json")
                        .body(user)
                        .when()
                        .post(ENDPOINT_PATH+"register");
    }

    @Step("Авторизация пользователя")
    public Response loginUser(User user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(ENDPOINT_PATH + "login");
    }

    @Step("Изменение данных пользователя")
    public Response changeUserData (User user ,String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .when()
                .body(user)
                .patch(ENDPOINT_PATH + "user");
    }

    @Step("Получение информации о пользователе")
    public Response getUserData (String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .when()
                .get(ENDPOINT_PATH + "user");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken){
            return given()
                    .header("Authorization",accessToken)
                    .when()
                    .delete(ENDPOINT_PATH + "user");
    }

}
