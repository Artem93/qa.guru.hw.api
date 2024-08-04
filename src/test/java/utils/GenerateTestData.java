package utils;

import models.*;

import static utils.StringPatterns.fullDateTimePattern;

public class GenerateTestData {

    public static UserModel getFirstSystemUser() {
        return new UserModel(new UserData(
                1,
                "george.bluth@reqres.in",
                "George",
                "Bluth",
                "https://reqres.in/img/faces/1-image.jpg"
        ), new UserSupport(
                "https://reqres.in/#support-heading",
                "To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    public static UserCreateRequestModel getNewUserForRequest() {
        return new UserCreateRequestModel("Artem", "Guru");
    }

    public static UserCreateResponseModel getNewUserForResponse() {
        return new UserCreateResponseModel("Artem", "Guru", "\\d+", fullDateTimePattern);
    }
}
