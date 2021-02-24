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
    final private com.dkit.gd2.kevinmcenroe.StudentManager studentManager;

    // reference to constructor injected courseManager
    final private CourseManager courseManager;

    // Store all the Course details -  fast access
    // Stores all of the available courses by code
    final private HashMap<String, Course> courseDetails = new HashMap<>();

    // caoNumber, course selection list - for fast access
    //private HashMap<String, String> courseCodes = new HashMap<String, String>();

    // caoNumber, arraylist of course codes relating to choices
    final private HashMap<Integer, ArrayList<Course>> studentCourseChoices = new HashMap<>();

    // CourseChoicesManager DEPENDS on both the StudentManager and CourseManager to access
    // student details and course details.  So, we receive a reference to each via
    // the constructor.
    // This is called "Dependency Injection", meaning that we
    // inject (or pass in) objects that this class requires to do its job.
    CourseChoicesManager(com.dkit.gd2.kevinmcenroe.StudentManager studentManager, CourseManager courseManager) {
        this.studentManager = studentManager;
        this.courseManager = courseManager;

        System.out.println("Adding courses to the course details map...");
        for(Course course : courseManager.getAllCourses()){
            courseDetails.put(course.getCourseId(), course);
            System.out.println(Colours.GREEN + "Added " + course.getCourseId() + " to the course details map" + Colours.RESET);
        }
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

        /* This code was refactored to displayCourseDetails()
        if(courseManager.coursesMap.containsKey(courseID))
            matchingCourse = courseManager.coursesMap.get(courseID);
        else{
            System.out.println("A course of ID " + courseID + " does not exist in the course map");
        }*/

        Course matchingCourse = courseManager.coursesMap.get(courseID);
        return matchingCourse;
    }

    public void displayCourseDetails(){
        System.out.println("Enter course ID from the list below to view corresponding course details");
        printAvailableCourses();
        String courseID = getInput("Course ID");
        if(courseManager.coursesMap.containsKey(courseID))
            System.out.println(Colours.GREEN + getCourseDetails(courseID) + Colours.RESET);
        else{
            System.out.println(Colours.RED + "A course of ID '" + courseID + "' does not exist in the course map" + Colours.RESET);
        }
    }

    public List<Course> getStudentChoices(int caoNumber){
        ArrayList<Course> studentChoices = null;
        if(studentManager.studentsMap.containsKey(caoNumber))
            studentChoices = studentCourseChoices.get(caoNumber);
        else
            System.out.println("CAO number " + caoNumber + " cannot be found in the student map");

        return studentChoices;
    }

    void updateChoices(int caoNumber, ArrayList<String> newCourseIDs) {

        if(getStudentChoices(caoNumber) == null) {
            createStudentCourseChoices(caoNumber);
        }

        if(studentManager.studentsMap.containsKey(caoNumber)) {
            ArrayList<Course> chosenCourses = new ArrayList<>(newCourseIDs.size());

            for(String courseID : newCourseIDs){
                if(courseDetails.containsKey(courseID)){
                    Course course = courseDetails.get(courseID);
                    chosenCourses.add(course);
                    System.out.println(Colours.GREEN + "Added " + course + Colours.RESET);
                }
                else{
                    System.out.println("Course ID '" + courseID + "' does not exist in the course map");
                }
            }

            studentCourseChoices.get(caoNumber).clear();
            studentCourseChoices.put(caoNumber, chosenCourses);
        }
    }

    private List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        Iterator courseIterator = this.courseDetails.entrySet().iterator();

        if(courseIterator.hasNext())
        while (courseIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)courseIterator.next();
            Course course = (Course)mapElement.getValue();
            allCourses.add(course);
            //System.out.println(Colours.GREEN + "Added course " + course + " to the list of all courses" +Colours.RESET);
        }
        else
            System.out.println(Colours.RED + "The administrator has not yet populated the course list" + Colours.RESET);
        return allCourses;
    }

    public void displayAllCourses(){
        List<Course> allCourses = getAllCourses();
        for(Course course : allCourses){
            System.out.println(Colours.GREEN + course + Colours.RESET);
        }
    }

    public void printStudentChoices(int caoNumber){
        try{
            if(getStudentChoices(caoNumber) == null) {
                createStudentCourseChoices(caoNumber);
            }

            List<Course> choices = getStudentChoices(caoNumber);
            if(choices.size() > 0)
                for (Course choice : choices) {
                   System.out.println(Colours.GREEN + choice + Colours.RESET);
                }
            else
                System.out.println(Colours.RED + "You have not yet specified any course choices" + Colours.RESET);
        }
        catch(NullPointerException npe){
            System.out.println(Colours.RED + "NullPointerException" + Colours.RESET);
        }
    }

    public void requestUpdateChoices(int caoNumber){
        ArrayList<String> newChoicesByCourseID = new ArrayList<>();
        System.out.println("Please enter your 8 chosen courses in order of preference by course code");
        printAvailableCourses();

        for (int i=1; i<9; i++){
            String courseCode = getInput("course of preference " + i);
            if(courseDetails.containsKey(courseCode)){
                if(newChoicesByCourseID.contains(courseCode)){
                    System.out.println(Colours.RED + "You have already added this course to your choices. Please try a different course code" + Colours.RESET);
                    i--;
                }
                else
                    newChoicesByCourseID.add(courseCode);
            }
            else{
                System.out.println(Colours.RED + "A course of ID " + courseCode + " does not exist. Please enter a valid ID" + Colours.RESET);
                i--;
            }
        }

        updateChoices(caoNumber, newChoicesByCourseID);
    }

    private void createStudentCourseChoices(int caoNumber){
        ArrayList<Course> choices = new ArrayList<>(100);
        this.studentCourseChoices.put(caoNumber, choices);
    }

    private void printAvailableCourses(){
        String availableCourseCodes = displaySortedKeys(courseDetails);
        System.out.println(Colours.BLUE + "Available Courses: " + availableCourseCodes + "\n" + Colours.RESET);
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
        System.out.println("\nSubmit your student details to log in");
        int caoNum = Integer.parseInt(getInput("your CAO Number"));
        String dateOfBirth = getInput("your Date of Birth");
        String password = getInput("your Password");

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

    private String displaySortedKeys(HashMap mapToSort)
    {
        ArrayList<String> sortedKeys = new ArrayList<String>(mapToSort.keySet());
        Collections.sort(sortedKeys);

        String keyList = "[";
        int index = 0;
        for (String key : sortedKeys) {
            if(index != sortedKeys.size()-1)
                keyList += key + ", ";
            else
                keyList += key;
            index++;
        }
        keyList += "]";

        return keyList;
    }
}
