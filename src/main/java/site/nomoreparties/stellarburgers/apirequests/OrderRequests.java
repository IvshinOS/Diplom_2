package site.nomoreparties.stellarburgers.apirequests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.pojo.Order;

import static io.restassured.RestAssured.given;

public class OrderRequests extends ApiBase{
    private final static String ENDPOINT_PATH_ORDER ="/api/orders";

    @Step("Создание заказа авторизованным пользователем")
    public Response createOrderAuthUser(Order order,String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .body(order)
                .when()
                .post(ENDPOINT_PATH_ORDER);
    }

    @Step("Создание заказа неавторизованным пользователем")
    public Response createOrderNotAuthUser(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ENDPOINT_PATH_ORDER);
    }

    @Step("Получение заказов авторизованного пользователя")
    public Response getOrdersAuthUser(String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .get(ENDPOINT_PATH_ORDER);
    }

    @Step("Получение заказов неавторизованного пользователя")
    public Response getOrdersNotAuthUser(){
        return given()
                .header("Content-type", "application/json")
                .get(ENDPOINT_PATH_ORDER);
    }
}
