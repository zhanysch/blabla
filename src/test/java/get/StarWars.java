package get;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.StarWarPojo;

public class StarWars {

    @Test
    public  void getCharachterTest(){

        Response response = RestAssured.given().accept("application/json")
                .when().get("https://swapi.dev/api/people/1")
                .then().statusCode(200)
                .extract().response();

        StarWarPojo parsedResponse = response.as(StarWarPojo.class);
        String expectedNme = "Luke Skywalker";
        String actualName = parsedResponse.getName();
        Assert.assertEquals("Failed to validate SW character name",expectedNme,actualName);
    }
}
