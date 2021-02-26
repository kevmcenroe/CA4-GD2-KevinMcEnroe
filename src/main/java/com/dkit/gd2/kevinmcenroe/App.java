package com.dkit.gd2.kevinmcenroe;

import java.util.InputMismatchException;
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

        // Loading of students initiated here
        com.dkit.gd2.kevinmcenroe.StudentManager studentManager = new com.dkit.gd2.kevinmcenroe.StudentManager();

        // Loading of courses initiated here
        CourseManager courseManager = new CourseManager();

        com.dkit.gd2.kevinmcenroe.CourseChoicesManager courseChoicesManager = new com.dkit.gd2.kevinmcenroe.CourseChoicesManager(studentManager, courseManager);

        doMainMenuLoop(studentManager, courseChoicesManager, courseManager);
    }

    //Adapted from my CA3 submission
    private void doMainMenuLoop(StudentManager studentManager, CourseChoicesManager courseChoicesManager, CourseManager courseManager)
    {
        boolean loop = true;
        int option;
        while(loop)
        {
            printMainMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.length() != 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= MainMenu.values().length)
                    throw new IllegalArgumentException();

                MainMenu menuOption = MainMenu.values()[option];
                switch (menuOption)
                {
                    case QUIT_APPLICATION:
                        loop = false;
                        break; // exit the loop
                    case STUDENT:
                        Student loggedIn = courseChoicesManager.displayLogin();
                        if(loggedIn != null)
                            doLoggedInMenuLoop(courseChoicesManager, loggedIn);
                        break;
                    case ADMINISTRATOR:
                        doAdminMenuLoop(studentManager, courseChoicesManager, courseManager);
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
                System.out.println(IColours.RED + "Please enter a valid option" + IColours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private void doLoggedInMenuLoop(CourseChoicesManager courseChoicesManager, Student student)
    {
        boolean loop = true;
        int option;
        while(loop)
        {
            printLoggedInMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.length() != 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= StudentMenu.values().length)
                    throw new IllegalArgumentException();

                courseChoicesManager.syncCourseData();
                StudentMenu menuOption = StudentMenu.values()[option];
                switch (menuOption)
                {
                    case DISPLAY_A_COURSE:
                        courseChoicesManager.displayCourseDetails();
                        break; // exit the loop
                    case DISPLAY_ALL_COURSES:
                        courseChoicesManager.displayAllCourses();
                        break;
                    case DISPLAY_CURRENT_CHOICES:
                        courseChoicesManager.displayStudentChoices(student.getCaoNumber());
                        break;
                    case UPDATE_CHOICES:
                        courseChoicesManager.displayUpdateChoices(student.getCaoNumber());
                        break;
                    case LOG_OUT:
                        loop = false;
                        break; // exit the loop
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println(IColours.RED + "Please enter a valid option (InputMismatchException - " + ime.getMessage() + ")" + IColours.RESET);
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(IColours.RED + "Please enter a valid option (IllegalArgumentException - " + iae.getMessage() + ")" + IColours.RESET);
            }
        }
        System.out.println("Thanks for using the student menu");
    }

    private void doAdminMenuLoop(StudentManager studentManager, CourseChoicesManager courseChoicesManager, CourseManager courseManager)
    {
        boolean loop = true;
        int option;
        while(loop)
        {
            printAdminMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.length() != 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= AdminMenu.values().length)
                    throw new IllegalArgumentException();

                AdminMenu menuOption = AdminMenu.values()[option];
                switch (menuOption)
                {
                    case ADD_A_COURSE:
                        courseManager.displayAddCourse();
                        break;
                    case REMOVE_A_COURSE:
                        courseManager.displayRemoveCourse();
                        break;
                    case DISPLAY_ALL_COURSES:
                        courseManager.displayAllCourses();
                        break;
                    case DISPLAY_A_COURSE:
                        courseManager.displayCourse();
                        break;
                    case ADD_STUDENT:
                        studentManager.displayAddStudent();
                        break;
                    case REMOVE_A_STUDENT:
                        studentManager.displayRemoveStudent();
                        break;
                    case DISPLAY_A_STUDENT:
                        studentManager.displayStudent();
                        break;
                    case SAVE_AND_EXIT:
                        loop = false;
                        doMainMenuLoop(studentManager, courseChoicesManager, courseManager);
                        break; // exit the loop*/
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println(IColours.RED + "Please enter a valid option (InputMismatchException - " + ime.getMessage() + ")" + IColours.RESET);
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(IColours.RED + "Please enter a valid option (IllegalArgumentException - " + iae.getMessage() + ")" + IColours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    //Adapted from my CA3 submission
    private void printMainMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < MainMenu.values().length; i++)
        {
            String menuOption = MainMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + IColours.BLUE + i + ". " + menuOption + IColours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    //Adapted from my CA3 submission
    private void printLoggedInMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i = 0; i < StudentMenu.values().length; i++)
        {
            String menuOption = StudentMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + IColours.BLUE + i + ". " + menuOption + IColours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    //Adapted from my CA3 submission
    private void printAdminMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < AdminMenu.values().length; i++)
        {
            String menuOption = AdminMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + IColours.BLUE + i + ". " + menuOption + IColours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }
}
