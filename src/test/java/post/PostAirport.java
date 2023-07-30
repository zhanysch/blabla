package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.*;
import utils.PayloadUtils;

import java.util.ArrayList;
import java.util.List;

public class PostAirport {


    @Test
    public  void createPetTest(){

        RestAssured.baseURI = "https://airportgap.dev-tester.com";
        RestAssured.basePath = "/api/airports/distance";

        Response response= RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                .body(PayloadUtils.getSlackPayLoadAirport("ORD","MIA"))
                .when().post()
                .then().statusCode(200)
                .extract().response();

        AirportDataPojo parsedResponse = response.as(AirportDataPojo.class);

        double actualrespnd = parsedResponse.getData().getAttributes().getMiles();
        Assert.assertEquals(actualrespnd,1198.4365657701198,0);
    }

    @Test
    public void homeWorkAirport(){
        RestAssured.baseURI = "https://airportgap.dev-tester.com/api/airports/";


        Response response= RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                .when().get()
                .then().statusCode(200)
                .log().body().extract().response();

        JsonPath parsedReponse = response.jsonPath();
        List<String> allAirports = new ArrayList<>();
        List<String> allMilles = new ArrayList<>();


        for (int i = 0; i<parsedReponse.getList("data").size() ; i++){
            String id = (String) parsedReponse.getList("data.id").get(i);
            String miles = (String) parsedReponse.getList("data.attributes.miles").get(i);
            allMilles.add(miles);
            allAirports.add(id);
            for (int j = i+1 ; j<allAirports.size(); i++){
                RestAssured.baseURI = "https://airportgap.dev-tester.com";
                RestAssured.basePath = "/api/airports/distance";

                Response response2= RestAssured.given()
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                        .body("{\n" +
                                "    \"from\": \""+allAirports.get(i)+"\",\n" +
                                "    \"to\": \""+allAirports.get(j)+"\"\n" +
                                "}")
                        .when().post()
                        .then().statusCode(200)
                        .extract().response();



                System.out.println(response2+"ttttt");
            }
            System.out.println(id+"ttttttttttttttttttttttt");
            System.out.println(miles);
        }

    }

    @Test
    public void homeWorkAirport2() {
        RestAssured.baseURI = "https://airportgap.dev-tester.com/api/airports/";


        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                .when().get()
                .then().statusCode(200)
                .log().body().extract().response();

        //JsonPath parsedReponse = response.jsonPath();

        JsonPath airportsJsonPath = response.jsonPath();
        List<String> airportIds = airportsJsonPath.getList("data.id");
        // Set the base URL of the API for retrieving airport distances


        double maxDistance = 0;
        String sourceAirport = "";
        String destinationAirport = "";

        // Make POST requests to retrieve distances between airports and find the largest distance
        for (String source : airportIds) {
            for (String destination : airportIds) {
                if (!source.equals(destination)) {
                    RestAssured.baseURI = "https://airportgap.dev-tester.com";
                    RestAssured.basePath = "/api/airports/distance";

                    Response response2= RestAssured.given()
                            .accept(ContentType.JSON)
                            .contentType(ContentType.JSON)
                            .header("Authorization", "Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                            .body("{\n" +
                                    "    \"from\": \""+source+"\",\n" +
                                    "    \"to\": \""+destination+"\"\n" +
                                    "}")
                            .when().post()
                            .then().statusCode(200)
                            .extract().response();

                    JsonPath distanceJsonPath = response2.jsonPath();
                    Double distance = distanceJsonPath.getDouble("data.attributes.miles");

                    // Check if the current distance is greater than the max distance found so far
                    if (distance > maxDistance) {
                        maxDistance = distance;
                        sourceAirport = source;
                        destinationAirport = destination;
                    }
                }
            }
        }

        System.out.println("The largest distance between airports is: " + maxDistance);
        System.out.println("From Airport ID: " + sourceAirport);
        System.out.println("To Airport ID: " + destinationAirport);
    }
    @Test
    public void Task_01() throws InterruptedException {
        RestAssured.baseURI = "https://airportgap.dev-tester.com/api/airports";
        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Authorization", "Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                .when().get()
                .then().statusCode(200).extract().response();

        // Deserialization
        JsonPath jsonPath = response.jsonPath();
        List<String> listOfAllAirports = jsonPath.getList("data.id");

        double largest = 0;
        String sourceAirport = "";
        String destinationAirport = "";

        for (int i = 0; i < listOfAllAirports.size(); i++) {
            for (int j = 0; j < listOfAllAirports.size(); j++) {
                Thread.sleep(200); // Add a delay of 500 milliseconds between requests

                RestAssured.baseURI = "https://airportgap.dev-tester.com/api/airports/distance";
                Response response1 = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                        .header("Authorization", "Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                        .body("{\n" +
                                "    \"from\":\"" + listOfAllAirports.get(i) + "\",\n" +
                                "    \"to\":\"" + listOfAllAirports.get(j) + "\"\n" +
                                "}")
                        .when().post()
                        .then().statusCode(200).extract().response();

                // Check if the response contains the distance value
                if (response1.body().asString().contains("\"distance\"")) {
                    // Extract the distance value from the response of each distance request
                    JsonPath distanceJsonPath = response1.jsonPath();
                    double distance = Double.parseDouble(distanceJsonPath.getString("distance"));

                    // Check if the current distance is greater than the largest distance found so far
                    if (distance > largest) {
                        largest = distance;
                        sourceAirport = listOfAllAirports.get(i);
                        destinationAirport = listOfAllAirports.get(j);
                    }
                }
            }
        }

        System.out.println("The largest distance between airports is: " + largest);
        System.out.println("From Airport ID: " + sourceAirport);
        System.out.println("To Airport ID: " + destinationAirport);
    }
}





