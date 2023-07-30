package delete;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetStorePojo;
import pojo.PetstoreCategoryPojo;

import java.util.Map;

public class PetStore {


    /*
    1. Create a pet
    2. Delete a pet
    3. Delete a pet - 404
     */

    @Test
    public void deletePetTest() {

        //1. Create a pet

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2/pet";


        int petId = 556789;
        String petName = "kitty";
        String petStatus = "playing";

        PetStorePojo petPayload = new PetStorePojo();
        petPayload.setId(petId);
        petPayload.setName(petName);
        petPayload.setStatus(petStatus);


        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(petPayload)
                .when().post()
                .then().statusCode(200).extract().response();

        //deserelization

        PetStorePojo parsedReponse = response.as(PetStorePojo.class);
        Assert.assertEquals(petName, parsedReponse.getName());
        Assert.assertEquals(petId, parsedReponse.getId());
        Assert.assertEquals(petStatus, parsedReponse.getStatus());


        //2. Delete a pet

      response=  RestAssured.given().accept(ContentType.JSON)
                .when().delete(String.valueOf(petId))
                .then().statusCode(200).extract().response();

     Map<String,Object> deserializeResponse = response.as(new TypeRef<Map<String,Object>>() {
      });
     String acutalMessage = String.valueOf(deserializeResponse.get("message"));
     int acutalCode = (int)deserializeResponse.get("code");

     Assert.assertEquals(200,acutalCode);
     Assert.assertEquals(String.valueOf(petId),acutalMessage);

     //3Delete a pet

       RestAssured.given().accept(ContentType.JSON)
                .when().delete(String.valueOf(petId))
                .then().statusCode(404);


    }




}
