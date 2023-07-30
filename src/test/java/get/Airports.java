package get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class Airports {

    @Test
    public void getListofAirportsTest(){

        RestAssured.baseURI = "https://airportgap.dev-tester.com";
        RestAssured.basePath ="/api/airports";

     Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Authorization","Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                .when().get()
                .then().statusCode(200)
                .extract().response();

     ///Hicham Habibi el nour el Ein

     //deserelization

       JsonPath parsedReponse = response.jsonPath();
       String nextPageUrl = parsedReponse.getString("links.next");
        System.out.println("Next page url is:" + nextPageUrl);

        //retrieve city of first airport
       String firstAirportId= parsedReponse.getString("data[0].id");
        Assert.assertEquals("GKA",firstAirportId);

        //retrieve city of first airport
      String firstAirportCity =  parsedReponse.getString("data[0].attributes.city");
      Assert.assertEquals("Goroka",firstAirportCity);

    }
}
