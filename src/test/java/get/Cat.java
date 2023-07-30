package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cat {

    /*
    send cat request to this endpoint and deserilze the response and validate that you're getting 100
    facts about cats
     */

    @Test
    public void getCats() {
        RestAssured.baseURI = "https://catfact.ninja";
        RestAssured.basePath = "facts";
        Response response = RestAssured.given().accept("application/json")
                .queryParam("limit", 100)
                .when().get()
                .then().statusCode(200).log().body().extract()
                .response();

        Map<String, Object> parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        List<Map<String, Object>> data = (List<Map<String, Object>>) parsedResponse.get("data");

        Integer excpectedFactNumber = 100;

        Integer actualFactNumber = data.size();
        Assert.assertEquals(excpectedFactNumber, actualFactNumber);

        /*
        fact && length  length has a lot of parameter find find only fact shorter than 50 character and fact longer
         */

    }

    @Test
    public void getAllCatsFact() {
        RestAssured.baseURI = "https://catfact.ninja";
        RestAssured.basePath = "facts";
        Response response = RestAssured.given().accept("application/json")
                .queryParam("limit", 100)
                .when().get()
                .then().statusCode(200).log().body().extract()
                .response();

        Map<String, Object> parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        List<Map<String, Object>> data = (List<Map<String, Object>>) parsedResponse.get("data");


        List<String> moreThanTwoHundred = new ArrayList<>();
        List<String> lessThanFifty = new ArrayList<>();


        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> tempMap = data.get(i);

            Serializable checkUrlCodewars = data.get(i).get("fact").toString().length() > 200 ? data.get(i).get("fact").toString().length() < 50 ?
                    lessThanFifty.add(tempMap.get("fact").toString())  : "Empty" : moreThanTwoHundred.add(tempMap.get("fact").toString());

            System.out.println(checkUrlCodewars);

        }

        System.out.println(moreThanTwoHundred);
        System.out.println(lessThanFifty);


    }


}
