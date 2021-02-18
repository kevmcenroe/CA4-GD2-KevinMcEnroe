package com.dkit.gd2.kevinmcenroe;


import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

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

    // Store all students in data structure
    // Fastest access to given CAO number = the key
    // Binary search
    // CAO numbers can be sorted alphabetically
    //List<Student> students = new ArrayList<>(100);
    HashMap<Integer, Student> coursesMap = new HashMap<Integer, Student>();

    // Constructor
    public CourseManager() {
        loadStudentsFromFile(this.coursesMap, "courses.dat");
    }

    // Adapted from my CA3 submission
    protected void loadStudentsFromFile(Map<Integer, Student> studentMap, String readFile){

        try(Scanner studentsFile = new Scanner(new BufferedReader(new FileReader(readFile))))
        {
            String input;
            System.out.println("Reading students from file...");
            while(studentsFile.hasNextLine())
            {
                input = studentsFile.nextLine();
                String[] data = input.split(",");
                int caoNumber = Integer.parseInt(data[0]);
                String dateOfBirth = data[1];
                String password = data[2];
                String email = data[3];

                Student readStudent = new Student(caoNumber, dateOfBirth, password, email);
                studentMap.put(readStudent.getCaoNumber(), readStudent);
                System.out.println(Colours.GREEN + "Student added to the student map. CAO number: " + readStudent.getCaoNumber() + Colours.RESET);
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

    private Student cloneStudent(Student studentToClone){
        return new Student(studentToClone.getCaoNumber(), studentToClone.getDayOfBirth(), studentToClone.getPassword(), studentToClone.getEmail());
    }

    private boolean isAlreadyRegistered(Student studentToCheck){
        if(this.coursesMap.containsKey(studentToCheck.getCaoNumber())) {
            System.out.println("A student of CAO number " + studentToCheck.getCaoNumber() + " already exists in the students map");
            return true;
        }
        else{
            return false;
        }

    }
    public Student getStudent(int caoNumber) {
        Student matchingStudent = this.coursesMap.get(caoNumber);
        Student studentClone = cloneStudent(matchingStudent);

        System.out.println("Matching student found at address " + matchingStudent);
        System.out.println("Clone student saved to address " + studentClone);
        return matchingStudent;
    }

    public void addStudent(Student studentToAdd) {
        if(isAlreadyRegistered(studentToAdd)){
            System.out.println("A student of CAO number " + studentToAdd.getCaoNumber() + " already exists in the students map");
        }
        else{
            Student studentClone = cloneStudent(studentToAdd);
            this.coursesMap.put(studentClone.getCaoNumber(), studentClone);
            writeToFile();
        }
    }

    public void removeStudent(int caoNumber) {
        if (this.coursesMap.containsKey(caoNumber)){
            this.coursesMap.remove(caoNumber);
            writeToFile();
        }
        else
            System.out.println("A student of CAO number " + caoNumber + " does not exist in the student map");

    }

    // Adapted from my CA3 submission
    public void writeToFile()
    {
        try(BufferedWriter coursesFile = new BufferedWriter(new FileWriter("courses.dat"))) {

            Iterator studentIterator = this.coursesMap.entrySet().iterator();

            while (studentIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)studentIterator.next();
                Student student = (Student)mapElement.getValue();
                coursesFile.write(student.getCaoNumber() + "," + student.getDayOfBirth() + "," + student.getPassword() + "," + student.getEmail() +"\n");
            }
        }
        catch(IOException ioe)
        {
            System.out.println(Colours.RED + "Could not write to file (IOException)" +Colours.RESET);
        }
    }

/*
    // Store all the Course details.
    // Requires fast access given courseId.

    public CourseManager() {
        // Hardcode some values to get started
        // load from text file "courses.dat" and populate coursesMap
    }

//    public  getCourse( ) {
//    }
//
//
//    public  getAllCourses() {
//    }
//
//    public addCourse() {
//    }
//
//    public removeCourse() {
//    }

    // editCourse(courseId);       // not required for this iteration
*/
}







