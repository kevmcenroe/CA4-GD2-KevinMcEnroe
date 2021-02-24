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
        int option = -1;
        while(loop)
        {
            printMainMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.isEmpty() || input.length() > 1)
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
                        Student loggedIn = courseChoicesManager.requestLogin();
                        if(loggedIn != null)
                            doLoggedInMenuLoop(studentManager, courseChoicesManager, loggedIn);
                        break;
                    case ADMINISTRATOR:
                        doAdminMenuLoop(studentManager, courseManager);
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
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= LoggedInMenu.values().length)
                    throw new IllegalArgumentException();

                LoggedInMenu menuOption = LoggedInMenu.values()[option];
                switch (menuOption)
                {
                    case DISPLAY_A_COURSE:
                        courseChoicesManager.displayCourseDetails();
                        break; // exit the loop
                    case DISPLAY_ALL_COURSES:
                        courseChoicesManager.displayAllCourses();
                        break;
                    case DISPLAY_CURRENT_CHOICES:
                        courseChoicesManager.printStudentChoices(student.getCaoNumber());
                        break;
                    case UPDATE_CHOICES:
                        courseChoicesManager.requestUpdateChoices(student.getCaoNumber());
                        break;
                    case LOG_OUT:
                        loop = false;
                        break; // exit the loop
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println(Colours.RED + "Please enter a valid option (InputMismatchException - " + ime.getMessage() + ")" + Colours.RESET);
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(Colours.RED + "Please enter a valid option (IllegalArgumentException - " + iae.getMessage() + ")" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private void doAdminMenuLoop(StudentManager studentManager, CourseManager courseManager)
    {
        boolean loop = true;
        int option = -1;
        while(loop)
        {
            printAdminMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.isEmpty() || input.length() > 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= AdminMenu.values().length)
                    throw new IllegalArgumentException();

                AdminMenu menuOption = AdminMenu.values()[option];
                switch (menuOption)
                {
                    case ADD_A_COURSE:
                        courseManager.requestAddCourse();
                        break;
                    case REMOVE_A_COURSE:
                        courseManager.requestRemoveCourse();
                        break;
                    case DISPLAY_ALL_COURSES:

                        break;
                    case DISPLAY_A_COURSE:

                        break;
                    case ADD_STUDENT:

                        break;
                    case REMOVE_A_STUDENT:

                        break;
                    case DISPLAY_A_STUDENT:

                        break;
                    case SAVE_AND_EXIT:
                        loop = false;
                        break; // exit the loop*/
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println(Colours.RED + "Please enter a valid option (InputMismatchException - " + ime.getMessage() + ")" + Colours.RESET);
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(Colours.RED + "Please enter a valid option (IllegalArgumentException - " + iae.getMessage() + ")" + Colours.RESET);
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

    //Adapted from my CA3 submission
    private void printAdminMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < AdminMenu.values().length; i++)
        {
            String menuOption = AdminMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }
}
