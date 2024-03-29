// Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe;

import java.util.*;

public class CourseChoicesManager extends StudentManager{

    final private com.dkit.gd2.kevinmcenroe.StudentManager studentManager;

    final private CourseManager courseManager;

    final private HashMap<String, Course> courseDetails = new HashMap<>();

    final private HashMap<Integer, ArrayList<Course>> studentCourseChoices = new HashMap<>();

    CourseChoicesManager(com.dkit.gd2.kevinmcenroe.StudentManager studentManager, CourseManager courseManager) {
        this.studentManager = studentManager;
        this.courseManager = courseManager;

        syncCourseData();
    }

    public void syncCourseData(){
        courseDetails.clear();
        for(Course course : courseManager.getAllCourses())
            courseDetails.put(course.getCourseId(), course);
        System.out.println("Data synced");
    }

    //As per the CA4 Brief and Menu requirements, calling this method was not necessary in the menu as getStudent() in the StudentManager fulfills the same functionality.
    //I decided to exercise the getStudent method instead to align the use of StudentManager methods in the admin menu
    public Student getStudentDetails(int caoNumber) {
        Student matchingStudent = null;
        if(studentManager.studentsMap.containsKey(caoNumber))
            matchingStudent = studentManager.studentsMap.get(caoNumber);
        else
            System.out.println(IColours.RED + "A student of CAO number " + caoNumber + " does not exist in the student map" + IColours.RESET);

        return matchingStudent;
    }

    public Course getCourseDetails(String courseID) {

        /* These if statements were refactored to displayCourseDetails()
        if(courseManager.coursesMap.containsKey(courseID))
            matchingCourse = courseManager.coursesMap.get(courseID);
        else{
            System.out.println("A course of ID " + courseID + " does not exist in the course map");
        }*/

        return courseManager.coursesMap.get(courseID);
    }

    public void displayCourseDetails(){
        System.out.println("Enter course ID from the list below to view corresponding course details");
        printAvailableCourses();
        String courseID = getInput("Course ID");
        if(courseManager.coursesMap.containsKey(courseID))
            System.out.println(IColours.GREEN + getCourseDetails(courseID) + IColours.RESET);
        else
            System.out.println(IColours.RED + "A course of ID '" + courseID + "' does not exist in the course map" + IColours.RESET);

    }

    private void createStudentChoices(int caoNumber){
        ArrayList<Course> choices = new ArrayList<>(8);
        this.studentCourseChoices.put(caoNumber, choices);
    }

    public List<Course> getStudentChoices(int caoNumber){
        ArrayList<Course> studentChoices = null;
        if(studentManager.studentsMap.containsKey(caoNumber))
            studentChoices = studentCourseChoices.get(caoNumber);
        else
            System.out.println("CAO number " + caoNumber + " cannot be found in the student map");

        return studentChoices;
    }

    public void displayStudentChoices(int caoNumber){
        try{
            if(getStudentChoices(caoNumber) == null) {
                createStudentChoices(caoNumber);
            }

            List<Course> choices = getStudentChoices(caoNumber);
            if(choices.size() > 0)
                for (Course choice : choices) {
                    System.out.println(IColours.GREEN + choice + IColours.RESET);
                }
            else
                System.out.println(IColours.RED + "You have not yet specified any course choices" + IColours.RESET);
        }
        catch(NullPointerException npe){
            System.out.println(IColours.RED + "NullPointerException" + IColours.RESET);
        }
    }

    void updateStudentChoices(int caoNumber, ArrayList<String> newCourseIDs) {

        if(getStudentChoices(caoNumber) == null) {
            createStudentChoices(caoNumber);
        }

        if(studentManager.studentsMap.containsKey(caoNumber)) {
            ArrayList<Course> chosenCourses = new ArrayList<>(newCourseIDs.size());

            for(String courseID : newCourseIDs){
                if(courseDetails.containsKey(courseID)){
                    Course course = courseDetails.get(courseID);
                    chosenCourses.add(course);
                    System.out.println(IColours.GREEN + "Added " + course + IColours.RESET);
                }
                else{
                    System.out.println("Course ID '" + courseID + "' does not exist in the course map");
                }
            }

            studentCourseChoices.get(caoNumber).clear();
            studentCourseChoices.put(caoNumber, chosenCourses);
        }
    }

    public void displayUpdateChoices(int caoNumber){
        ArrayList<String> newChoicesByCourseID = new ArrayList<>();
        System.out.println("Please enter your 8 chosen courses in order of preference by course code");
        printAvailableCourses();

        for (int i=1; i<9; i++){
            String courseCode = getInput("course of preference " + i);
            if(courseDetails.containsKey(courseCode)){
                if(newChoicesByCourseID.contains(courseCode)){
                    System.out.println(IColours.RED + "You have already added this course to your choices. Please try a different course code" + IColours.RESET);
                    i--;
                }
                else
                    newChoicesByCourseID.add(courseCode);
            }
            else{
                System.out.println(IColours.RED + "A course of ID " + courseCode + " does not exist. Please enter a valid ID" + IColours.RESET);
                i--;
            }
        }

        updateStudentChoices(caoNumber, newChoicesByCourseID);
    }

    private List<Course> getAllCourses() {
        courseManager.loadCoursesFromFile(courseDetails, "courses.dat");

        List<Course> allCourses = new ArrayList<>();
        Iterator courseIterator = this.courseDetails.entrySet().iterator();

        if(courseIterator.hasNext())
        while (courseIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)courseIterator.next();
            Course course = (Course)mapElement.getValue();
            allCourses.add(course);
        }
        else
            System.out.println(IColours.RED + "The administrator has not yet populated the course list" + IColours.RESET);

        return allCourses;
    }

    public void displayAllCourses(){
        List<Course> allCourses = getAllCourses();
        for(Course course : allCourses){
            System.out.println(IColours.GREEN + course + IColours.RESET);
        }
    }

    private void printAvailableCourses(){
        String availableCourseCodes = displaySortedKeys(courseDetails);
        System.out.println(IColours.BLUE + "Available Courses: " + availableCourseCodes + "\n" + IColours.RESET);
    }

    boolean login(int caoNumber, String dateOfBirth, String password) {
        String invalidDetails = "";

        if(this.studentManager.isRegistered(caoNumber)){
            Student student = this.studentManager.getStudent(caoNumber);

            if(dateOfBirth.equals(student.getDayOfBirth())) {

                if (password.equals(student.getPassword())){
                    System.out.println(IColours.GREEN + "Successful log in" + IColours.RESET);
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

        System.out.println(IColours.RED + invalidDetails + IColours.RESET);
        return false;
    }

    public Student displayLogin(){
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

        StringBuilder keyList = new StringBuilder("[");
        int index = 0;
        for (String key : sortedKeys) {
            if(index != sortedKeys.size()-1)
                keyList.append(key).append(", ");
            else
                keyList.append(key);
            index++;
        }
        keyList.append("]");

        return keyList.toString();
    }
}
