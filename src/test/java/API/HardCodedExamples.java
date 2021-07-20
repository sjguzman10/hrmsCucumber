package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*; //manually needs to be imported
import static org.hamcrest.Matchers.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {

    /*
    Given = preparing the request
    When = making the request/making the call/hitting the end point
    Then = verification/assertions
     */

    String baseURI=RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api"; //base URL=base URI
    String token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjY3NTA5MTMsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYyNjc5NDExMywidXNlcklkIjoiMjk2NyJ9.MpjfoC7bTmfZ-QoWNpnCY4D49enc0eUJ2ExxbFYku9Y";
    static String employee_id;

 //   @Test //tag this to run and print without main
    public void sampleTest() {

        //makes my request
        RequestSpecification preparedRequest =
                given().header("Authorization", token)
                        .header("Content-Type", "application/json").queryParam("employee_id", "21900A");

        Response response = preparedRequest.when().get("/getOneEmployee.php"); //makes my call

        //Printing response using asString() method to convert to JSON object a String and printing using SOUT & allows us to see the CALL
        System.out.println(response.asString());
    }

    @Test
    public void aPostCreateEmployee() {
        RequestSpecification preparedRequest = given().header("Authorization", token)
                .header("Content-Type", "application/json")
                .body("{\n" + "  \"emp_firstname\": \"Syntax\",\n" + "  \"emp_lastname\": \"API\",\n"
                        + "  \"emp_middle_name\": \"Instructor\",\n" + "  \"emp_gender\": \"F\",\n"
                        + "  \"emp_birthday\": \"1990-07-10\",\n" + "  \"emp_status\": \"Employee\",\n"
                        + "  \"emp_job_title\": \"Healer\"\n" + "}")
                .log().all(); //will log and print all information being sent with the request

        Response response = preparedRequest.when().post("/createEmployee.php");
        response.prettyPrint(); //does the same as SOUT(response.asString());

        /**
         * jsonPath() allows us to retrieve specific data from a json object - just like
         * an xpath with selenium
         */
        employee_id = response.jsonPath().getString("Employee.employee_id");

        System.out.println(employee_id);

        /**
         * Performing assertions
         */
        response.then().assertThat().statusCode(201);

        /**
         * Using Hamcrest Matchers class equalTo()
         */
        response.then().assertThat().body("Message", equalTo("Employee Created"));

        // Write an assertion that verifies that the response body has the name you used

        response.then().assertThat().body("Employee.emp_firstname", equalTo("Syntax"));

        /**
         * Verifying server
         */
        response.then().assertThat().header("Server", "Apache/2.4.39 (Win64) PHP/7.2.18");
    }
    @Test
    public void bGetCreatedEmployee() {

        RequestSpecification preparedRequest = given().header("Authorization", token).header("Content-Type", "application/json").queryParam("employee_id", employee_id);
        Response response = preparedRequest.when().get("/getOneEmployee.php");
      // response.prettyPrint();

        //assert that the emp ID from the same response is the same from run time
        String empID=response.jsonPath().getString("employee.employee_id");

        boolean comparingEmpIDs=empID.contentEquals(employee_id);
        Assert.assertTrue(comparingEmpIDs); //if true, test will pass
        //also works without boolean
            //Assert.assertEquals(empID,employeeID);


        // Task: Retrieve the first name and assert that the first name is the same as the one you used
        //do not use HAMCREST MATCHERS
     //   String firstName=response.jsonPath().getString("employee.emp_firstname");
     //   Assert.assertTrue(firstName.contentEquals("Stephanie J"));
    }
    @Test
    public void cGetAllEmployees(){
        RequestSpecification preparedRequest = given().header("Authorization", token).header("Content-Type", "application/json");

        Response response=preparedRequest.when().get("/getAllEmployees.php");

        String allEmployees=response.prettyPrint();

        //write a program to gives us total number of employees
        JsonPath js=new JsonPath(allEmployees);
        int count=js.getInt("Employees.size()"); //get count
        System.out.println(count);

        //Print out all employee IDs from the response using for loop
        for(int i=0; i<count; i++) {
            String employeeIDs=js.getString("Employees["+ i +"].employee_id");
          //  System.out.println(employeeIDs); //printing all emp ids

            //If my emp ID is in response
            if(employeeIDs.contentEquals(employee_id)) {

                System.out.println("Employee ID" + employee_id + "is present in response body");
                String firstName=js.getString("Employees[\"+ i +\"].employee_id");
                System.out.println("Employee name is" +firstName);
                break;
            }
        }
    }
}
