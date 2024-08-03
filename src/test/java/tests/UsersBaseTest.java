package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class UsersBaseTest {

    @BeforeAll
    static void setTests() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
    }
}
