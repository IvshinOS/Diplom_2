import io.qameta.allure.junit4.DisplayName;
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

import static org.apache.http.HttpStatus.*;

public class TestGetOrder {
    OrderRequests orderRequests = new OrderRequests();
    User user = new User("ios@yandex.ru","123","Oleg");
    UserRequests userRequests = new UserRequests();
    private Order order;
    private Ingredient ingredientList;
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
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void checkGetOrderAuthUser(){
        orderRequests.getOrdersAuthUser(accessToken)
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Получение списка заказов неавторизованного пользователя")
    public void checkGetOrderNotAuthUser(){
        orderRequests.getOrdersNotAuthUser()
                .then().assertThat().statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void deleteUser(){
        if (accessToken!=null) {
            userRequests.deleteUser(accessToken);
        }
    }
}
