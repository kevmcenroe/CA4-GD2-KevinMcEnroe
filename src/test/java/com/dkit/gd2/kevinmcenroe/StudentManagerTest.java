package com.dkit.gd2.kevinmcenroe;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StudentManagerTest {

    @Test
    public void loadStudentsFromFile() {
        HashMap<Integer, Student> expectedMap = new HashMap<Integer, Student>();
        Student expectedStudentA = new Student(10100,"2004-02-25","james123","james@gmail.com");
        Student expectedStudentB = new Student(22000,"2002-12-25","rob123","rob@gmail.com");
        expectedMap.put(expectedStudentA.getCaoNumber(), expectedStudentA);
        expectedMap.put(expectedStudentB.getCaoNumber(), expectedStudentB);

        StudentManager studentManager = new StudentManager();
        HashMap<Integer, Student> actualMap = new HashMap<Integer, Student>();
        studentManager.loadStudentsFromFile(actualMap, "teststudents.txt");
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void getStudent() {
    }

    @Test
    public void addStudent() {
        // should i create a new instance of student manager?
        //Student student = new Student(2345, "2000-12-12", "password", "mike@gmail.com");
        //actualResult = addStudent(student);
    }

    @Test
    public void removeStudent() {
    }

    @Test
    public void writeToFile() {
    }
}