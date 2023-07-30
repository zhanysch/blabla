package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetStorePojo;
import utils.PayloadUtils;

import java.io.File;

public class PetStore {

    @Test
    public  void createPetTest(){


     RestAssured.baseURI= "https://petstore.swagger.io";
        RestAssured.basePath="/v2/pet";
        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPathPayload(274,"blabla"))
                .when().post().then()
                .statusCode(200)
                .extract().response();

       PetStorePojo parsedResponse =  response.as(PetStorePojo.class);
       String actualName = parsedResponse.getName();
       String actualStatus = parsedResponse.getStatus();

        Assert.assertEquals("blabla",actualName);
        Assert.assertEquals("fishing",actualStatus);
    }

    @Test
    public void createPetWithJsonFileTest(){
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        File jsonFile = new File("src/test/resources/pet.json");
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonFile)
                .then().statusCode(200)
                .log().body() ;

        ////////////////////////////////////////////////////////////////////

        PetStorePojo pet = new PetStorePojo();
        pet.setId(78932);
        pet.setName("rex");
        pet.setStatus("barking");

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
                .when().post()
                .then().statusCode(200);

        ///////////////////////////////////////////////

     Response reponse=    RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
                .when().post()
                .then().statusCode(200)
             .log().body().extract().response();

     JsonPath parsedReponse = reponse.jsonPath();
     String actual = parsedReponse.getString("name");
     Assert.assertEquals("rex",actual);
    }
}
