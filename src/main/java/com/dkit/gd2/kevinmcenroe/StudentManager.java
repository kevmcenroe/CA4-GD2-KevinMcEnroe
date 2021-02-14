package com.dkit.gd2.kevinmcenroe;
// StudentManager encapsulates the storage and ability
// to manipulate student objects


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StudentManager {

    // Store all students in data structure
    // Fastest access to given CAO number = the key
    // Binary search
    // CAO numbers can be sorted alphabetically
    //List<Student> students = new ArrayList<>(100);
    HashMap<Integer, Student> students = new HashMap<Integer, Student>();

    public StudentManager() {
        // Hardcode some values to get started

        // later, load from text file "students.dat" and populate studentsMap
    }

    public StudentManager(List<Student> students) {
        for(Student student : students){
            if(students.contains(student))
                this.students.put(student.getCaoNumber(), student);
            else
                System.out.println("This student already exists in the map");
        }
    }

    // Adapted from my CA3 submission
    protected List<Student> loadStudentsFromFile(){
        List<Student> readStudents = new ArrayList<>();

        try(Scanner studentsFile = new Scanner(new BufferedReader(new FileReader("students.dat"))))
        {
            String input;
            while(studentsFile.hasNextLine())
            {
                input = studentsFile.nextLine();
                String[] data = input.split(",");
                int caoNumber = Integer.parseInt(data[0]);
                String dateOfBirth = data[1];
                String password = data[2];
                String email = data[3];

                Student readStudent = new Student(caoNumber, dateOfBirth, password, email);
                readStudents.add(readStudent);
            }
        }
        catch(FileNotFoundException fne)
        {
            System.out.println("Unable to read students.dat");
        }

        return readStudents;
    }

    public Student getStudent(int caoNumber) {
        Student matchingStudent = students.get(caoNumber);
        return matchingStudent;
    }
//
//    public addStudent() {
//    }
//
//    public removeStudent() {
//    }

//    isRegistered( caoNumber)
//        students.isValid()
}
