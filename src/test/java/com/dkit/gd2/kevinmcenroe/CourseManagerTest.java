package com.dkit.gd2.kevinmcenroe;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CourseManagerTest {

    @Test
    public void loadCoursesFromFile() {
        HashMap<String, Course> expectedMap = new HashMap<>();
        Course expectedCourseA = new Course("DK700","8","Testing and Research","DKIT");
        Course expectedCourseB = new Course("DK999","7","Quality Assurance","DKIT");
        expectedMap.put(expectedCourseA.getCourseId(), expectedCourseA);
        expectedMap.put(expectedCourseB.getCourseId(), expectedCourseB);

        CourseManager courseManager = new CourseManager();
        HashMap<String, Course> actualMap = new HashMap<>();
        courseManager.loadCoursesFromFile(actualMap, "testcourses.txt");
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void writeToFile() {
        HashMap<String, Course> expectedMap = new HashMap<>();
        Course expectedCourseA = new Course("DK567","8","Architecture","DKIT");
        Course expectedCourseB = new Course("DK908","7","Construction","DKIT");
        Course expectedCourseC = new Course("DK727","6","Hospitality Management","DKIT");
        expectedMap.put(expectedCourseA.getCourseId(), expectedCourseA);
        expectedMap.put(expectedCourseB.getCourseId(), expectedCourseB);
        expectedMap.put(expectedCourseC.getCourseId(), expectedCourseC);

        CourseManager courseManager = new CourseManager();
        courseManager.writeToFile(expectedMap, "testcourses.txt");

        HashMap<String, Course> actualMap = new HashMap<>();
        courseManager.loadCoursesFromFile(actualMap, "testcourses.txt");

        assertEquals(expectedMap, actualMap);
    }
}