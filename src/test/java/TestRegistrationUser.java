import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.apirequests.UserRequests;
import site.nomoreparties.stellarburgers.pojo.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class TestRegistrationUser {

    User user = new User("ios@yandex.ru","123","Oleg");
    UserRequests userRequests = new UserRequests();
    String accessToken;
    @Before
    public void setUp(){
        userRequests.setUp();
    }

    @Test
    @DisplayName("Регистрация пользователя(успех)")
    public void checkCreateUser(){
        userRequests.createUser(user)
                .then().assertThat().statusCode(SC_OK);
    }

@Test
@DisplayName("Регистрация ранее зарегестрированного пользователя")
public void checkCreateDuplicateUser(){
        userRequests.createUser(user);
        userRequests.createUser(user)
                .then().assertThat().statusCode(SC_FORBIDDEN);
}

@Test
@DisplayName("Регистрация пользователя без email")
public void checkCreateUserWithoutEmail(){
        user.setEmail("");
        userRequests.createUser(user)
            .then().assertThat().statusCode(SC_FORBIDDEN);
}

    @Test
    @DisplayName("Регистрация пользователя без пароля")
    public void checkCreateUserWithoutPassword(){
        user.setPassword("");
        userRequests.createUser(user)
                .then().assertThat().statusCode(SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Регистрация пользователя без имени")
    public void checkCreateUserWithoutName(){
        user.setName("");
        userRequests.createUser(user)
                .then().assertThat().statusCode(SC_FORBIDDEN);
    }

    @After
    public void deleteUser(){
        accessToken = userRequests.loginUser(user).then().extract().path("accessToken");
        if (accessToken!=null) {
            userRequests.deleteUser(accessToken);
        }
    }
}
