package advanced;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

public class Football {


    @Test
    public void getCompetionsTest() {
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "/v2/competitions";

        RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .body("competitions.find { it.id ==2166 }.name",
                        Matchers.is("AFC Champions League"));
    }

    @Test
    public void getCompetionsTest2() {
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "/v2/competitions";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .body("competitions.find { it.id ==2166 }.name",
                        Matchers.is("AFC Champions League"))
                .body("competitions.findAll { it.area.name == 'Africa' }.name"
                        ,Matchers.hasItems("AFC Champions League","WC Qualification CAF"))
                .and()
                .body("competitions.min {it.id}.name",Matchers.equalTo("FIFA World Cup"))
                .body("competitions.collect { it.id }.sum()",Matchers.greaterThanOrEqualTo(360884))
                .extract().response();
        List<String> africaCompetionsList =
                response.path("competitions.findAll { it.area.name == 'Africa' }.name");
        System.out.println(africaCompetionsList);
    }
}
