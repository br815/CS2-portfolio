/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	Customer.java:			This source file defines the Customer class, which includes 24 function definitions. */

package DrinkRewards;

/*
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;
*/



// Beginning of class definition of Customer
public class Customer
{

	// class-specific member variables
	private String firstName;
	private String lastName;
	private String guestID;
	private float amountSpent;

	private boolean initialized;	// distinguishes between objects created by each of the overloaded constructors

	// package-wide member variables
	protected boolean promoted;
	protected boolean lastRequestedOrderAmountWasValid;
	protected boolean processedLastOrder;
	/// private float thresholdForPromotion;


	
	/*	Function Type:		constructor
		Descr:				This constructor initializes class members.
		Param:    			None.
		Return:				None, initializes a Customer object. */
	protected Customer()
	{
		// initialize class-specific members
		guestID = "";
		firstName = "";
		lastName = "";
		amountSpent = 0.0f;

		// initialize other members
		promoted = false;
		lastRequestedOrderAmountWasValid = false;
		/// thresholdForPromotion = 50.00F;
		initialized = false;
	}



	/*	Function Type:		constructor (overloaded)
		Descr:				This overloading of the constructor sets class members to the parameters supplied.
		Param:    			String guest_ID, String first_name, String last_name, float amount_spent
							Values for the members of a Customer object.
		Return:				None, initializes a Customer object. */
	public Customer(String guest_ID, String first_name, String last_name, float amount_spent)
	{
		// initialize class-specific members
		guestID = guest_ID;
		firstName = first_name;
		lastName = last_name;
		amountSpent = amount_spent;
		
		// initialize other members
		promoted = false;
		lastRequestedOrderAmountWasValid = false;
		processedLastOrder = false;
		/// thresholdForPromotion = 50.00F;
		initialized = true;
		initialized = true;
	}



	/*	Function 3:			toString()
		Descr:				This function concatenates the customer information given by 4 private members into a string.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toString()
	{
		return "Guest ID: " + guestID 
				+ " fn: " + firstName 
				+ " ln: " + lastName 
				+ " has spent: " + amountSpent;
	}



	/*	Function 4:			toDebugString()
		Descr:				This function concatenates further customer information into a string for debugging.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toDebugString()
	{
		return "Guest ID: " + guestID 
			+ " fn: " + firstName 
			+ " initialized: " + initialized 
			+ " processedLastOrder: " + processedLastOrder 
			+ " promoted: " + promoted 
			+ " has spent: " + amountSpent;
	}



	/*	Function 5:			toOutputString()
		Descr:				This function concatenates the customer information given by 4 private members into a string, 
							first converting the float for amountSpent for output.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toOutputString()
	{
		return guestID + " " 
			+ firstName + " " 
			+ lastName + " " 
			+ toMoneyString(amountSpent);
	}



	/*	Function 6:			toMoneyString()
		Descr:				This function is called by toOutputString().
							It returns strings like 40.00, 25.01, 55.37, etc.
							It is a static function, so it can be called without creating an object of this class.
		Param:    			float amount
							A float representing a money amount.
		Return:				String str_amount
							The string conversion of the money amount. */
	public static String toMoneyString(float amount)
	{

		int int_amount = (int) (Math.round(amount * 100.0));
		int dollars = int_amount / 100;
		int cents = int_amount - dollars*100;

		String str_amount;
		if(cents < 10 )
		{
			str_amount = dollars + ".0" + cents;
		} else
		{
			str_amount = dollars + "." + cents;
		}
		
		return str_amount;
	}



	/*	Functions 7-13:		getFirstName(), getLastName(), getGuestID(), getAmountSpent(), isPromoted(), isInitialized(), isProcessedLastOrder()
		Descr:				These 7 functions are accessors.
		Param:    			None for all.
		Return:				The corresponding members accessed by each accessor. */
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String getGuestID()
	{
		return guestID;
	}
	public float getAmountSpent()
	{
		return amountSpent;
	}
	public boolean isPromoted()
	{
		return promoted;
	}
	public boolean isInitialized()
	{
		return initialized;
	}
	public boolean isProcessedLastOrder()
	{
		return processedLastOrder;
	}



	/*	Functions 14-19:	setFirstName(), setLastName(), setGuestID(), setAmountSpent(), setPromoted(), setInitialized()
		Descr:				These 6 functions are mutators.
		Param:    			The corresponding members mutated by each mutator (except for setPromoted, which has no param).
		Return:				None for all. */
	public void setFirstName(String first_name)
	{
		this.firstName = first_name;
	}
	public void setLastName(String last_name)
	{
		this.lastName = last_name;
	}
	public void setGuestID(String guest_ID)
	{
		this.guestID = guest_ID;
	}
	protected void setAmountSpent(float amount_spent)
	{
		this.amountSpent = amount_spent;
	}
	protected void setPromoted()
	{
		promoted = true;
	}
	protected void setInitialized(boolean newStatus)
	{
		if(newStatus)
		{
			if(guestID.length() > 0)
			{
				initialized = true;
			}
			else
			{
				initialized = false;
			}
		}
		else
		{
			guestID = "";
			firstName = "";
			lastName = "";
			amountSpent = 0.0f;
			promoted = false;
			processedLastOrder = false;
			/// thresholdForPromotion = 50.00F;
			initialized = false;
			
		}
	}



/*	
	public void addToAmountSpent(float amount_to_be_added)
	{
		this.amountSpent += amount_to_be_added;
		// CHECK IF STATUS NEEDS TO BE UPDATED
	}
*/


	
	/*	Functions 20-21:	getsDiscountOrReward(), getSaleFactor()
		Descr:				These 2 functions are accessors that return static values.
		Param:    			None for all.
		Return:				The static values returned by each accessor. */
	public boolean getsDiscountOrReward()
	{
		return false;
	}
	public float getSaleFactor()
	{
		return 1.0F;
	}



	/*	Function 22:		isValidLastRequestedOrderAmount()
		Descr:				This function is an accessor like functions 7-13.
		Param:    			None.
		Return:				boolean lastRequestedOrderAmountWasValid
							The corresponding member accessed by this accessor. */
	public boolean isValidLastRequestedOrderAmount()
	{
		return lastRequestedOrderAmountWasValid;
	}



	/*	Function 23:		isValidLastRequestedOrderAmount() (overloaded)
		Descr:				This overloaded function first mutates its corresponding member according to a condition, 
							then returns it.
		Param:    			float order_total_price
							Amount ordered by the customer.
		Return:				boolean lastRequestedOrderAmountWasValid
							The corresponding member accessed by this function. */
	protected boolean isValidLastRequestedOrderAmount(float order_total_price)
	{
		int int_order_total_price = (int) (Math.round(order_total_price*1000.0f)); 
		
		if(int_order_total_price < 10)
		{
			lastRequestedOrderAmountWasValid = false; // order not valid because order is less than a penny
		}
		else
		{
			lastRequestedOrderAmountWasValid = true;
		}
		
		return lastRequestedOrderAmountWasValid;
	}



	/*	Function 24:		processOrder()
		Descr:				This function processes a customer's order in accordance with their customer status.
							`example  :   c2 = cc.processOrder(10.00f);`
							`if(cc.isProcessedLastOrder() == true && cc.isPromoted() == false) : order processed successfully`
							`if(cc.isProcessedLastOrder() == false && [cc.isPromoted() == true] && c2.isInitialized() == true) : order processed by c2 - after this c2 should be used, not cc. Condition checked within [] is redundant`
							`other cases of if(cc.isProcessedLastOrder() == false) : order process failed`
		Param:    			float order_total_price
							Amount ordered by the customer.
		Return:				Customer() or promotedCustomer
							An object of the Customer class or one of its subclasses. */
	public Customer processOrder(float order_total_price)
	{
		processedLastOrder = false;

		// if object was already promoted then order should not be processed as a base customer 
		if(promoted)
		{
			return new Customer();	// order not processed
			/// return this;		// order not processed
		}

		if(!isValidLastRequestedOrderAmount(order_total_price))
		{
			return new Customer();	// order not processed because order is less than a penny
			/// return this; 		// order not processed because order is less than a penny
		}

		Customer promotedCustomer = new Customer();

		float tentativeAmountSpent = amountSpent + order_total_price;
		int int_tentativeAmountSpent = (int) (Math.round(tentativeAmountSpent*1000.0f)); 
		
		if(int_tentativeAmountSpent < (GoldCustomer.GOLD_MIN_AMOUNT_SPENT_INT - 5) )
		{
			amountSpent = tentativeAmountSpent;
			processedLastOrder = true;
			return new Customer();
			/// `MAY BE return this; ?`
		}
		else 
		{
			promotedCustomer = GoldCustomer.promote(order_total_price, this);
			processedLastOrder = false;
			/// promoted = true;
		}

		return promotedCustomer;
	}

}	// end of class Customer
