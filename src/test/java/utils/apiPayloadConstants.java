package utils;

public class apiPayloadConstants {

    //taking body from Postman and pasting here to create a method that returns me a String
    public static String createEmployeePayload() {
        String createEmployeePayload="{\n" +
                "  \"emp_firstname\": \"Stephanie\",\n" +
                "  \"emp_lastname\": \"Postman\",\n" +
                "  \"emp_middle_name\": \"G\",\n" +
                "  \"emp_gender\": \"F\",\n" +
                "  \"emp_birthday\": \"2021-07-20\",\n" +
                "  \"emp_status\": \"Employee\",\n" +
                "  \"emp_job_title\": \"Cloud Consultant\"\n" +
                "}";
        return createEmployeePayload;
    }
}
