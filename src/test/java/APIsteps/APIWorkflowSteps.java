package APIsteps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.apiConstants;
import utils.apiPayloadConstants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class APIWorkflowSteps {
    //making variables below global since we want to use same request with other calls
    RequestSpecification request;
    Response response;
    static String employee_id;

    //-----------------------scenario creating an employee ---------------------------

    @Given("a request is prepared to create an employee")
    public void a_request_is_prepared_to_create_an_employee() {
        //this generates and returns our token
        request = given().header(apiConstants.Header_Content_type, apiConstants.Content_type)
                .header(apiConstants.Header_Authorization, GenerateTokenSteps.token).
                        body(apiPayloadConstants.createEmployeePayload());
    }

    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        response = request.when().post(apiConstants.CREATE_EMPLOYEE_URI);
        response.prettyPrint();
    }

    @Then("the status code for creating an employee is {int}")
    public void the_status_code_for_creating_an_employee_is(int statusCode) {
        //assert status code
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("the employee created contains key {string} and value {string}")
    public void the_employee_created_contains_key_and_value(String message, String employeeCreated) {
        //assert using hamcrest
        response.then().assertThat().body(message, equalTo(employeeCreated));
    }

    @Then("the employeeID {string} is stored as a global variable to be used for other calls")
    public void the_employee_id_is_stored_as_a_global_variable_to_be_used_for_other_calls(String empID) {
        //use JSON Path and use a global static variable
        employee_id = response.jsonPath().getString(empID);
    }

    //--------------------------------scenario retrieving created employee--------------------------------
    @Given("a request is prepared to retrieve the created employee")
    public void a_request_is_prepared_to_retrieve_the_created_employee() {
        request = given().header(apiConstants.Header_Content_type, apiConstants.Content_type).
                header(apiConstants.Header_Authorization, GenerateTokenSteps.token).queryParams("employee_id", employee_id);
    }

    @When("a GET call is made to retrieve the created employee")
    public void a_get_call_is_made_to_retrieve_the_created_employee() {
        response = request.when().get(apiConstants.GET_ONE_EMPLOYEE_URI);

    }

    @Then("the status code for retrieving the created employee is {int}")
    public void the_status_code_for_retrieving_the_created_employee_is(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("the retrieved employee ID {string} matches the globally stored employee ID")
    public void the_retrieved_employee_id_matches_the_globally_stored_employee_id(String employeeIDfromResponse) {
        //from response we get, create string and then assert
        String tempEmpID = response.jsonPath().getString(employeeIDfromResponse);
        assertEquals(employee_id, tempEmpID);
    }

    @Then("the retrieved data at {string} matches the data used to create an employee with employee ID {string}")
    public void the_retrieved_data_at_matches_the_data_used_to_create_an_employee_with_employee_id(String employeeObject, String responseEmployeeID, DataTable dataTable) {

        List<Map<String, String>> expectedData = dataTable.asMaps(String.class, String.class); //retrieving data from Cucumber feature file
        List<Map<String, String>> actualData = response.body().jsonPath().get(employeeObject); //retrieving whole object (returns all key & valued pairs and info)

        int index = 0;

        for (Map<String, String> map : expectedData) { //getting exp data (every key & value pairs) one by one is going to be stored in map below
            Set<String> keys = map.keySet(); //storing all iterated exp data into keys
            for (String key : keys) { //breaking down to get each key
                String expectedValue = map.get(key); //getting value of the key
                String actualValue = actualData.get(index).get(key); //getting String of the key ??

                Assert.assertEquals(expectedValue, actualValue); //asserting each value one by one at a time
            }
            index++; //we want to continue the iterator until it reaches end
        }
        String empID=response.body().jsonPath().get(responseEmployeeID);
        //asserting that employee ID matches since it was not part of map
        Assert.assertEquals(empID, employee_id);
    }
}

