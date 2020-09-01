package studentapi;

import org.json.simple.*;


public class StudentAPI
{
    public static JSONObject classes = new JSONObject();
    public static JSONObject students = new JSONObject();
    public static JSONObject grades = new JSONObject();
    public static JSONObject SIDs = new JSONObject();
    public static int lastSID = 0;

    public static void addClasses(int amount)
    {
        //Looping through the classes until we reach the desired amount
        JSONArray array = new JSONArray();
        for(int i = 1; i <= amount; i++) {array.add(String.valueOf(i));}
        classes.put("classes", array);
    }

    public static void addStudent(String firstName, String lastName, int clazz) //Using "clazz" to prevent confusion
    {
        //Assigning values
        String classString = String.valueOf(clazz);
        String studentID = generateSID();

        //Creating a new JSONArray and adding the student's information
        JSONArray information = new JSONArray();
        information.add(firstName);
        information.add(lastName);
        information.add(classString);

        //Assigning the information to the studentID and reversed
        students.put(studentID, information);
        SIDs.put(firstName + "-" + lastName + "-" + classString, studentID);

        //Adding a placeholder array to make sure the JSONObject works properly
        JSONArray placeholderArray = new JSONArray();
        placeholderArray.add("*");
        placeholderArray.add("empty");
        grades.put(studentID, placeholderArray);
    }

    public static void addGrade(String firstName, String lastName, int clazz, int grade)
    {
        //Getting the studentID
        String classString = String.valueOf(clazz);
        String studentID = getSID(firstName, lastName, clazz);
        String gradE = String.valueOf(grade);

        //Adding the grade to the JSONObject
        JSONArray gradesArray = (JSONArray) grades.get(studentID);
        gradesArray.add(gradE);
        if(gradesArray.contains("empty")) {gradesArray.remove("empty");}
        if (gradesArray.size() > 2 && gradesArray.contains("*")) {gradesArray.remove("*");}
        grades.put(studentID, gradesArray);
    }

    public static String generateSID()
    {
        int newSID = lastSID + 1;
        lastSID = newSID;
        String zero = "000";

        if (newSID > 9)
        {   
            zero = "00";
            if (newSID > 99)
            {
                zero = "0";
                if (newSID > 999) {zero = "";}
            }
        }

        return ("#" + zero + newSID); 
    }

    public static String getSID(String firstName, String lastName, int clazz)
    {
        String classString = String.valueOf(clazz);
        return (String) SIDs.get(firstName + "-" + lastName + "-" + classString);
    }

    public static JSONArray getStudentInformation(String studentID)
    {
        JSONArray information = (JSONArray) students.get(studentID);
        return information;
    }

    public static JSONArray getGrades(String studentID)
    {
        JSONArray gradez = (JSONArray) grades.get(studentID);
        return gradez;
    }

    public static JSONArray getGrades(String firstName, String lastName, int clazz)
    {
        String studentID = getSID(firstName, lastName, clazz);
        JSONArray gradez = (JSONArray) grades.get(studentID);
        return gradez;
    }

    public static JSONObject getClasses() {return classes;}

    public static JSONObject getStudents() {return students;}
}