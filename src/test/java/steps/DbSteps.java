package steps;

import io.cucumber.java.en.Then;
import utils.DbUtils;
import utils.GlobalVariables;

import java.util.List;
import java.util.Map;

//all steps related to database will go in here
public class DbSteps {

    //extract data and store in variables
    @Then("query the HRMS database")
    public void query_the_hrms_database() {
        //pass the query and making accessible to each unique ID
        String query="select emp_firstname,emp_middle_name,emp_lastname from hs_hr_employees where emp_number="+ GlobalVariables.empId;
        List<Map<String,String>> tableDataAsList= DbUtils.getTableDataAsList(query); //will return list of maps
        //extract data from my list of maps with help of keys from front end
        GlobalVariables.dbFirstName=tableDataAsList.get(0).get("emp_firstname");
        GlobalVariables.dbMiddleName=tableDataAsList.get(0).get("emp_middle_name");
        GlobalVariables.dbLastName=tableDataAsList.get(0).get("emp_lastname");

        //compare data from back end to front end

    }
}
