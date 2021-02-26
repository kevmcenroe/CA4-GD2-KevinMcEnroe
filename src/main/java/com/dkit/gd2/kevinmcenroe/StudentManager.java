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
        if(this.studentsMap.containsKey(caoNumber)){
            return true;
        }
        else{
            return false;
        }
    }

    public Student getStudent(int caoNumber) {
        Student matchingStudent = this.studentsMap.get(caoNumber);
        Student studentClone = new Student(matchingStudent);
        return studentClone;
    }

    public void addStudent(Student studentToAdd) {
        if(isRegistered(studentToAdd.getCaoNumber()))
            System.out.println("A student of CAO number " + studentToAdd.getCaoNumber() + " already exists in the students map");
        else{
            Student studentClone = new Student(studentToAdd);
            this.studentsMap.put(studentClone.getCaoNumber(), studentClone);
            System.out.println(IColours.GREEN + "Added " + studentClone + IColours.RESET);
            writeToFile();
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
            this.studentsMap.remove(caoNumber);
            writeToFile();
        }
        else
            System.out.println("A student of CAO number " + caoNumber + " does not exist in the student map");

    }

    public void displayRemoveStudent() {
        int removingCAONum = Integer.parseInt(getInput("CAO Number of the student to be removed"));

        removeStudent(removingCAONum);
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
            System.out.println(IColours.RED + "Could not write to file (IOException)" + IColours.RESET);
        }
    }

    private static Scanner keyboard = new Scanner(System.in);

    //Adapted from my CA3 submission
    private String getInput(String requested) {
        String input;
        System.out.print("Please enter " + requested + " :>");

        input = keyboard.nextLine();
        return input;
    }


}
