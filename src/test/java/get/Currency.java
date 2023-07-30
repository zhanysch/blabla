package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class Currency {

    /*
    * 1. Defined URL/endpoint https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json
    * 2. Add query string params if needed
    * 3. Add headers if needed
    * 4. Define HTTP methods(Get)
    * 5. Send request
     */

    @Test
    public void usdCurrencyTest(){


        RestAssured.baseURI = "https://fakestoreapi.com/products/1";
      Response response =  RestAssured.given().header("Accept","application/json")
                .when().get()
                .then().statusCode(200).log().body()
              .extract().response();
        Map<String, Object> deseralizedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });
        System.out.println(deseralizedResponse);
        int id = (Integer) deseralizedResponse.get("id");
        String title = String.valueOf(deseralizedResponse.get("title"));

        double price = (double) deseralizedResponse.get("price");
        Assert.assertEquals(1, id);

        Assert.assertEquals("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops", title);
        Assert.assertEquals(109.95,price,0);

        Map<String,Object> ratingmap = (Map<String, Object>) deseralizedResponse.get("rating");
        Double rate =(Double) ratingmap.get("rate");
        Integer count =(Integer) ratingmap.get("count");
        Assert.assertTrue(rate==3.9);
        Assert.assertTrue(count==120);

    }

    @Test
    public void fakeStory(){


        RestAssured.baseURI = "https://fakestoreapi.com/products/";
        Response response =  RestAssured.given().header("Accept","application/json")
                .when().get()
                .then().statusCode(200).log().body()
                .extract().response();
        List<Map<String, Object>> parsedResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
        });


       double count = 0;

       for (Map<String,Object> product: parsedResponse){
           System.out.println();
           double price = Double.parseDouble(product.get("price").toString()) ;
           price++;
           count+= price;

       }
       Assert.assertTrue(count>200);

    }





}
