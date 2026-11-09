/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	OrderManager.java:		This source file defines the OrderManager class, which includes ... function definitions. */

package DrinkRewards;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;



// Beginning of class definition of OrderManager
public class OrderManager
{
	// member constants
	// names of input files to be read in
	private static final String CUSTOMER_FILE_NAME = "src/customer.dat";
	private static final String PREFERRED_CUSTOMER_FILE_NAME = "src/preferred.dat";
	private static final String ORDER_FILE_NAME = "src/orders.dat";
	// names of output files to be written to
	private static final String CUSTOMER_OUTPUT_FILE_NAME = "src/customer.dat";
	private static final String PREFERRED_CUSTOMER_OUTPUT_FILE_NAME = "src/preferred.dat";
	
	// member variables
	private int numOrdinaryCustomers;
	private int numPreferredCustomers;
	private int numOrders;
	// member arrays
	private Customer[] customers;
	private Customer[] preferredCustomers;
	private Order[] orders;



	/*	Function Type:		constructor
		Descr:				This constructor initializes class members.
		Param:    			None.
		Return:				None, initializes an OrderManager object. */
	public OrderManager()
	{
		// initialize members
		numOrdinaryCustomers = 0;
		numPreferredCustomers = 0;
		numOrders = 0;
	}



	/*	Function 2:			manage()
		Descr:				This function concatenates the customer information given by 4 private members into a string.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public void manage() throws java.io.IOException
	{

		final boolean DEBUG = false;
		
		read_preferred_customers();
		if(DEBUG)
		{
			System.out.println("numPreferredCustomers = " + numPreferredCustomers);

			for(int i = 0; i < numPreferredCustomers; i++)
			{
				System.out.println(preferredCustomers[i].toDebugString());
			}
		}
		

		read_customers();
		if(numOrdinaryCustomers == 0)
		{
			System.exit(1); // No customers read from file /// Spec not clear on this
		}
		if(DEBUG)
		{
			System.out.println("numOrdinaryCustomers = " + numOrdinaryCustomers + " customers.length = " + customers.length);
			for(int i = 0; i < numOrdinaryCustomers; i++)
			{
				System.out.println(customers[i].toDebugString());
			}
		}

		
		read_orders();
		if(numOrders == 0)
		{
			if(DEBUG) System.out.println( "ERROR: No order found in file " + ORDER_FILE_NAME ); 
			System.exit(1);
		}

		if(DEBUG)
		{
			System.out.println("numOrders = " + numOrders + " orders.length = " + orders.length);
			for(int i = 0; i < numOrders; i++)
			{
				System.out.println(orders[i].toString());
			}
		}

			
		if(DEBUG)
		{
			System.out.println("numOrdinaryCustomers = " + numOrdinaryCustomers
				+ "  numPreferredCustomers = " + numPreferredCustomers
				+ "  numOrders = " + numOrders);
		}
		
		processOrders();
		
		/// write output to files
		if(numPreferredCustomers > 0)
		{
			writeOutputToFiles( preferredCustomers, PREFERRED_CUSTOMER_OUTPUT_FILE_NAME);
		}
		writeOutputToFiles( customers, CUSTOMER_OUTPUT_FILE_NAME);
		
	} // end of method go()

	
 	private void read_customers()  throws java.io.IOException {

		final boolean DEBUG = false;;

		File file = new File(CUSTOMER_FILE_NAME);
		if(!file.exists())
		{
			System.out.println( "ERROR: File " + CUSTOMER_FILE_NAME   
					+ " does not exist" );
			System.exit(1);
		}
		
		Scanner scanner = new Scanner( file );
		
		int customerIndex = -1;
		int lineNum = 0; // (NOT index): line being read from the file
		String strLine = ""; // a line read from the input file 

		// A token is a maximal sequence of consecutive characters that 
		// are not delimiters
		String[] tokens = {"", "", "", ""}; // each string in strLine
		StringTokenizer stringTokenizer;
		int tokenCount = 0;
		float amount_spent = 0.0F;
		Customer customer;

		while (scanner.hasNextLine()) {
			strLine = scanner.nextLine().trim();
			lineNum++;
			if(strLine.length() == 0)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + lineNum + " has no data items.");
				continue;
			}
			stringTokenizer = new StringTokenizer( strLine, " \t\n\r" );
			tokenCount = 0;
			tokens[0] = "";
			tokens[1] = "";
			tokens[2] = "";
			tokens[3] = "";
			amount_spent = 0.0F;
			
			while(stringTokenizer.hasMoreTokens())
			{
				tokenCount++;
				if(tokenCount > 4)
				{
					break;
				}
				tokens[tokenCount - 1] = stringTokenizer.nextToken();
			}

			if(tokenCount < 4)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + lineNum + " has too few data items.");
			} else if(tokenCount > 4)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + lineNum + " has too many data items.");
			} else
			{
				customerIndex++;
				amount_spent = Float.parseFloat(tokens[3]);
				customer = new Customer(tokens[0], tokens[1], tokens[2], amount_spent);

				if(customerIndex == 0)
				{
					customers = new Customer[0];
				}
				customers = addToArray(customers, customer);
				
				if(DEBUG)
					System.out.println("Line # " + lineNum + " Customer index: " + customerIndex + "   " + customers[customerIndex]);
			}

		}
		
		numOrdinaryCustomers = customerIndex + 1;
		scanner.close();

		if(DEBUG)
		{
			System.out.println("numOrdinaryCustomers = " + numOrdinaryCustomers + ". Closing file." );
		}
		
	} // end of method read_customers()

 	
	private void read_preferred_customers()  throws java.io.IOException {

		final boolean DEBUG = false;

		File file = new File(PREFERRED_CUSTOMER_FILE_NAME);
		if(!file.exists())
		{
			if(DEBUG) System.out.println( "File " + PREFERRED_CUSTOMER_FILE_NAME + " does not exist" );
			return;
		}
		
		Scanner scanner = new Scanner( file );
		
		int customerIndex = -1;
		int lineIndex = -1; // to identify the line# in file being read
		String strLine = ""; // a line read from the input file 
		String[] tokens = {"", "", "", "", ""}; // each string in strLine
		StringTokenizer stringTokenizer;
		int tokenCount = 0;
		float amount_spent = 0.0F;
		float discount_percent = 0.0F;
		float bonus_bucks = 0.0F;
		String pct = "";

		Customer customer;

		while (scanner.hasNextLine()) {
			strLine = scanner.nextLine().trim();
			lineIndex++;
			if(strLine.length() == 0)
			{
				System.out.println( "Warning: Line #" + (lineIndex+1) + " has no data items.");
				continue;
			}
			stringTokenizer = new StringTokenizer( strLine, " \t\n\r" );
			tokenCount = 0;
			tokens[0] = "";
			tokens[1] = "";
			tokens[2] = "";
			tokens[3] = "";
			tokens[4] = "";
			amount_spent = 0.0F;
			discount_percent = 0.0F;
			bonus_bucks = 0.0F;
			
			while(stringTokenizer.hasMoreTokens())
			{
				tokenCount++;
				if(tokenCount > 5)
				{
					break;
				}
				tokens[tokenCount - 1] = stringTokenizer.nextToken();
			}

			if(tokenCount < 5)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + (lineIndex+1) + " has too few data items.");
			} else if(tokenCount > 5)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + (lineIndex+1) + " has too many data items.");
			} else
			{
				customerIndex++;
				amount_spent = Float.parseFloat(tokens[3]);
				if(tokens[4].endsWith("%"))
				{
					pct = tokens[4].substring(0, tokens[4].length()-1);
					if(DEBUG) System.out.println( "pct=[" + pct + "]");
					discount_percent = Float.parseFloat(pct);
					customer = new GoldCustomer(tokens[0], tokens[1], tokens[2], amount_spent, discount_percent);
				} else
				{
					bonus_bucks = Float.parseFloat( tokens[4] );
					customer = new PlatinumCustomer(tokens[0], tokens[1], tokens[2], amount_spent, bonus_bucks);
				}

				if(customerIndex == 0)
				{
					preferredCustomers = new Customer[0];
				}
				preferredCustomers = addToArray(preferredCustomers, customer);

				if(DEBUG)
					System.out.println("Line: " + lineIndex + " Customer # " + customerIndex + "   " + preferredCustomers[customerIndex]);
			}

		}
		
		numPreferredCustomers = customerIndex + 1;
		scanner.close();
		if(DEBUG)
		{
			System.out.println("numPreferredCustomers = " + numPreferredCustomers + ". Closing file." );
		}
		///return customers;
	} // end of method read_preferred_customers()


	private void read_orders()  throws java.io.IOException {

		boolean DEBUG = false;
		File file = new File(ORDER_FILE_NAME );
		if(!file.exists())
		{
			if(DEBUG) System.out.println( "ERROR: File " + ORDER_FILE_NAME + " does not exist" );
			System.exit(1);
		}
		
		Scanner scanner = new Scanner( file );
		
		int orderIndex = -1;
		int lineNum = 0; // the line being read from the file
		String strLine = ""; // a line read from the input file 
		String[] tokens = {"", "", "", "", ""}; // each string in strLine
		StringTokenizer stringTokenizer;
		int tokenCount = 0;
		char size_letter = '*';
		float price_per_square_inch = 0.0F;
		int num_bottles_ordered = 0;
		
		while (scanner.hasNextLine()) {
			strLine = scanner.nextLine().trim();
			lineNum++;
			if(strLine.length() == 0)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + lineNum + " has no data items.");
				continue; // skip remainder of this iteration
			}
			stringTokenizer = new StringTokenizer( strLine, " \t\n\r" );
			tokenCount = 0;
			tokens[0] = "";
			tokens[1] = "";
			tokens[2] = "";
			tokens[3] = "";
			tokens[4] = "";
			size_letter = '*';
			price_per_square_inch = 0.0F;
			num_bottles_ordered = 0;
			
			while(stringTokenizer.hasMoreTokens())
			{
				tokenCount++;
				if(tokenCount > 5)
				{
					break;
				}
				tokens[tokenCount - 1] = stringTokenizer.nextToken();
			}

			if(tokenCount < 5)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + lineNum + " has too few data items.");
			} else if(tokenCount > 5)
			{
				if(DEBUG) System.out.println( "Warning: Line #" + lineNum + " has too many data items.");
			} else
			{
				// check if customer id is valid:
				if( customerIndex(tokens[0], numOrdinaryCustomers, customers)
						< 0 && customerIndex(tokens[0], numPreferredCustomers, 
						preferredCustomers) < 0 )
				{
					if(DEBUG) System.out.println( "Warning: Customer ID " + tokens[0] + " not found - skip line");
					continue;
				} 
					

				// validate price value
				if(! isOkayPriceValue(tokens[3]))
				{
					if(DEBUG) System.out.println( "Warning: Price value has problem " + tokens[3] + " - skip line");
					continue;
				}

				// validate # of bottles value
				if(! isUnsignedPositiveInteger(tokens[4]))
				{
					if(DEBUG) System.out.println( "Warning: Number of bottles value has problem " + tokens[4] + " - skip line");
					continue;
				}

				price_per_square_inch = Float.parseFloat(tokens[3]);
				num_bottles_ordered = Integer.parseInt(tokens[4]);

				if(tokens[1].equals("S") || tokens[1].equals("M")
					|| tokens[1].equals("L") )
				{
					size_letter = tokens[1].charAt(0);
				} else
				{
					if(DEBUG) System.out.println( "invalid size letter " + tokens[1] + " - skip line");
					continue;
				}

				// validate drink type
				if(tokens[2].equals("soda") || tokens[2].equals("tea")
						|| tokens[2].equals("punch") )
					{
						// Ok
					} else
					{
						if(DEBUG) System.out.println( "invalid drink type " + tokens[2] + " - skip line");
						continue;
					}

				Order order = new Order(tokens[0], num_bottles_ordered, 
						tokens[2], size_letter, price_per_square_inch);

				orderIndex++;
				if(DEBUG) System.out.println("Line No. " + lineNum + " Order index " + orderIndex + "   " + order);

				if(orderIndex == 0)
				{
					orders = new Order[0];
				}
				orders = addToArray(orders, order);
				
				if(DEBUG) System.out.println("Line No. " + lineNum + " Order index " + orderIndex + "   " + orders[orderIndex]);
				///System.out.println("Line: " + lineIndex + " Customer # " + customerIndex + "   " + customers[customerIndex]);
			}

		}
		
		numOrders = orderIndex + 1;
		scanner.close();
		
	} // end of method read_orders()


// Get index of the array element that matches the supplied customerID
	public int customerIndex(String customerID, int numCustomers, Customer[] customers)
	{
		final boolean DEBUG = false; 

		int i = 0;
		for(i = 0; i < numCustomers; i++)
		{
			if( customerID.equals( customers[i].getGuestID() ) )
			{
				if(DEBUG) System.out.println("***  i = " + i + "   customerID: " + customerID + "  customers[i].getGuestID(): " + customers[i].getGuestID());
				return i;
			}
		}
		
		// if here then match not found
		return -1;
	}


	private void processOrders() {
		boolean DEBUG = false;
		boolean DEBUG2 = false;

		String customer_id = "";
		int index = -1;
		Customer customer;
		Customer c2;
		float order_total_price = 0;
		
		for( int i = 0; i < numOrders; i++ )
		{
			customer_id = "";
			index = -1;
			customer = null;
			order_total_price = orders[i].getTotalPrice();

			customer_id = orders[i].getCustomerID();
			if(DEBUG) System.out.print("i = " + i + " " + customer_id );
			index = customerIndex(customer_id, 
					numOrdinaryCustomers, customers);
			
			if(index >= 0)
			{
				// customer was found in the customers array
				customer = customers[index];
				c2 = customer.processOrder(order_total_price);
				if(DEBUG2) System.out.println("\nafter process, customer: " + customer.toDebugString());
				if(DEBUG2) System.out.println("after process, c2: " + c2.toDebugString() + "\n");

				// if(cc.isProcessedLastOrder() == false && [cc.isPromoted() == true] && c2.isInitialized() == true) : order processed by c2 - after this c2 should be used, not cc. Condition checked within [] is redundant 
				if(customer.isProcessedLastOrder() == false && customer.isPromoted() == true)
				{
					if(c2.isInitialized() == false)
					{
						System.out.println("***** AN ERROR HAS OCCURED. Calling Customer was promoted but the returned customer is not initialized (should be Gold or Platinum Customer). *****");
					} else
					{
						// remove customer from the array and add c2 to the preferred array
						customers = removeFromArray(customers, index);
						numOrdinaryCustomers--;
						if(DEBUG)
						{
							System.out.println("\nnumOrdinaryCustomers = " + numOrdinaryCustomers + " customers.length = " + customers.length);
							for(int ii = 0; ii < numOrdinaryCustomers; ii++)
							{
								System.out.println(customers[ii].toDebugString());
							}
						}
						
						if(numPreferredCustomers == 0)
						{
							preferredCustomers = new Customer[0]; // create array
						}
						preferredCustomers = addToArray(preferredCustomers, c2);
						numPreferredCustomers++;

						if(DEBUG)
						{
							System.out.println("\nnumPreferredCustomers = " + numPreferredCustomers + " preferredCustomers.length = " + preferredCustomers.length);
							for(int ii = 0; ii < numPreferredCustomers; ii++)
							{
								System.out.println(preferredCustomers[ii].toDebugString());
							}
						}
					}
				} // done checking for promoted customer

			} else // now check the preferred customers array
			{
				index = customerIndex(customer_id, 
					numPreferredCustomers, preferredCustomers);

				// customer was found in the preferred customers array
				customer = preferredCustomers[index];
				c2 = customer.processOrder(order_total_price);
				if(DEBUG2) System.out.println("\nafter process, customer: " + customer.toDebugString());
				if(DEBUG2) System.out.println("after process, c2: " + c2.toDebugString() + "\n");
				
				if(customer.isProcessedLastOrder() == false && customer.isPromoted() == true)
				{
					if(c2.isInitialized() == false)
					{
						System.out.println("***** AN ERROR HAS OCCURED. Calling Customer was promoted but the returned customer is not initialized (should be a Platinum Customer). *****");
					} else
					{
						// replace the old customer with the promoted c2
						preferredCustomers[index] = c2;
						if(DEBUG)
						{
							System.out.println("\nnumPreferredCustomers = " + numPreferredCustomers + " preferredCustomers.length = " + preferredCustomers.length);
							for(int ii = 0; ii < numPreferredCustomers; ii++)
							{
								System.out.println(preferredCustomers[ii].toDebugString());
							}
						}
					}
				} // done checking for promoted customer
			} // done checking in p.customers array
			
			///total_price = orders[i].getTotalPrice();
			if(DEBUG) System.out.println("  index = " + index + " " + customer.getGuestID() + " " + order_total_price);

		} // end of for loop iteration for current order
	}
	


 	// remove the element at position identified by index. Shift subsequent elements to the left
 	private Customer[] removeFromArray(Customer[] customers, int index) {

 		boolean DEBUG = false;

 		int oldArrayLength = customers.length;
 		int newArrayLength = oldArrayLength - 1;

		Customer[] temp = new Customer[oldArrayLength];
		// copy the previous customer elements into the temp array 
		for(int i = 0; i < oldArrayLength; i++)
		{
			temp[i] = customers[i];
			if(DEBUG) System.out.println("i = " + i + "  " + customers[i].toDebugString());
		}

		// create a new customers array with its size 1 more than previous size
		customers = new Customer[newArrayLength];

		// copy the elements from temp into customers array 
		for(int i = 0; i < newArrayLength; i++)
		{
			if(i < index)
			{
				customers[i] = temp[i];
			} else
			{
				customers[i] = temp[i+1];
			}
			if(DEBUG) System.out.println("*i = " + i + "  " + customers[i].toDebugString());
		}
		
		return customers;

 	} // end of removeFromArray()

 	private void writeOutputToFiles(Customer[] outputCustomers, 
 			String strOutputFileName) throws java.io.IOException {
 		
		File file = new File(strOutputFileName);
		if(file.exists())
		{
			// Generally warn if it is okay to overwrite existing file 
		}
		
		PrintWriter pw = new PrintWriter( file );
		int arrayLength = outputCustomers.length;
		
		if(arrayLength > 0)
		{
			for(int i = 0; i < arrayLength-1; i++)
			{
				pw.println(outputCustomers[i].toOutputString());
			}

			// no newline character at the very last line in the file
			pw.print(outputCustomers[arrayLength - 1].toOutputString());
		}
		
		pw.close();
 	}
 	
 	private Customer[] addToArray(Customer[] customers, Customer customer) {

 		boolean DEBUG = false;

 		int oldArrayLength = customers.length;
 		int newArrayLength = oldArrayLength + 1;
 		int indexForTheNewElement = newArrayLength - 1;

		if(newArrayLength == 1)
		{
			customers = new Customer[1];
			customers[0] = customer;
			if(DEBUG) System.out.println("0:  " + customers[0].toDebugString());
		} else
		{
			Customer[] temp = new Customer[newArrayLength];
			// store the newest customer at the last position of temp array 
			temp[indexForTheNewElement] = customer;

			// copy the previous customer elements into the temp array 
			for(int i = 0; i < oldArrayLength; i++)
			{
				temp[i] = customers[i];
				if(DEBUG) System.out.println("i = " + i + "  " + customers[i].toDebugString());
			}

			// create a new customers array with its size 1 more than previous size
			customers = new Customer[newArrayLength];

			// copy the elements from temp into customers array 
			for(int i = 0; i < newArrayLength; i++)
			{
				customers[i] = temp[i];
				if(DEBUG) System.out.println("*i = " + i + "  " + customers[i].toDebugString());
			}
		}
		
		return customers;

 	} // end of addToArray()

 	

 	private Order[] addToArray(Order[] orders, Order order) {

 		boolean DEBUG = false;

 		int oldArrayLength = orders.length;
 		int newArrayLength = oldArrayLength + 1;
 		int indexForTheNewElement = newArrayLength - 1;

		if(newArrayLength == 1)
		{
			orders = new Order[1];
			orders[0] = order;
			if(DEBUG) System.out.println("0:  " + orders[0].toString());
		} else
		{
			Order[] temp = new Order[newArrayLength];
			// store the newest element at the last position of temp array 
			temp[indexForTheNewElement] = order;

			// copy the previous array elements into the temp array 
			for(int i = 0; i < oldArrayLength; i++)
			{
				temp[i] = orders[i];
				if(DEBUG) System.out.println("i = " + i + "  " + orders[i].toString());
			}

			// create a new array with its size 1 more than previous size
			orders = new Order[newArrayLength];

			// copy the elements from temp array into the new array 
			for(int i = 0; i < newArrayLength; i++)
			{
				orders[i] = temp[i];
				if(DEBUG) System.out.println("*i = " + i + "  " + orders[i].toString());
			}
		}
		
		return orders;

 	} // end of addToArray()

	

	// For use with price. May have a . and + sign
	public static boolean isOkayPriceValue(String str) {

		boolean valid = true;
		char ch = '*';
		double d;

		try {
			d = Double.parseDouble(str);

			for(int i = 0; i < str.length(); i++)
			{
				ch = str.charAt(i);
				if(! Character.isDigit(ch)) 
				{
					if(ch != '+' && ch!='.')
					{
						valid = false;
						break;
					}
				} // end of if
			} // end of for

		} catch(Exception e)
		{
			valid = false;
		}

		return valid;
	}
	
	// "5" is valid but "5.", "5.0", "+5" are not
	public static boolean isUnsignedPositiveInteger(String str) {

		char ch = '*';
		int len = str.length();

		if(len < 1) 
		{
			return false;
		} else
		{
			for(int i = 0; i < len; i++)
			{
				ch = str.charAt(i);
				if(! Character.isDigit(ch))
				{
					return false;
				}
			}
		}
		
		// if here then none of the false scenarios occurred
		return true;
	}

	
	
	
} // end of class OrderManager
