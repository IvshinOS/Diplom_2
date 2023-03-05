package site.nomoreparties.stellarburgers.apirequests;

import io.qameta.allure.Step;
import site.nomoreparties.stellarburgers.pojo.Ingredient;

import static io.restassured.RestAssured.given;

public class IngredientRequests extends ApiBase{
private static final String PATH_INGREDIENTS = "/api/ingredients";

@Step("Получение информации об ингредиентах")
    public Ingredient getIngredient(){
    return given()
            .header("Content-type", "application/json")
            .get(PATH_INGREDIENTS)
            .as(Ingredient.class);
}
}
