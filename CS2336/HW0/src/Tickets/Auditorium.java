/*	Author:				Bushra Rahman
	Course:				CS 2336 (Spring 2020)
   	Date:				1/30/2020
	Auditorium.java:	This source file defines the Auditorium class, which includes 18 function definitions. */

package Tickets;

import java.io.*;
import java.util.Scanner;



// Beginning of class definition of Auditorium
public class Auditorium
{
	// member constants
	private final String seating_arrangement_file = "A1.txt";	// input file name: the input files provided are A1.txt or A2.txt
	private final int MAX_ROWS = 10;							// max # of rows
	private final int MAX_SEATS_PER_ROW = 26;					// max # of columns
	private final double TICKET_PRICE_ADULT = 10.00;			// adult ticket price
	private final double TICKET_PRICE_CHILD = 5.00;				// child ticket price
	private final double TICKET_PRICE_SENIOR = 7.50;			// senior ticket price

	// member variables
	private int numRows;				// # of rows in input file
	private int numSeatsPerRow;			// # of columns in input file
	private char[][] seats;				// array of characters from input file

	private int num_AdultTickets;		// # of adult tickets
	private int num_ChildTickets;		// # of child tickets
	private int num_SeniorTickets;		// # of senior tickets
	private int num_TotalTicketsSold;	// total number of tickets
	private double totalSaleAmount;		// total amount in profits


	
	/*	Function Type:		constructor
		Descr:				This function initializes row & column member variables & the array of seats.
							It throws java.io.IOException because it calls read_seating_arrangement_file() 
							and does not use try/catch to handle exceptions from read_seating_arrangement_file() here in the constructor.
		Param:    			None.
		Return:				None, initializes an Auditorium object. */
	public Auditorium() throws java.io.IOException
	{
		// set number rows and columns to invalid numbers
		numRows = -1;
		numSeatsPerRow = -1;

		// create array and initialize with '*' (an invalid char for the array)
		seats = new char[MAX_ROWS][MAX_SEATS_PER_ROW];
		for(int row = 0; row < MAX_ROWS; row++)
		{
			for(int col = 0; col < MAX_SEATS_PER_ROW; col++)
			{
				seats[row][col] = '*';
			}
		}

		// read from input file, store data in array, initialize variables
		read_seating_arrangement_file();
	}



	/*	Function 2:			read_seating_arrangement_file()
		Descr:				This function is called by the constructor.
							It reads the input file & calculates variables at the end of the read.
							It throws java.io.IOException because it attempts input file handling 
							and does not use try/catch to handle exceptions from input file handling here in this function.
		Param:    			None.
		Return:				None. */
	private void read_seating_arrangement_file() throws java.io.IOException
	{
		// create input file object
		File file = new File(seating_arrangement_file);
		Scanner in = new Scanner(file);

		// initialize variables
		int numSeatsInCurrentRow = 0, numSeatsInFirstRow = 0;
		int rowIndex = -1;
		String str = "";	// each line in the input file
		int seatIndex = 0;
		char seatStatus = '*';

		// read from input file
		while(in.hasNext())
		{
			str = in.next();						// ignores line with only white spaces - GOOD
			numSeatsInCurrentRow = str.length();	// length of first line of text
			rowIndex++;

			// Check for inconsistent # of rows and/or seats - START
			if(rowIndex == 0)	// for first line of input file only
			{
				numSeatsInFirstRow = numSeatsInCurrentRow;
				if(numSeatsInCurrentRow > MAX_SEATS_PER_ROW)	// more seats than max allowed
				{
					System.out.println("INPUT FILE ERROR: seats in the first row = " + numSeatsInCurrentRow + "   max allowed = " + MAX_SEATS_PER_ROW);
					System.exit(1);
				}
			}
			else if(rowIndex == MAX_ROWS)	// rowIndex's range is only 0-9, and should never = 10
			{
				System.out.println("INPUT FILE ERROR: Reading row # " + (rowIndex + 1) + "   max allowed = " + MAX_ROWS);
				System.exit(1);
			}
			else
			{
				if(numSeatsInFirstRow != numSeatsInCurrentRow)	// if any row has more seats than it should
				{
					System.out.println("INPUT FILE ERROR: Seats in First Row = " + numSeatsInFirstRow + "    but Seats in Current Row = " + numSeatsInCurrentRow);
					System.exit(1);
				}
			}
			// DONE - Check for inconsistent # of rows and/or seats

			
			// save seat status for the current row and count seats
			for(seatIndex = 0; seatIndex < numSeatsInCurrentRow; seatIndex++)
			{
				// seatStatus is the char at each position in the input file; either 'A' or 'C' or 'S' or '.'
				seatStatus = str.charAt(seatIndex);
				
				// if any seats are reserved, they count towards the total ticket sales of the auditorium.
				if(seatStatus == 'A')
				{
					num_AdultTickets++;
				}
				else if(seatStatus == 'C')
				{
					num_ChildTickets++;
				}
				else if(seatStatus == 'S')
				{
					num_SeniorTickets++;
				}
				else if(seatStatus != '.')	// any other char is invalid
				{
					System.out.println("ERROR. Row # " + (rowIndex+1) + ", Seat # " + (seatIndex+1) + " : invalid seat type is: " + seatStatus);
					System.exit(1);
				}
				seats[rowIndex][seatIndex] = seatStatus;
			}
		} // end of while loop

		// close input file
		in.close();

		// check if input file was empty
		if(rowIndex < 0)	// rowIndex never increased from -1
		{
			System.out.println("INPUT FILE ERROR: Number of Rows = " + (rowIndex + 1));
			System.exit(1);
		}

		// if this point is reached, then valid data was found & read from the input file.
		// initialize numRows and numSeatsPerRow
		numRows = rowIndex + 1;
		numSeatsPerRow = numSeatsInFirstRow;
		// calculate total profits
		totalSaleAmount = num_AdultTickets * TICKET_PRICE_ADULT + num_ChildTickets * TICKET_PRICE_CHILD + num_SeniorTickets * TICKET_PRICE_SENIOR;
		// calculate total tickets sold in a single purchase
		num_TotalTicketsSold = num_AdultTickets + num_ChildTickets + num_SeniorTickets;
	}


	
	/*	Function 3:			displayMainMenu()
		Descr:				This function is the main menu & driver method of this class.
							It throws java.io.IOException because it calls writeToFile() 
							and does not use try/catch to handle exceptions from writeToFile() here in the driver method.
		Param:    			None.
		Return:				None. */
	public void displayMainMenu() throws java.io.IOException
	{
		// create Scanner object
		Scanner scanner = new Scanner(System.in);

		// determine if there are still open seats
		boolean okayToLoop = isSeatsAvailable();
		if(okayToLoop)
		{
			int optionSelected = 0;
			do {											// display the main menu at least once
				System.out.println("1. Reserve Seats");
				System.out.println("2. Exit");

				optionSelected = scanner.nextInt();

				if(optionSelected == 1)					// "Reserve Seats" option is chosen
				{
					promptReservationMenu(scanner);			// show reservation menu and pass scanner to the function
					okayToLoop = isSeatsAvailable();		// check if there are still open seats
				}
			} while(optionSelected != 2 && okayToLoop);
		}

		// End of if loop - write everything to file, show on-screen report
		displayReport();
		if(!okayToLoop)
		{	// all seats have been sold
			System.out.println("STOP: No seats available for sale.");
		}
		// print the final state of the array back to the file
		writeToFile();
	}



	/*	Function 4:			isSeatsAvailable()
		Descr:				This function is called by displayMainMenu().
							It checks if Auditorium still has any seats available.
		Param:    			None.
		Return:				true OR false
							Boolean determining whether all seats are not yet sold. */
	private boolean isSeatsAvailable()
	{
		// calculate total seats in auditorium
		int num_total_seats_in_Auditorium = numRows*numSeatsPerRow;
		if(num_TotalTicketsSold < num_total_seats_in_Auditorium)
		{	// if all seats are not yet sold
			return true;
		}
		else
		{	// if all seats are sold
			return false;
		}
	}



	/*	Function 5:			displayReport()
		Descr:				This function is called by displayMainMenu().
							It prints the final report to the screen.
		Param:    			None.
		Return:				None. */
	private void displayReport() 
	{
		System.out.printf("%-29s%3d\n", "Total Seats in Auditorium:", (numRows*numSeatsPerRow));

		System.out.printf("%-29s%3d\n", "Total Tickets Sold:", num_TotalTicketsSold);
		System.out.printf("%-29s%3d\n", "Adult Tickets Sold:", num_AdultTickets);
		System.out.printf("%-29s%3d\n", "Child Tickets Sold:", num_ChildTickets);
		System.out.printf("%-29s%3d\n", "Senior Tickets Sold:", num_SeniorTickets);

		System.out.printf("%-27s%1s%7.2f\n", "Total Tickets Sale:", "$", totalSaleAmount);
	}



	/*	Function 6:			promptReservationMenu()
		Descr:				This function is called by displayMainMenu().
							It shows available seats, asks for desired seats, makes reservation, etc.
		Param:    			Scanner scanner
							Scanner object to get user input.
		Return:				None. */
	private void promptReservationMenu(Scanner scanner)
	{
		// displays contents of input file to screen
		displaySeatsOnScreen(scanner);
		int rowIndex = promptRowNumberMenu(scanner) - 1;			// index of user's desired row
		char startingSeatLetter = promptSeatLetterMenu(scanner);	// letter of user's desired starting seat
		int startingSeatIdNumber = (int)(startingSeatLetter);		// cast that letter to a number so it can be worked with

		// receive user's desired amounts of adult, child, and senior tickets
		int numAdultTicketsRequested = promptNumberOfTicketsMenu(scanner, 1);
		int numChildTicketsRequested = promptNumberOfTicketsMenu(scanner, 2);
		int numSeniorTicketsRequested = promptNumberOfTicketsMenu(scanner, 3);

		int numTotalTicketsRequested = numAdultTicketsRequested + numChildTicketsRequested + numSeniorTicketsRequested;
		if(numTotalTicketsRequested == 0)
		{	// if user chose to buy 0 of all three types of tickets
			System.out.println("WARNING: Total # of tickets requested = 0");
			return;
		}

		if(seatSelectionExtendsPastEndOfRow (startingSeatIdNumber, numTotalTicketsRequested) == true)
		{	// check that the desired amount of seats fits in the desired row
			System.out.println("WARNING: Seat Selection Extends Past End of Row");
			return;
		}

		boolean okayToProcessOrder = false;
		if(seatsAreOpen(rowIndex, startingSeatIdNumber, numTotalTicketsRequested) == true)
		{	// seats are open, so the program can proceed to reserve the seats
			okayToProcessOrder = true;
			/// System.out.println("okayToProcessOrder is TRUE");
		} 
		else
		{
			// check for alternate seats
			int alt_SeatIdNumber = altSeatIdNumber(scanner, rowIndex, numTotalTicketsRequested);
			if(alt_SeatIdNumber > -1)  // alternate seats were found
			{
				// alternate seats are available, so the program can proceed to reserve the seats
				okayToProcessOrder = true;
				startingSeatIdNumber = alt_SeatIdNumber;
				/// System.out.println("startingSeat: " + (char)startingSeatIdNumber);
			}
		}
		/// System.out.println("**** okayToProcessOrder: " + okayToProcessOrder);

		// seats are available, either the desired or alternate seats
		if(okayToProcessOrder)
		{
			// order can be processed
			processOrder(rowIndex, startingSeatIdNumber, numAdultTicketsRequested, numChildTicketsRequested, numSeniorTicketsRequested);
		}
		/// System.out.println( "Seat Id:" + seatIdNumber );
		/// scanner.close();
	}



	/*	Function 7:			processOrder()
		Descr:				This function is called by promptReservationMenu().
							It fills seats and updates counters.
		Param:    			int rowIndex, int startingSeatIdNumber, int numAdultTicketsRequested, int numChildTicketsRequested, int numSeniorTicketsRequested
							User-inputted row number, starting seat letter, and number of each type of ticket requested.
		Return:				None. */
	private void processOrder(int rowIndex, int startingSeatIdNumber, int numAdultTicketsRequested, int numChildTicketsRequested, int numSeniorTicketsRequested)
	{
		int seatIndex = startingSeatIdNumber - 65;	// the actual numerical index of the seat
		while(seatIndex < (startingSeatIdNumber + numAdultTicketsRequested - 65))
		{	// fills the adult seats first
			seats[rowIndex][seatIndex] = 'A';
			seatIndex++;
		}

		while(seatIndex < (startingSeatIdNumber + numAdultTicketsRequested + numChildTicketsRequested -65))
		{	// fills the child seats next
			seats[rowIndex][seatIndex] = 'C';
			seatIndex++;
		}

		while(seatIndex < (startingSeatIdNumber + numAdultTicketsRequested + numChildTicketsRequested + numSeniorTicketsRequested -65))
		{  // fills the senior seats last
			seats[rowIndex][seatIndex] = 'S';
			seatIndex++;
		}
		
		// update member variables of this object
		num_TotalTicketsSold += numAdultTicketsRequested + numChildTicketsRequested + numSeniorTicketsRequested;
		num_AdultTickets += numAdultTicketsRequested;
		num_ChildTickets += numChildTicketsRequested;
		num_SeniorTickets += numSeniorTicketsRequested;
		totalSaleAmount += numAdultTicketsRequested * TICKET_PRICE_ADULT + numChildTicketsRequested * TICKET_PRICE_CHILD + numSeniorTicketsRequested * TICKET_PRICE_SENIOR;
		
		System.out.println("Your order has been processed.");
	}



	/*	Function 8:			altSeatIdNumber()
		Descr:				This function is called by promptReservationMenu().
							It determines the ID of the first seat in an alternate range of seats.
		Param:    			Scanner scanner, int rowIndex, int numTotalTicketsRequested
							Scanner object to get user input, user's originally-requested desired row & number of tickets.
		Return:				int altSeatIdNumber
							ID number of the alternate starting seat. */
	private int altSeatIdNumber(Scanner scanner, int rowIndex, int numTotalTicketsRequested)
	{
		int altSeatIdNumber = -1;

		// starting index of an available range of alternate seats to the
		// right of the row center
		int right = rightSeatIdNumber(rowIndex, numTotalTicketsRequested);
		/// System.out.println("right: " + right);

		// starting index of an available range of alternate seats to the
		// left of the row center
		int left = leftSeatIdNumber(rowIndex, numTotalTicketsRequested);
		/// System.out.println("left: " + left);

		if(right > -1 && left > -1)
		{	// if there are alternate seats on both sides of the row center
			double rowCenter = (65 + (65 + numSeatsPerRow - 1)) / 2.0;						// index of row center = average of the # of seats in a row
			double rightCenter = (right + (right + numTotalTicketsRequested - 1)) / 2.0;	// index of right-side selection's center
			double leftCenter = (left + (left + numTotalTicketsRequested - 1)) / 2.0;		// index of left-side selection's center
			double rightDistance = rightCenter - rowCenter;									// distance between right-side center and row center
			double leftDistance = rowCenter - leftCenter;									// distance between left-side center and row center 

			/// System.out.println("rightDistance: " + rightDistance);
			/// System.out.println("leftDistance: " + leftDistance);
			if(rightDistance <= leftDistance)
			{	// if right-side seats are closer to row center, they are offered
				if(acceptedAlternateSeatOffer(scanner, right))
				{
					altSeatIdNumber = right;
				}
			}
			else
			{	// if left-side seats are closer to row center, they are offered instead
				if(acceptedAlternateSeatOffer(scanner, left))
				{
					altSeatIdNumber = left;
				}
			}
		}
		else if(right > -1)
		{	// if left-side seats were unavailable but there were available seats of the right
			if(acceptedAlternateSeatOffer(scanner, right))
			{
				altSeatIdNumber = right;
			}
		}
		else if(left > -1)
		{	// if right-side seats were unavailable but there were available seats of the left
			if(acceptedAlternateSeatOffer(scanner, left))
			{
				altSeatIdNumber = left;
			}
		}

		if(right == -1 && left == -1)
		{	// neither left side nor right side has alternate seats
			System.out.println("Requested seat(s) not available. Best seat(s) also not available. Please try again.");
		}

		return altSeatIdNumber;
	}

		

	/*	Function 9:			acceptedAlternateSeatOffer()
		Descr:				This function is called by altSeatIdNumber().
							It prompts the user to accept or decline the next-best seats.
		Param:    			Scanner scanner, int seatIdNumber
							Scanner object to get user input, ID number of the alternate starting seat.
		Return:				boolean acceptedOffer
							Boolean determining whether the user accepted the alternate seating offer. */
	private boolean acceptedAlternateSeatOffer(Scanner scanner, int seatIdNumber)
	{
		char userResponse = '*';
		String str = "*";
		System.out.print("Requested seat(s) not available. Best available seat(s) " + "starts with " + (char) seatIdNumber + ".   Reserve the best available seat(s)? Y/N : ");

		str = scanner.next().toUpperCase();
		userResponse = str.charAt(0);

		// prompt user to accept or decline next-best seats
		while(str.length() > 1 || (userResponse != 'Y' && userResponse != 'N'))
		{
			System.out.print("Reserve the Best available seat(s)? Y/N : ");
			str = scanner.next().toUpperCase();
			userResponse = str.charAt(0);
		}

		// check if user has accepted
		boolean acceptedOffer = false;
		if(userResponse == 'Y')
		{
			acceptedOffer = true;
		}
		return acceptedOffer;
	}



	/*	Function 10:		leftSeatIdNumber()
		Descr:				This function is called by altSeatIdNumber().
							It determines if a range of alternate seats is available to the left of the row center.
		Param:    			int rowIndex, int numTicketsRequested
							User's originally-requested desired row & number of tickets.
		Return:				int leftSeatIdNumber
							ID number of the alternate starting seat closest from the left. */
	private int leftSeatIdNumber(int rowIndex, int numTicketsRequested)
	{
		int leftSeatIdNumber = -1;
		// We start at the left-side seat that is closest to the row center, and end at the leftmost index from the center, which is 0.
		int startingSeatIndexForTesting = numSeatsPerRow / 2 + 1 - numTicketsRequested;
		int endingSeatIndexForTesting = 0;
		/// System.out.println("startingSeatIndexForTesting:" + startingSeatIndexForTesting);
		/// System.out.println( "endingSeatIndexForTesting:" + endingSeatIndexForTesting);

		for(int seatIdNumber = 65 + startingSeatIndexForTesting; seatIdNumber >= (65 + endingSeatIndexForTesting); seatIdNumber--)
		{	// iterate right-to-left through the seats left of the row center
			/// System.out.println("** seatIdNumber:" + seatIdNumber);
			if(seatsAreOpen(rowIndex, seatIdNumber, numTicketsRequested))
			{	// if the user's desired amount of seats are available
				leftSeatIdNumber = seatIdNumber;
				break;
			}
		}
		return leftSeatIdNumber;
	}



	/*	Function 11:		rightSeatIdNumber()
		Descr:				This function is called by altSeatIdNumber().
							It determines if a range of alternate seats is available to the right of the row center.
		Param:    			int rowIndex, int numTicketsRequested
							User's originally-requested desired row & number of tickets.
		Return:				int rightSeatIdNumber
							ID number of the alternate starting seat closest from the right. */
	private int rightSeatIdNumber(int rowIndex, int numTicketsRequested)
	{
		int rightSeatIdNumber = -1;
		// We start at the right-side seat that is closest to the row center, and end at the rightmost index from the center, which is 
		// the last available starting position from which the user might get to buy their desired amount of seats.
		int startingSeatIndexForTesting = numSeatsPerRow / 2;
		int endingSeatIndexForTesting = numSeatsPerRow - numTicketsRequested;
		/// System.out.println("startingSeatIndexForTesting:" + startingSeatIndexForTesting);
		/// System.out.println("endingSeatIndexForTesting:" + endingSeatIndexForTesting);

		for(int seatIdNumber = 65 + startingSeatIndexForTesting; seatIdNumber <= (65 + endingSeatIndexForTesting); seatIdNumber++)
		{	// iterate left-to-right through the seats right of the row center
			/// System.out.println("** seatIdNumber:" + seatIdNumber);
			if(seatsAreOpen(rowIndex, seatIdNumber, numTicketsRequested))
			{	// if the user's desired amount of seats are available
				rightSeatIdNumber = seatIdNumber;
				break;
			}
		}
		return rightSeatIdNumber;
	}

	

	/*	Function 12:		seatsAreOpen()
		Descr:				This function is called by promptReservationMenu(), leftSeatIdNumber(), rightSeatIdNumber().
							It determines if all seats in a range of seats are open.
							startingSeatIdNumber will be within valid range when this method is called.
		Param:    			int rowIndex, int startingSeatIdNumber, int numTickets
							User's originally-requested desired row, starting seat, & number of tickets.
		Return:				boolean areOpen
							Boolean determining whether any '.' exists in a given range of seats. */
	private boolean seatsAreOpen(int rowIndex, int startingSeatIdNumber, int numTickets)
	{
		boolean areOpen = true;
		if(numTickets > 0)
		{
			int startingSeatIndex = startingSeatIdNumber - 65;
			for(int col = startingSeatIndex; col < (startingSeatIndex + numTickets); col++)
			{
				// If there's a '.' at any point in the desired range of seats,
				// then the seat selection as a whole can't be reserved.
				if(seats[rowIndex][col] != '.')
				{
					areOpen = false;
					break;
				}
			}
		}
		return areOpen;
	}



	/*	Function 13:		seatSelectionExtendsPastEndOfRow()
		Descr:				This function is called by promptReservationMenu().
							It determines if a range of seats is greater than the max seats in a row.
							startingSeatIdNumber will be within valid range when this method is called.
		Param:    			int startingSeatIdNumber, int numTicketsRequested
							User's originally-requested desired starting seat & number of tickets.
		Return:				boolean extendsPastEndOfRow
							Boolean determining whether the user's selected range of seats invalidly extends past the row end. */
	private boolean seatSelectionExtendsPastEndOfRow(int startingSeatIdNumber, int numTicketsRequested)
	{
		boolean extendsPastEndOfRow = false;
		if(numTicketsRequested > 0)
		{
			int endingSeatIdNumber = numTicketsRequested + startingSeatIdNumber - 1;
			int validEndingSeatIdNumber = 65 + numSeatsPerRow - 1;

			if(endingSeatIdNumber > validEndingSeatIdNumber)
			{	// if the last seat is a number past the last valid seat in a row
				extendsPastEndOfRow = true;
			}
		}
		return extendsPastEndOfRow;
	}



	/*	Function 14:		promptNumberOfTicketsMenu()
		Descr:				This function is called by promptReservationMenu().
							It prompts for the user's desired amounts of adult, child, and senior tickets.
							Type = 1 for Adult, 2 for Child, 3 for Senior.
		Param:    			Scanner scanner, int type
							Scanner object to get user input, ticket type between the range [1,3]
		Return:				int numTicketsRequested
							User-inputted number of tickets requested for the given type of the 3 types. */
	private int promptNumberOfTicketsMenu(Scanner scanner, int type)
	{
		int numTicketsRequested = 0;
		String str = "";
		if(type == 1)
		{
			str = "Adult tickets: ";
		}
		else if(type == 2)
		{
			str = "Child tickets: ";
		}
		else
		{
			str = "Senior tickets: ";
		}

		System.out.print("Enter number of " + str);
		numTicketsRequested = scanner.nextInt();

		while(numTicketsRequested < 0)
		{
			System.out.print("Enter a valid number of " + str);
			numTicketsRequested = scanner.nextInt();
		}
		return numTicketsRequested;
	}



	/*	Function 15:		promptSeatLetterMenu()
		Descr:				This function is called by promptReservationMenu().
							It prompts for the user's desired starting seat.
		Param:    			Scanner scanner
							Scanner object to get user input.
		Return:				char seatLetter
							User's choice of a valid letter indicating their starting seat in a row. */
	private char promptSeatLetterMenu(Scanner scanner)
	{
		int seatIdNumber = 0;
		char seatLetter = '*';
		String str = "*";
		System.out.print("Enter starting seat letter: ");

		str = scanner.next().toUpperCase();
		seatLetter = str.charAt(0);
		seatIdNumber = (int) (seatLetter);

		while(str.length() > 1 || seatIdNumber < 65 || seatIdNumber >= (65 + numSeatsPerRow))
		{
			System.out.print("Enter a valid seat letter: ");
			str = scanner.next().toUpperCase();
			seatLetter = str.charAt(0);
			seatIdNumber = (int) (seatLetter);
		}
		return seatLetter;
	}



	/*	Function 16:		promptRowNumberMenu()
		Descr:				This function is called by promptReservationMenu().
							It prompts for the user's desired row number.
		Param:    			Scanner scanner
							Scanner object to get user input.
		Return:				int rowNum
							User's choice of a valid row. */
	private int promptRowNumberMenu(Scanner scanner)
	{
		int rowNum = 0;
		System.out.print("Enter row number: ");
		rowNum = scanner.nextInt();
		
		while(rowNum < 1 || rowNum > this.numRows)
		{
			System.out.print("Enter a valid row number: ");
			rowNum = scanner.nextInt();
		}
		return rowNum;
	}



	/*	Function 17:		displaySeatsOnScreen()
		Descr:				This function is called by promptReservationMenu().
							It makes changes to the array read in from the input file to make it suitable for the user to view on-screen.
		Param:    			Scanner scanner
							Scanner object to get user input.
		Return:				None. */
	private void displaySeatsOnScreen(Scanner scanner)
	{
		// First line - print the seat letters
		char letter = '*';
		System.out.print("   ");
		for(int col = 0; col < numSeatsPerRow; col++)
		{	// prints out the header for the columns (of letters A-Z)
			letter = (char) (65 + col);
			System.out.print(letter);
		}
		// Insert a new line character at the end of this "header" row
		System.out.println();

		// Iterate through all of the rows and print each char
		for(int row = 0; row < numRows; row++)
		{
			System.out.printf("%2d ", (row + 1));	// print the row #
			for(int col = 0; col < numSeatsPerRow; col++)
			{
				letter = seats[row][col];
				if( letter == 'A' || letter == 'C' || letter == 'S' )
				{	// change all letters to '#'
					letter = '#';
				}
				System.out.print(letter);
			}
			// Insert a new line character at the end of this row
			System.out.println();
		}
	}



	/*	Function 18:		writeToFile()
		Descr:				This function is called by displayMainMenu().
							It is used to write the final state of the array back to file.
							It throws java.io.IOException because it attempts output file handling 
							and does not use try/catch to handle exceptions from output file handling here in this function.
		Param:    			None.
		Return:				None. */
	private void writeToFile() throws java.io.IOException
	{
		File file = new File(seating_arrangement_file);
		PrintWriter pout = new PrintWriter(file);
		// iterate through all of the rows
		for(int row = 0; row < numRows; row++)
		{
			for(int col = 0; col < numSeatsPerRow; col++)
			{
				// print the state of the array into the file
				pout.print(seats[row][col]);
			}
			// Insert a new line character at the end of this row
			pout.println();
		}
		pout.close();
	}

} // end of class Auditorium
