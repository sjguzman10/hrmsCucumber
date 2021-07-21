package APIsteps;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import utils.apiConstants;


public class GenerateTokenSteps {
    static String token;
    @Given("a JWT is generated")
    public void a_jwt_is_generated() {
        //prepping our request to generate token, and making our call by using the Generate Token URI and printing it

        RequestSpecification generateTokenRequest=given().header("Content-type","application/json")
                .body("{\n" +
                        "  \"email\": \"stephanieAPItest01@gmail.com\",\n" +
                        "  \"password\": \"wearealllosttobefound\"\n" +
                        "}");
        Response generateTokenResponse=generateTokenRequest.when().post(apiConstants.GENERATE_TOKEN_URI);
        generateTokenResponse.prettyPrint();

        token="Bearer "+generateTokenResponse.jsonPath().getString("token"); //using jsonPath() to get value of token
    }
}
