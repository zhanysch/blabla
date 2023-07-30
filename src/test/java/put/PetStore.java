package put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import utils.PayloadUtils;

public class PetStore {

    @Test

    public void updatePutTest(){
        /*

        *1.Create a pet
        *2. List a pet
        *3. Update a pet
        *4.List a pet
         */


        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath="/v2/pet";

        //1 create pet
        int originalPetId = 11355;
        String originalPetName = "alfa";
        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPathPayload(originalPetId,originalPetName))
                .when().post()
                .then().statusCode(200)
                .body("id", Matchers.equalTo(originalPetId))
                .body("name",Matchers.equalTo(originalPetName));

        //2 Get Pet

        RestAssured.given().accept(ContentType.JSON)
                .when().get(originalPetId+"")
                .then().statusCode(200)
                .body("id",Matchers.is(originalPetId))
                .body("name",Matchers.equalTo(originalPetName));

        //3. Put
        String updatedPetName = "pluto";
        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPathPayload(originalPetId,updatedPetName))
                .when().put()
                .then().statusCode(200)
                .body("id",Matchers.is(originalPetId))
                .body("name",Matchers.is(updatedPetName));

        //4 Get Pet

        RestAssured.given().accept(ContentType.JSON)
                .when().get(""+originalPetId)
                .then().statusCode(200)
                .body("id",Matchers.equalTo(originalPetId))
                .body("name",Matchers.equalTo(updatedPetName));
    }




}
