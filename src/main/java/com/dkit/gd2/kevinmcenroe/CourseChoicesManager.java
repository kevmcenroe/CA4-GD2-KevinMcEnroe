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

import java.util.ArrayList;
import java.util.HashMap;

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
    //
    CourseChoicesManager(com.dkit.gd2.kevinmcenroe.StudentManager studentManager, CourseManager courseManager) {
        this.studentManager = studentManager;
        this.courseManager = courseManager;

    }

    public Student getStudentDetails(int caoNumber) {
        Student matchingStudent = null;
        if(studentManager.studentsMap.containsKey(caoNumber))
            matchingStudent = studentManager.studentsMap.get(caoNumber);
        else{
            System.out.println("A student of CAO number " + caoNumber + " does not exist in the student map");
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

    //Pseudocode below is not final. This is my initial breakdown.

    public void getStudentChoices(){
        //TO DO - READ BRIEF
        //Maybe take input from a menu first
        //Feed that into a map of key caoNumber
        //Then search for a match in the map
    }

    void updateChoices() {
        //TO DO - READ BRIEF
        //Search in map for matching caoNumber provided
        //Get reference to the courseChoices of that student
        //Take input/updated values from menu
        //Overwrite existing data in map with new values provided
        //Handle exceptions!
    }

    public void getAllCourses() {
        //TO DO - READ BRIEF
        //Has to return a LIST of all courses
        //Cycle through courseDetails map perhaps
        //Return their data, maybe output it too
    }

    boolean login() {
        //TO DO - READ BRIEF
        //Take input via menu for caoNumber, dateOfBirth etc
        //Check if they are a perfect match with any of the students in students.dat
        //If there is a match, allow the menu system to continue maybe
        return true;
    }


}
