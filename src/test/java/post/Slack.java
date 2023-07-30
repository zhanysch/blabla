package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import pojo.SlackPojo;
import utils.PayloadUtils;

public class Slack {

    @Test
    public void sendMessageTest() {

        RestAssured.baseURI = "https://app.slack.com";
        RestAssured.basePath = "api/chat.postMessage";

     Response response= RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
                .body(PayloadUtils.getSlackPayLoad("salam postman"))
                .when().post()
                .then().statusCode(200)
                .extract().response();

        SlackPojo parsedResponse = response.as(SlackPojo.class);

        String actualMessage = parsedResponse.getMessage().getText();
        Assert.assertTrue(actualMessage.contains("Janysh"));
    }

    @Test
    public  void sendMessageTest2(){
        RestAssured.baseURI = "https://app.slack.com";
        RestAssured.basePath = "api/chat.postMessage";

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
                .body(PayloadUtils.getSlackPayLoad("message sent from Java"))
                .when().post()
                .then().statusCode(200)
                .body("ok", Matchers.is(true))
                .body("message.text",Matchers.equalTo("Janysh: message sent from Java"));

    }
}
