package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static utils.StringPatterns.fullTimePattern;

public class UsersTests extends UsersBaseTest {
    @MethodSource
    @ParameterizedTest(name = "Проверка успешного создания юзера c именем ''{0}'' и работой ''{1}''")
    void checkSuccessfulCreateUserTest(String name, String job) {
        String userBody = String.format("{ \"name\": \"%s\", \"job\": \"%s\" }", name, job);

        Faker.instance().name();
        Faker.instance().job();
        given()
                .body(userBody)
                .contentType(JSON)
                .log().body()

                .when()
                .post()

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(name))
                .body("job", is(job))
                .body("id", matchesRegex("\\d+"))
                .body("createdAt", matchesRegex(fullTimePattern));
    }

    public static Stream<Arguments> checkSuccessfulCreateUserTest() {
        return Stream.of(Arguments.of(
                Faker.instance().name().name(),
                Faker.instance().job().title()
        ));
    }

    @Test
    @DisplayName("Проверка успешного создания юзера с пустым body")
    void checkSuccessfulCreateUserWithEmptyBodyTest() {
        String userBody = "{}";

        given()
                .body(userBody)
                .contentType(JSON)
                .log().body()

                .when()
                .post()

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("id", matchesRegex("\\d+"))
                .body("createdAt", matchesRegex(fullTimePattern));
    }

    @Test
    @DisplayName("Проверка ответа с информацией о юзере")
    void checkUserInfoTest() {
        given()
                .when()
                .get("/1")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(1))
                .body("data.email", is("george.bluth@reqres.in"))
                .body("data.first_name", is("George"))
                .body("data.last_name", is("Bluth"))
                .body("data.avatar", is("https://reqres.in/img/faces/1-image.jpg"));
    }

    @Test
    @DisplayName("Проверка ответа о несуществующем юзере")
    void checkUserNotFoundTest() {
        given()
                .when()
                .get("/111")

                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body(is("{}"));
    }

    @ValueSource(strings = {"#", "@", "-"})
    @ParameterizedTest(name = "Проверка ответа о юзере с некорректным id = ''{0}''")
    void checkUserNotFoundTest(String path) {
        given()
                .when()
                .get(path)

                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body(is("{}"));
    }

    @Test
    @DisplayName("Проверка ответа со списком юзеров")
    void checkListUsersFoundTest() {
        given()
                .when()
                .get()

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(1))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data", iterableWithSize(6));
    }
}