import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.apirequests.IngredientRequests;
import site.nomoreparties.stellarburgers.apirequests.OrderRequests;
import site.nomoreparties.stellarburgers.apirequests.UserRequests;
import site.nomoreparties.stellarburgers.pojo.Ingredient;
import site.nomoreparties.stellarburgers.pojo.Order;
import site.nomoreparties.stellarburgers.pojo.User;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class TestCreateOrder {
    OrderRequests orderRequests = new OrderRequests();
    User user = new User("ios@yandex.ru","123","Oleg");
    UserRequests userRequests = new UserRequests();
    public Order order;
    public Ingredient ingredientList;
    IngredientRequests ingredientRequests = new IngredientRequests();
    private String accessToken;
    public List<String> ingredients;
    @Before
    public void setUp(){
        orderRequests.setUp();
        ingredientList = ingredientRequests.getIngredient();
        ingredients = new ArrayList<>();
        ingredients.add(ingredientList.getData().get(1).get_id());
        ingredients.add(ingredientList.getData().get(2).get_id());
        ingredients.add(ingredientList.getData().get(3).get_id());
        userRequests.createUser(user);
        accessToken = userRequests.loginUser(user).then().extract().path("accessToken");
        order = new Order(ingredients);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    public void checkCreateOrderAuthUser(){
        orderRequests.createOrderAuthUser(order,accessToken)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    public void checkCreateOrderNotAuthUser(){
        orderRequests.createOrderNotAuthUser(order)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем без ингредиентов")
    public void checkCreateOrderNoIngredients(){
        ingredients.clear();
        order = new Order(ingredients);
        orderRequests.createOrderAuthUser(order,accessToken)
                .then().assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание заказа c неверным хешем ингредиентов")
    public void checkCreateOrderWithWrongHash(){
        ingredients.clear();
        ingredients.add("wrong");
        order = new Order(ingredients);
        orderRequests.createOrderAuthUser(order,accessToken)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void deleteUser(){
        if (accessToken!=null) {
            userRequests.deleteUser(accessToken);
        }
    }
}
