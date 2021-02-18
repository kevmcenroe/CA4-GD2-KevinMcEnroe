package com.dkit.gd2.kevinmcenroe;
// StudentManager encapsulates the storage and ability
// to manipulate student objects


import java.io.*;
import java.util.*;

public class StudentManager {

    // Store all students in data structure
    // Fastest access to given CAO number = the key
    HashMap<Integer, Student> studentsMap = new HashMap<Integer, Student>();

    // Constructor
    public StudentManager() {
        loadStudentsFromFile(this.studentsMap, "students.dat");
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
/*
    Retired in favour of the Student copy constructor
    private Student cloneStudent(Student studentToClone){
        return new Student(studentToClone.getCaoNumber(), studentToClone.getDayOfBirth(), studentToClone.getPassword(), studentToClone.getEmail());
    }
*/
    private boolean isAlreadyRegistered(Student studentToCheck){
        if(this.studentsMap.containsKey(studentToCheck.getCaoNumber())) {
            System.out.println("A student of CAO number " + studentToCheck.getCaoNumber() + " already exists in the students map");
            return true;
        }
        else{
            return false;
        }

    }
    public Student getStudent(int caoNumber) {
        Student matchingStudent = this.studentsMap.get(caoNumber);
        Student studentClone = new Student(matchingStudent);

        System.out.println("Matching student found at address " + matchingStudent);
        System.out.println("Clone student saved to address " + studentClone);
        return matchingStudent;
    }

    public void addStudent(Student studentToAdd) {
        if(isAlreadyRegistered(studentToAdd)){
            System.out.println("A student of CAO number " + studentToAdd.getCaoNumber() + " already exists in the students map");
        }
        else{
            Student studentClone = new Student(studentToAdd);
            this.studentsMap.put(studentClone.getCaoNumber(), studentClone);
            writeToFile();
        }
    }

    public void removeStudent(int caoNumber) {
        if (this.studentsMap.containsKey(caoNumber)){
            this.studentsMap.remove(caoNumber);
            writeToFile();
        }
        else
            System.out.println("A student of CAO number " + caoNumber + " does not exist in the student map");

    }

    // Adapted from my CA3 submission
    public void writeToFile()
    {
        try(BufferedWriter studentsFile = new BufferedWriter(new FileWriter("students.dat"))) {

            Iterator studentIterator = this.studentsMap.entrySet().iterator();

            while (studentIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)studentIterator.next();
                Student student = (Student)mapElement.getValue();
                studentsFile.write(student.getCaoNumber() + "," + student.getDayOfBirth() + "," + student.getPassword() + "," + student.getEmail() +"\n");
            }
        }
        catch(IOException ioe)
        {
            System.out.println(Colours.RED + "Could not write to file (IOException)" +Colours.RESET);
        }
    }
}
