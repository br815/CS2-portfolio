/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	Main.java:				This source file is the main driver of the program. */

package DrinkRewards;

public class Main
{
	public static void main(String[] args) throws java.io.IOException
	{
		/// System.out.println( "in main" );

		// Start the program
		OrderManager orderManager = new OrderManager();
		orderManager.manage();		
	}

}	// end of class Main
