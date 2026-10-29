/*	Author:				Bushra Rahman
	Course:				CS 2336 (Spring 2020)
   	Date:				1/30/2020
	Main.java:			This source file is the main driver of the program. */

package Tickets;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) //throws java.io.IOException			// main() does not need to throw IOException as long as Auditorium() & displayMainMenu() are called in a try/catch block
	{
		/*	IOException handling: 
			read_seating_arrangement_file() throws to the class constructor Auditorium(), 
			and writeToFile() throws to the driver method displayMainMenu(). 
			Both the constructor & driver method also throw to where they are called, ie. main(). 
			So in main(), a try/catch block is used to handle any IOException. 
			Without these blocks, uncaught exceptions propagate to the JVM, which defaults to printing the call stack and terminating the program. */
		try
		{
			Auditorium auditorium = new Auditorium();							// create an Auditorium object
			auditorium.displayMainMenu();										// call its driver method
		}
		catch(IOException fileException)
		{
        	System.out.println("File error: " + fileException.getMessage());	// print any file error for front-facing user to see
			/// fileException.printStackTrace();								// un-comment for debugging info for back-end developer use
		}
	}
}	// end of class Main
