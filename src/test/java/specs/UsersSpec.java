package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static utils.CustomAllureRestAssured.withCustomTemplates;

public class UsersSpec {

    public static RequestSpecification userRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();

    public static ResponseSpecification userResponseSpec200 = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .log(LogDetail.HEADERS)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification userResponseSpec404 = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .log(LogDetail.HEADERS)
            .expectStatusCode(404)
            .build();

    public static ResponseSpecification userResponseSpec201 = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .log(LogDetail.HEADERS)
            .expectStatusCode(201)
            .build();


}
