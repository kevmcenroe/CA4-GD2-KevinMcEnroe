package com.dkit.gd2.kevinmcenroe;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * Notes:
 *  Synchronisation of multiple reads and writes to file is not considered here.
 *
 */
public class App 
{
    public static Scanner keyboard = new Scanner(System.in);

    public static void main( String[] args )
    {
        System.out.println( "CAO Online - CA4" );
        new App().start();
        // same as saying new application,
        // just leaving out the left hand side
    }

    private void start() {

        // load students
        com.dkit.gd2.kevinmcenroe.StudentManager studentManager = new com.dkit.gd2.kevinmcenroe.StudentManager();

        // load courses
        CourseManager courseManager= new CourseManager();

        // load manager to provide functionality to allow a student
        // to login and add/update their course selections
        // This CourseChoicesManager component depends on the
        // StudentManager and the CourseManager,
        // so we 'inject' or pass-in these objects.
        //

        com.dkit.gd2.kevinmcenroe.CourseChoicesManager courseChoicesManager = new com.dkit.gd2.kevinmcenroe.CourseChoicesManager(studentManager, courseManager);

        // display a menu to do things
        // manual testing of mgr public interface
        doMainMenuLoop(studentManager, courseChoicesManager);

//        if ( mgr.login(22224444, "xxxx","bbbb") )
//        {
//            Student student = mgr.getStudentDetails(22224444);
//
//            System.out.println("Student: " + student);
//        }
//        else
//            System.out.println("Not logged in - try again");


        //mgr.saveToFile();

    }

    //Adapted from my CA3 submission
    private void doMainMenuLoop(StudentManager studentManager, CourseChoicesManager courseChoicesManager)
    {
        boolean loop = true;
        int option = -1;
        while(loop)
        {
            printMainMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.isEmpty() || input.length() > 1)
                {
                    throw new IllegalArgumentException();
                }
                else
                {
                    option = Integer.parseInt(input);
                }
                if(option < 0 || option >= MainMenu.values().length)
                {
                    throw new IllegalArgumentException();
                }

                MainMenu menuOption = MainMenu.values()[option];
                switch (menuOption)
                {
                    case QUIT_APPLICATION:
                        loop = false;
                        break; // exit the loop
                    case STUDENT_LOG_IN:
                        Student loggedIn = courseChoicesManager.requestLogin();
                        if(loggedIn != null)
                            doLoggedInMenuLoop(studentManager, courseChoicesManager, loggedIn);
                        else
                            System.out.println("IT WAS NULL");
                        break;
                    case COURSE_PORTAL:

                        break;
                    case STUDENT_PORTAL:

                        break;
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println("Please enter a valid option");
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private void doLoggedInMenuLoop(StudentManager studentManager, CourseChoicesManager courseChoicesManager, Student student)
    {
        boolean loop = true;
        int option = -1;
        while(loop)
        {
            printLoggedInMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.isEmpty() || input.length() > 1)
                {
                    throw new IllegalArgumentException();
                }
                else
                {
                    option = Integer.parseInt(input);
                }
                if(option < 0 || option >= MainMenu.values().length)
                {
                    throw new IllegalArgumentException();
                }

                LoggedInMenu menuOption = LoggedInMenu.values()[option];
                switch (menuOption)
                {
                    case LOG_OUT:
                        loop = false;
                        break; // exit the loop
                    case VIEW_COURSE_CHOICES:
                        courseChoicesManager.printStudentChoices(student.getCaoNumber());
                        break;
                    case UPDATE_COURSE_CHOICES:
                        courseChoicesManager.requestUpdateChoices(student.getCaoNumber());
                        break;
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println("Please enter a valid option");
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    //Adapted from my CA3 submission
    private String requestInput(String requested) {
        String input;
        System.out.print("Please enter " + requested + " :>");

        input = keyboard.nextLine();
        return input;
    }

    //Adapted from my CA3 submission
    private void printMainMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < MainMenu.values().length; i++)
        {
            String menuOption = MainMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    //Adapted from my CA3 submission
    private void printLoggedInMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < LoggedInMenu.values().length; i++)
        {
            String menuOption = LoggedInMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }
}
