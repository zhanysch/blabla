package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.ContinentPojo;
import pojo.PetStorePojo;
import pojo.PlanetPojo;

import java.util.*;

public class GameOfThrones {

    @Test
    public void gameOfThrones() {

        RestAssured.baseURI = "https://thronesapi.com/api/v2/Characters";
        RestAssured.given().header("Accept", "application/json")
                .when().get()
                .then().statusCode(200).log().body();
    }

    @Test
    public void getSepceficCharacterTest() {
        RestAssured.baseURI = "https://thronesapi.com/api/v2/Characters/10";
        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get()
                .then().statusCode(200).log().body().extract()
                .response();

        Map<String, Object> deseralizedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });
        System.out.println(deseralizedResponse);
        String firstName = (String) deseralizedResponse.get("firstName"); // 1 way converting object to String
        String lastName = String.valueOf(deseralizedResponse.get("lastName"));//second way converting object to String
        Assert.assertEquals("Cateyln", firstName);
        Assert.assertEquals("Stark", lastName);
    }

    @Test
    public void getContinets() {
        RestAssured.baseURI = "https://thronesapi.com";
        RestAssured.basePath = "/api/v2/continents";
        Response response = RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200).log().body().extract()
                .response();

        List<Map<String, Object>> parsedResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
        });
        Integer actualConitnet = parsedResponse.size();
        Integer expected = 4;
        Assert.assertEquals(actualConitnet, expected);


        List<String> continentList = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < parsedResponse.size(); i++) {
            Map<String, Object> tempMap = parsedResponse.get(i);
            Map<String, Object> id = parsedResponse.get(i);
            // System.out.println(id.get("id"));
            System.out.println(tempMap.get("name"));
            continentList.add((String) tempMap.get("name"));
            ids.add((Integer) id.get("id"));
        }
        System.out.println(continentList);
        System.out.println(ids);

        Map<String, Integer> maps = new HashMap<>();
        for (int i = 0; i < parsedResponse.size(); i++) {
            maps.put(continentList.get(i), ids.get(i));
        }
        System.out.println(maps + "  w  ");
    }


    @Test
    public void continetListTest() {
        RestAssured.baseURI = "https://thronesapi.com";
        RestAssured.basePath = "/api/v2/continents/0";

        Response response = RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200).log()
                .body().extract()
                .response();

        ContinentPojo parsedResponse = response.as(ContinentPojo.class);
        int id = parsedResponse.getId();
        String name = parsedResponse.getName();

        Assert.assertEquals(0,id);
        Assert.assertEquals("Westeros",name);
    }

    @Test
    public void continentTest(){

        RestAssured.baseURI = "https://thronesapi.com/";
        RestAssured.basePath = "api/v2/continents";

      Response response =  RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200).log().body()
                .extract().response();

      //pojo deseralization

        List<ContinentPojo> continentPojoList = new ArrayList<>();
        response.as(continentPojoList.getClass());

        Map<String,Integer> continentIdMap = new HashMap<>();

        for (int i = 0; i<continentPojoList.size(); i++){
        ContinentPojo continentPojo =    continentPojoList.get(i);
          String name = continentPojo.getName();
          int id = continentPojo.getId();
          continentIdMap.put(name,id);
        }

        /*
        ContinentPojo[] parsedResp = response.as(ContinentPojo[].class);

        Map<String,Integer> continentIdMap = new HashMap<>();
        for (int i = 0; i<parsedResp.length; i++){
        ContinentPojo continentPojo =    parsedResp[i];
          String name = continentPojo.getName();
          int id = continentPojo.getId();
          continentIdMap.put(name,id);
        }
         */
    }

    @Test
    public void getPetTest(){

        RestAssured.baseURI ="https://swapi.dev";
        RestAssured.basePath = "/api/planets/1";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().response();

        PlanetPojo parsedResponse = response.as(PlanetPojo.class);
        List<String> d =  Arrays.asList(parsedResponse.getName(),parsedResponse.getPopulation(),parsedResponse.getTerrain());
        List <String> w = Arrays.asList(parsedResponse.getPopulation(),parsedResponse.getPopulation(),parsedResponse.getTerrain());


        Assert.assertEquals("faile to validate pet name ",d,w);
    }

}
