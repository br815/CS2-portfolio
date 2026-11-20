/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	Main.java:				This source file is the main driver of the program. */

package DrinkRewards;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) //throws java.io.IOException			// main() does not need to throw an I/O exception as long as 
	{																			// OrderManager() & manage() are called in a try/catch block.
		/// System.out.println( "in main" );
		// Start the program
		OrderManager orderManager = new OrderManager();							// create an OrderManager object

		/*	I/O exception handling: 
			readPreferredCustomers(), readCustomers(), readOrders(), & writeOutputToFiles() all throw to the driver method manage().
			The driver method also throws to where it is called, ie. here in main().
			So in main(), a try/catch block is used to handle any I/O exception.
			Without these blocks, uncaught exceptions propagate to the JVM, which defaults to printing the call stack and terminating the program. */
		try
		{
			orderManager.manage();												// call driver method
		}
		catch(IOException fileException)
		{
        	System.out.println("File error: " + fileException.getMessage());	// print any file error for front-facing user to see
			/// fileException.printStackTrace();								// un-comment for debugging info for back-end developer use
		}
	}

}	// end of class Main
