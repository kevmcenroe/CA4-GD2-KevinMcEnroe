package com.dkit.gd2.kevinmcenroe;


import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * CoursesManager
 * This software component Encapsulates the storage and management of
 * all courses available through the CAO system.
 * Only administrators would typically be allowed to update this data,
 * but other users can get a COPY of all the courses by calling getAllCourses().
 * The Web Client would need this data to display the course codes,
 * course titles and institutions to the student.
 */

public class CourseManager {

    HashMap<String, Course> coursesMap = new HashMap<String, Course>();

    // Constructor
    public CourseManager() {
        loadCoursesFromFile(this.coursesMap, "courses.dat");
    }

    // Adapted from my StudentManager
    protected void loadCoursesFromFile(Map<String, Course> courseMap, String readFile){

        try(Scanner coursesFile = new Scanner(new BufferedReader(new FileReader(readFile))))
        {
            String input;
            System.out.println("Reading courses from file...");
            while(coursesFile.hasNextLine())
            {
                input = coursesFile.nextLine();
                String[] data = input.split(",");
                String courseID = data[0];
                String level = data[1];
                String title = data[2];
                String institution = data[3];

                Course readCourse = new Course(courseID, level, title, institution);
                courseMap.put(readCourse.getCourseId(), readCourse);
                System.out.println(Colours.GREEN + "Course added to the courses map. Course ID number: " + readCourse.getCourseId() + Colours.RESET);
            }
        }
        catch(FileNotFoundException fne)
        {
            System.out.println("Unable to read file (FileNotFoundException)");
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Input data type does not match that required (NumberFormatException)");
        }
    }

    private boolean isAlreadyRegistered(Course courseToCheck){
        if(this.coursesMap.containsKey(courseToCheck.getCourseId())) {
            System.out.println("A course of course ID " + courseToCheck.getCourseId() + " already exists in the courses map");
            return true;
        }
        else{
            return false;
        }

    }

    public Course getCourse(String courseID) {
        Course matchingCourse = this.coursesMap.get(courseID);
        Course courseClone = new Course(matchingCourse);

        System.out.println("Matching student found at address " + matchingCourse);
        System.out.println("Clone student saved to address " + courseClone);
        return matchingCourse;
    }

    public List<Course> getAllCourses(){
        List<Course> allCourses = new ArrayList<>();
        Iterator courseIterator = this.coursesMap.entrySet().iterator();

        while (courseIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)courseIterator.next();
            Course course = (Course)mapElement.getValue();
            allCourses.add(course);
        }
        return allCourses;
    }

    private void addCourse(Course courseToAdd) {
        if(isAlreadyRegistered(courseToAdd)){
            System.out.println("A course of course ID " + courseToAdd.getCourseId() + " already exists in the courses map");
        }
        else{
            Course courseClone = new Course(courseToAdd);
            this.coursesMap.put(courseClone.getCourseId(), courseClone);
            writeToFile();
        }
    }

    public void requestAddCourse(){
        String courseID = getInput("Course ID");
        String level = getInput("Level");
        String title = getInput("Title");
        String institution = getInput("Institution");

        Course generatedCourse = new Course(courseID, level, title, institution);
        addCourse(generatedCourse);
    }

    private void isValidCourseID(){
        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern pattern = Pattern.compile(regex);

        //TO DO
    }

    public void removeCourse(String courseID) {
        if (this.coursesMap.containsKey(courseID)){
            this.coursesMap.remove(courseID);
            writeToFile();
        }
        else
            System.out.println("A course of course ID " + courseID + " does not exist in the courses map");
    }

    public void requestRemoveCourse(){
        System.out.println("Enter the ID of the course you wish to remove");
        printAvailableCourses();
        String courseID = getInput("Course ID");

        if(coursesMap.containsKey(courseID)){
            System.out.println(Colours.GREEN + "Removed "+ coursesMap.get(courseID) + Colours.RESET);
            coursesMap.remove(courseID);
        }
        else
            System.out.println(Colours.RED + "Unable to find course of ID " + courseID + Colours.RESET);
    }

    private void printAvailableCourses(){
        String availableCourseCodes = displaySortedKeys(coursesMap);
        System.out.println(Colours.BLUE + "Available Courses: " + availableCourseCodes + "\n" + Colours.RESET);
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

    // Adapted from my StudentManager
    public void writeToFile()
    {
        try(BufferedWriter coursesFile = new BufferedWriter(new FileWriter("courses.dat"))) {

            /*First approach:
            Iterator courseIterator = this.coursesMap.entrySet().iterator();

            while (courseIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)courseIterator.next();
                Course course = (Course)mapElement.getValue();
                coursesFile.write(course.getCourseId() + "," + course.getLevel() + "," + course.getTitle() + "," + course.getInstitution() +"\n");
            }*/
        }
        catch(IOException ioe)
        {
            System.out.println(Colours.RED + "Could not write to file (IOException)" +Colours.RESET);
        }
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







