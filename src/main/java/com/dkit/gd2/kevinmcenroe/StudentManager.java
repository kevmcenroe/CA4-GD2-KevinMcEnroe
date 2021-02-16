package com.dkit.gd2.kevinmcenroe;
// StudentManager encapsulates the storage and ability
// to manipulate student objects


import java.io.*;
import java.util.*;

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
            if(isAlreadyRegistered(student))
                System.out.println("A student of CAO number " + student.getCaoNumber() + " already exists in the students map");
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
        System.out.println("Matching student found at address " + matchingStudent);
        Student studentClone = cloneStudent(matchingStudent);
        System.out.println("Clone student saved to address " + studentClone);
        return matchingStudent;
    }

    public void addStudent(Student studentToAdd) {
        if(isAlreadyRegistered(studentToAdd)){
            System.out.println("A student of CAO number " + studentToAdd.getCaoNumber() + " already exists in the students map");
        }
        else{
            Student studentClone = cloneStudent(studentToAdd);
            this.studentsMap.put(studentClone.getCaoNumber(), studentClone);
            writeToFile();
        }
    }

    public void removeStudent(int caoNumber) {
        this.studentsMap.remove(caoNumber);
        writeToFile();
    }

    // Adapted from my CA3 submission
    public void writeToFile()
    {
        try(BufferedWriter studentsFile = new BufferedWriter(new FileWriter("students.txt"))) {

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
