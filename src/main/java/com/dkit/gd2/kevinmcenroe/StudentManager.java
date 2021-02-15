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
    HashMap<Integer, Student> studentsMap = new HashMap<Integer, Student>();

    // Constructor
    public StudentManager() {
        List<Student> studentsOnFile = loadStudentsFromFile();
        System.out.println("Adding students from file to new StudentManager...");
        for(Student student : studentsOnFile){
            if(this.studentsMap.containsKey(student))
                System.out.println("This student already exists in the students map");
            else {
                System.out.println(Colours.GREEN + "Added student with CAO number: " + student.getCaoNumber() + Colours.RESET);
                this.studentsMap.put(student.getCaoNumber(), student);
            }
        }

        // Testing

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
            System.out.println("Unable to read students.dat (FileNotFoundException)");
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Input data type does not match that required (NumberFormatException)");
        }

        return readStudents;
    }

    private Student cloneStudent(Student studentToClone){
        return new Student(studentToClone.getCaoNumber(), studentToClone.getDayOfBirth(), studentToClone.getPassword(), studentToClone.getEmail());
    }
    public Student getStudent(int caoNumber) {
        Student matchingStudent = this.studentsMap.get(caoNumber);
        System.out.println("Matching student found at address " + matchingStudent);
        Student studentClone = cloneStudent(matchingStudent);
        System.out.println("Clone student saved to address " + studentClone);
        return matchingStudent;
    }

    public void addStudent(Student studentToAdd) {
        Student studentClone = cloneStudent(studentToAdd);
    }

//    public removeStudent() {
//    }

//    isRegistered( caoNumber)
//        students.isValid()
}
