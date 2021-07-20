package utils;
//this global var class helps to access from multiple files even though the methods are not in the same file
public class GlobalVariables {
    //all id, first name, middle name, and last name will be accessed from database and stored in this file
        //this helps to access same type of data again
    public static String empId;
    public static String dbFirstName; //back end = actual values
    public static String dbMiddleName;
    public static String dbLastName;
    public static String firstName; //front end = expected values
    public static String middleName;
    public static String lastName;

}
