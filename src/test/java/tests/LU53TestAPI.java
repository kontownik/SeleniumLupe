package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LU53TestAPI {

    private String authHeader;
    private int expectedCategoryCount = 8; // ilosc kategorii zgloszen

    @BeforeClass
    public static void setupURI() {
        RestAssured.baseURI = "https://dummydata";
    }

    @Test
    public void testAPI() throws Exception {
        String authJson = "dummydata";


        Response responseAuth =
                given()
                    .header("Accept", "application/json")
                    .header("Cache-Control", "no-cache")
                    .contentType(ContentType.JSON)
                    .body(authJson)
                .when()
                    .post("/auth/login")
                .then()
                    .statusCode(201)
                    .extract()
                    .response();

        JsonPath jsonResponseAuth = new JsonPath(responseAuth.asString());
        String accessToken = jsonResponseAuth.getString("access_token");
        String tokenType = jsonResponseAuth.getString("token_type");
        this.authHeader = tokenType+" "+accessToken;

        Response r =
                given()
                    .pathParam("cityId", "599")
                    .header("Authorization", authHeader)
                    .header("Accept", "application/json")
                    .header("Cache-Control", "no-cache")
                .when()
                    .get("/city/{cityId}/category")
                .then()
                    .statusCode(200)
                    .extract().response();

        JsonPath jsonCategoryResponse = new JsonPath(r.asString());
        int actualCategoryCount = jsonCategoryResponse.getList("").size();
        Assert.assertEquals("ERROR: Ilosc inna niz spodziwana", expectedCategoryCount, jsonCategoryResponse.getList("").size());
    }
}
