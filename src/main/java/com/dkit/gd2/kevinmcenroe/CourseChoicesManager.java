package com.dkit.gd2.kevinmcenroe;

// Stores all student CAO choices.
// Allows student to make course choices, save them and update them later.
//
// emphasis on speed of access when multiple users are accessing this at same time
//
// This component would interact with a Network component that would, in turn, send
// data over the internet to a web client.
//
// Clone all received and returned objects - encapsulation

import java.util.*;

public class CourseChoicesManager extends StudentManager{

    // reference to constructor injected studentManager
    private com.dkit.gd2.kevinmcenroe.StudentManager studentManager;

    // reference to constructor injected courseManager
    private CourseManager courseManager;

    // Store all the Course details -  fast access
    HashMap<String, Course> courseDetails = new HashMap<String, Course>();

    // caoNumber, course selection list - for fast access
    HashMap<String, ArrayList<Course>> courseChoices = new HashMap<String, ArrayList<Course>>();

    // CourseChoicesManager DEPENDS on both the StudentManager and CourseManager to access
    // student details and course details.  So, we receive a reference to each via
    // the constructor.
    // This is called "Dependency Injection", meaning that we
    // inject (or pass in) objects that this class requires to do its job.
    CourseChoicesManager(com.dkit.gd2.kevinmcenroe.StudentManager studentManager, CourseManager courseManager) {
        this.studentManager = studentManager;
        this.courseManager = courseManager;

    }

    public Student getStudentDetails(int caoNumber) {
        Student matchingStudent = null;
        if(studentManager.studentsMap.containsKey(caoNumber))
            matchingStudent = studentManager.studentsMap.get(caoNumber);
        else{
            System.out.println(Colours.RED + "A student of CAO number " + caoNumber + " does not exist in the student map" + Colours.RESET);
        }
        return matchingStudent;
    }

    public Course getCourseDetails(String courseID) {
        Course matchingCourse = null;
        if(courseManager.coursesMap.containsKey(courseID))
            matchingCourse = courseManager.coursesMap.get(courseID);
        else{
            System.out.println("A course of ID " + courseID + " does not exist in the course map");
        }
        return matchingCourse;
    }

    public List<Course> getStudentChoices(int caoNumber){
        ArrayList<Course> studentChoices = null;
        if(studentManager.studentsMap.containsKey(caoNumber)) {
            studentChoices = courseChoices.get(caoNumber);
        }
        else{
            System.out.println("CAO number " + caoNumber + " cannot be found in the student map");
        }
        return studentChoices;
    }

    void updateChoices(int caoNumber, ArrayList<String> newCourseIDs) {

        if(studentManager.studentsMap.containsKey(caoNumber)) {
            ArrayList<Course> newCourses = new ArrayList<Course>(newCourseIDs.size());

            for(String courseID : newCourseIDs){
                if(courseDetails.containsKey(courseID)){
                    Course course = courseDetails.get(courseID);
                    newCourses.add(course);
                }
                else{
                    System.out.println("Course ID " + courseID + " does not exist in the course map");
                }

            }

            courseChoices.get(caoNumber).clear();
            courseChoices.get(caoNumber).addAll(newCourses);
        }
    }

    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        Iterator courseIterator = this.courseDetails.entrySet().iterator();

        while (courseIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)courseIterator.next();
            Course course = (Course)mapElement.getValue();
            allCourses.add(course);
            System.out.println(Colours.GREEN + "Added course " + course + " to the list of all courses" +Colours.RESET);
        }
        return allCourses;
    }

    public void printStudentChoices(int caoNumber){
        try{
            if(getStudentChoices(caoNumber).size() > 0) {
            List<Course> choices = getStudentChoices(caoNumber);
            /*ArrayList<Course> choices = new ArrayList<>();
            choices.addAll(getStudentChoices(caoNumber));
            */

            //for (Course choice : choices) {
                //System.out.println(choice);
            //}
            }
            else{
                System.out.println("You have not specified any course choices yet");
            }
        }
        catch(NullPointerException npe){
            System.out.println(npe.getMessage());
        }
    }

    boolean login(int caoNumber, String dateOfBirth, String password) {
        String invalidDetails = "";

        if(this.studentManager.isRegistered(caoNumber)){
            Student student = this.studentManager.getStudent(caoNumber);

            if(dateOfBirth.equals(student.getDayOfBirth())) {

                if (password.equals(student.getPassword())){

                    System.out.println(Colours.GREEN + "Successful log in" + Colours.RESET);
                    return true;
                }
                else
                    invalidDetails+="Incorrect Password [Hint: " + student.getPassword() + "]";
            }
            else
                invalidDetails+="Incorrect Date of Birth [Hint: " + student.getDayOfBirth() + "]";
        }
        else
            invalidDetails+="Incorrect CAO Number";

        System.out.println(Colours.RED + invalidDetails + Colours.RESET);
        return false;
    }

    public Student requestLogin(){
        int caoNum = Integer.parseInt(getInput("CAO Number"));
        String dateOfBirth = getInput("Date of Birth");
        String password = getInput("Password");

        if(login(caoNum, dateOfBirth, password))
            return studentManager.getStudent(caoNum);
        else
            return null;
    }




    public static Scanner keyboard = new Scanner(System.in);

    //Adapted from my CA3 submission
    private String getInput(String requested) {
        String input;
        System.out.print("Please enter " + requested + " :>");

        input = keyboard.nextLine();
        return input;
    }

}
