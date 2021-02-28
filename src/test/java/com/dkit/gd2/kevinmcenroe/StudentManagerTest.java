// Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StudentManagerTest {

    @Test
    public void loadStudentsFromFile() {
        HashMap<Integer, Student> expectedMap = new HashMap<>();
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
    public void writeToFile() {
        HashMap<Integer, Student> expectedMap = new HashMap<>();
        Student expectedStudentA = new Student(23000,"2000-02-25","charles123","charles@gmail.com");
        Student expectedStudentB = new Student(90700,"1999-12-25","rose123","rose@gmail.com");
        Student expectedStudentC = new Student(88000,"2006-12-25","roger123","roger@gmail.com");
        expectedMap.put(expectedStudentA.getCaoNumber(), expectedStudentA);
        expectedMap.put(expectedStudentB.getCaoNumber(), expectedStudentB);
        expectedMap.put(expectedStudentC.getCaoNumber(), expectedStudentC);

        StudentManager studentManager = new StudentManager();
        studentManager.writeToFile(expectedMap, "teststudents.txt");

        HashMap<Integer, Student> actualMap = new HashMap<>();
        studentManager.loadStudentsFromFile(actualMap, "teststudents.txt");

        assertEquals(expectedMap, actualMap);
    }
}