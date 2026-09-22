package web.app.api.services;
import static io.restassured.RestAssured.given;
import web.app.api.endpoints.UrlRouts;
import web.app.api.models.User;
import java.util.List;

public class UserService extends BaseService {

    public List<User> getAllUsers() {
        return given().spec(getRequestSpec())
                .when().get(UrlRouts.USERS)
                .then().extract().jsonPath().getList("users", User.class);
    }

    public Long getFirstUserId() {
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            throw new IllegalStateException("No users available in the application");
        }
        return users.get(0).id();
    }
}
