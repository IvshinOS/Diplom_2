import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.apirequests.UserRequests;
import site.nomoreparties.stellarburgers.pojo.User;

import static org.apache.http.HttpStatus.*;

public class TestLoginUser {
    User user = new User("ios@yandex.ru","123","Oleg");
    UserRequests userRequests = new UserRequests();
    private String accessToken;
    @Before
    public void setUp(){
        userRequests.setUp();
        userRequests.createUser(user);
        accessToken = userRequests.loginUser(user).then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Авторизация пользователя (успех)")
    public void checkLoginUser(){
        userRequests.loginUser(user)
                .then().assertThat().statusCode(SC_OK);
    }

@Test
@DisplayName("Авторизация с некорректным email")
public void checkLoginUserIncorrectEmail(){
    user.setEmail("ios@yandex");
    userRequests.loginUser(user)
            .then().assertThat().statusCode(SC_UNAUTHORIZED);
}

    @Test
    @DisplayName("Авторизация с некорректным паролем")
    public void checkLoginUserIncorrectPassword(){
        user.setPassword("000");
        userRequests.loginUser(user)
                .then().assertThat().statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void deleteUser(){
        if (accessToken!=null) {
            userRequests.deleteUser(accessToken);
        }
    }
}
