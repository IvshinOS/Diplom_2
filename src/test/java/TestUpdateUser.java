import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.apirequests.UserRequests;
import site.nomoreparties.stellarburgers.pojo.User;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class TestUpdateUser {
    User user = new User("ios@yandex.ru","123","Oleg");
    UserRequests userRequests = new UserRequests();
    private String accessToken;
    private String updateEmail = "ios1@yandex.ru";
    private String updateName = "Ivan";
    @Before
    public void setUp(){
        userRequests.setUp();
        userRequests.createUser(user);
        accessToken = userRequests.loginUser(user).then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Изменение email авторизованного пользователя")
    public void checkUpdateEmailAuthUser(){
        user.setEmail(updateEmail);
        userRequests.changeUserData(user,accessToken)
                .then().assertThat()
                .body("success",equalTo(true))
                .and().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Изменение имени авторизованного пользователя")
    public void checkUpdateNameAuthUser(){
        user.setName(updateName);
        userRequests.changeUserData(user,accessToken)
                .then().assertThat()
                .body("success",equalTo(true))
                .and().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Изменение email неавторизованного пользователя")
    public void checkUpdateEmailNotAuthUser(){
        accessToken="";
        user.setEmail(updateEmail);
        userRequests.changeUserData(user,accessToken)
                .then().assertThat().statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Изменение имени неавторизованного пользователя")
    public void checkUpdateNameNotAuthUser() {
        accessToken="";
        user.setName(updateName);
        userRequests.changeUserData(user, accessToken)
                .then().assertThat().statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void deleteUser(){
        if (accessToken!=null) {
            userRequests.deleteUser(accessToken);
        }
    }
}
