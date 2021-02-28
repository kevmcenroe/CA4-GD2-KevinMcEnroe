// Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe;

import java.io.*;
import java.util.*;

public class StudentManager {

    // Store all students in data structure
    // Fastest access to given CAO number = the key
    HashMap<Integer, Student> studentsMap = new HashMap<>();

    // Constructor
    public StudentManager() {
        loadStudentsFromFile(this.studentsMap, "students.dat");
    }

    // Adapted from my CA3 submission
    protected void loadStudentsFromFile(Map<Integer, Student> studentMap, String readFile){

        try(Scanner studentsFile = new Scanner(new BufferedReader(new FileReader(readFile))))
        {
            String input;
            //System.out.println("Reading students from file...");
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

    public boolean isRegistered(int caoNumber){
        return this.studentsMap.containsKey(caoNumber);
    }

    public Student getStudent(int caoNumber) {
        Student matchingStudent = this.studentsMap.get(caoNumber);
        return new Student(matchingStudent);
    }

    public void displayStudent() {
        int caoNumber = Integer.parseInt(getInput("CAO Number"));
        if(studentsMap.containsKey(caoNumber))
        {
            Student studentToDisplay = getStudent(caoNumber);
            System.out.println(IColours.GREEN + studentToDisplay + IColours.RESET);
        }
        else
            System.out.println(IColours.RED + "A student of CAO number " + caoNumber + " does not exist" + IColours.RESET);
    }

    public void addStudent(Student studentToAdd) {
        if(isRegistered(studentToAdd.getCaoNumber()))
            System.out.println(IColours.RED + "A student of CAO number " + studentToAdd.getCaoNumber() + " already exists" + IColours.RESET);
        else{
            Student studentClone = new Student(studentToAdd);
            this.studentsMap.put(studentClone.getCaoNumber(), studentClone);
            System.out.println(IColours.GREEN + "Added " + studentClone + IColours.RESET);
            writeToFile(studentsMap, "students.dat");
        }
    }

    public void displayAddStudent() {
        System.out.println("Creating a student...");
        int caoNumber = Integer.parseInt(getInput("CAO Number"));
        String dateOfBirth = getInput("Date of Birth");
        String password = getInput("Password");
        String email = getInput("Email");

        Student generatedStudent = new Student(caoNumber, dateOfBirth, password, email);
        addStudent(generatedStudent);
    }

    public void removeStudent(int caoNumber) {
        if (this.studentsMap.containsKey(caoNumber)){
            Student studentToRemove = studentsMap.get(caoNumber);
            System.out.println(IColours.GREEN + "Removed " + studentToRemove + IColours.RESET);
            this.studentsMap.remove(caoNumber);
            writeToFile(studentsMap, "students.dat");
        }
        else
            System.out.println(IColours.RED + "A student of CAO number " + caoNumber + " does not exist" + IColours.RESET);
    }

    public void displayRemoveStudent() {
        int removingCAONum = Integer.parseInt(getInput("CAO Number of the student to be removed"));

        removeStudent(removingCAONum);
    }

    // Adapted from my CA3 submission
    protected void writeToFile(HashMap<Integer, Student> mapOfStudents, String writeFile)
    {
        try(BufferedWriter studentsFile = new BufferedWriter(new FileWriter(writeFile))) {
            for(Map.Entry<Integer, Student> entry : mapOfStudents.entrySet())
            {
                studentsFile.write(entry.getValue().getCaoNumber() + "," + entry.getValue().getDayOfBirth() + "," + entry.getValue().getPassword() + "," + entry.getValue().getEmail() + "\n");
            }
        }
        catch(IOException ioe)
        {
            System.out.println(IColours.RED + "Could not write to file (IOException)" + IColours.RESET);
        }
    }

    private static final Scanner keyboard = new Scanner(System.in);

    //Adapted from my CA3 submission
    private String getInput(String requested) {
        String input;
        System.out.print("Please enter " + requested + " :>");

        input = keyboard.nextLine();
        return input;
    }
}
