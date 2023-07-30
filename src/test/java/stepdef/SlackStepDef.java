package stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import utils.PayloadUtils;

public class SlackStepDef {
  private   Response response;

    @Given("user has slack endpoint")
    public void user_has_slack_endpoint() {
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "/api/chat.postMessage";
    }
    @When("user sends a message to api channel")
    public void user_sends_a_message_to_api_channel() {
        response = RestAssured.given().accept(ContentType.JSON)
              .contentType(ContentType.JSON)
              .header("Authorization","Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
              .body(PayloadUtils.getSlackPayLoad("Message from Cucumber"))
              .when().post();
    }
    @Then("status code is {int}")
    public void status_code_is(int expectedStatusCode) {
       int actualStatusCode = response.statusCode();
        Assert.assertEquals(expectedStatusCode,actualStatusCode);
    }
    @Then("message is succesfully sent")
    public void message_is_succesfully_sent() {

        response.then().assertThat().body("ok", Matchers.is(true));
    }

}
