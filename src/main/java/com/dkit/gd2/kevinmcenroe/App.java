package com.dkit.gd2.kevinmcenroe;

/**
 *
 * Notes:
 *  Synchronisation of multiple reads and writes to file is not considered here.
 *
 */
public class App 
{
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
        com.dkit.gd2.kevinmcenroe.CourseChoicesManager mgr = new com.dkit.gd2.kevinmcenroe.CourseChoicesManager(studentManager, courseManager);

        // display a menu to do things
        // manual testing of mgr public interface

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
}
