package tests;

import models.UserCreateResponseModel;
import models.UserList;
import models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.GenerateTestData;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specs.UsersSpec.*;
import static utils.GenerateTestData.getNewUserForRequest;
import static utils.GenerateTestData.getNewUserForResponse;
import static utils.StringPatterns.*;

public class UsersTests extends UsersBaseTest {

    @Test
    @DisplayName(value = "Проверка успешного создания юзера c именем и работой")
    void checkSuccessfulCreateUserTest() {
        var newUserForRequest = getNewUserForRequest();
        var expectedInResponseNewUser = getNewUserForResponse();

        var actualUser = step("Запрос на создание юзера", () -> given(userRequestSpec)
                .body(newUserForRequest)

                .when()
                .post()

                .then()
                .spec(userResponseSpec201).extract().as(UserCreateResponseModel.class));

        step("Проверка ответа на валидность данных", () -> {
            assertEquals(actualUser.job(), expectedInResponseNewUser.job());
            assertEquals(actualUser.name(), expectedInResponseNewUser.name());
            assertTrue(actualUser.id().matches(expectedInResponseNewUser.id()));
            assertTrue(actualUser.createdAt().matches(expectedInResponseNewUser.createdAt()));
        });
    }

    @Test
    @DisplayName("Проверка успешного создания юзера с пустым body")
    void checkSuccessfulCreateUserWithEmptyBodyTest() {
        var response = step("Запрос на создание юзера с пустым body", () -> given(userRequestSpec)
                .body(emptyBody)

                .when()
                .post()

                .then()
                .spec(userResponseSpec201).extract().as(UserCreateResponseModel.class)
        );
        step("Проверка ответа", () -> {
            assertTrue(response.id().matches(simpleDigit));
            assertTrue(response.createdAt().matches(fullDateTimePattern));
        });
    }

    @Test
    @DisplayName("Проверка ответа с информацией о дефолтном юзере")
    void checkUserInfoTest() {
        var expectedUser = GenerateTestData.getFirstSystemUser();

        var actualUser = step("Запрос на создание юзера с пустым body", () -> given(userRequestSpec)
                .when()
                .get("/1")

                .then()
                .spec(userResponseSpec200).extract().as(UserModel.class)
        );
        step("Проверка ответа", () -> {
            assertEquals(actualUser.data().id(), expectedUser.data().id());
            assertEquals(actualUser.data().email(), expectedUser.data().email());
            assertEquals(actualUser.data().first_name(), expectedUser.data().first_name());
            assertEquals(actualUser.data().last_name(), expectedUser.data().last_name());
            assertEquals(actualUser.data().avatar(), expectedUser.data().avatar());
            assertEquals(actualUser.userSupport().url(), expectedUser.userSupport().url());
            assertEquals(actualUser.userSupport().text(), expectedUser.userSupport().text());
        });
    }

    @Test
    @DisplayName("Проверка ответа о несуществующем юзере")
    void checkUserNotFoundTest() {

        step("Проверка запроса с несущесвующим id", () ->
                given(userRequestSpec)
                        .when()
                        .get("/111")

                        .then()
                        .spec(userResponseSpec404)
                        .body(is(emptyBody))
        );
    }

    @ValueSource(strings = {"#", "@", "-"})
    @ParameterizedTest(name = "Проверка ответа о юзере с некорректным url = ''{0}''")
    void checkUserNotFoundTest(String path) {
        step("Проверка запроса с невалидным id", () ->
                given(userRequestSpec)
                        .when()
                        .get(path)

                        .then()
                        .spec(userResponseSpec404)
                        .body(is(emptyBody))
        );
    }

    @Test
    @DisplayName("Проверка ответа со списком юзеров")
    void checkListUsersFoundTest() {
        var userList = step("Запрос на получение списка юзеров", () -> given(userRequestSpec)
                .when()
                .get()

                .then()
                .spec(userResponseSpec200).extract().as(UserList.class)
        );
        step("Проверка ответа", () -> {
            assertEquals(userList.page(), 1);
            assertEquals(userList.perPage(), 6);
            assertEquals(userList.total(), 12);
            assertEquals(userList.totalPages(), 2);
            assertEquals(userList.data().size(), 6);
        });
    }
}
