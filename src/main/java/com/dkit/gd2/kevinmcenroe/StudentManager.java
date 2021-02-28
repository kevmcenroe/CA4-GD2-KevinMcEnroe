// Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe;

//import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String dateOfBirth = getValidDateOfBirth();
        String password = getValidPassword();
        String email = getValidEmail();

        Student generatedStudent = new Student(caoNumber, dateOfBirth, password, email);
        addStudent(generatedStudent);
    }

    private String getValidDateOfBirth(){
        String dateOfBirth = getInput("Date of Birth in YYYY-MM-DD format");
        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile((datePattern));
        Matcher matcher = pattern.matcher(dateOfBirth);

        if(!matcher.matches()) {
            System.out.println(IColours.RED + "Invalid Date of Birth" + IColours.RESET);
            getValidDateOfBirth();
        }

        return dateOfBirth;

        /*  Previous String.matches attempt:
        String regex = "\"^\\\\d{4}-\\\\d{2}-\\\\d{2}$\"";
        String dateOfBirth = getInput("Date of Birth in YYYY-MM-DD format");
        if(dateOfBirth.matches(regex) == false) {
            System.out.println(IColours.RED + "Invalid Date of Birth" + IColours.RESET);
            getValidDateOfBirth();
        }*/
    }

    private String getValidPassword(){
        String password = getInput("Password of 5 to 20 Characters");
        String passwordPattern = "^[a-zA-Z0-9]{8,20}$";
        Pattern pattern = Pattern.compile((passwordPattern));
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()) {
            System.out.println(IColours.RED + "Invalid Password" + IColours.RESET);
            getValidPassword();
        }

        return password;
    }

    private String getValidEmail(){
        String email = getInput("Email");
        String emailPattern = "^(.+)@(.+)?\\.[a-zA-Z]{2,15}$"; //Guarantees the @ symbol and .com etc
        Pattern pattern = Pattern.compile((emailPattern));
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()) {
            System.out.println(IColours.RED + "Invalid Email" + IColours.RESET);
            getValidEmail();
        }

        return email;
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
