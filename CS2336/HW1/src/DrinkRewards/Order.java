/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	Order.java:				This source file defines the Order class, which includes 6 function definitions. */

package DrinkRewards;



// Beginning of class definition of Order
public class Order
{
	// class-specific member variables
	private String customerID;
	private Drink drink;
	private int numBottlesOrdered;
	private float totalPrice;	// as ordered price (not necessarily what will be charged)



	/*	Function Type:		constructor
		Descr:				This constructor sets class members to the parameters supplied.
		Param:    			String customer_ID, int num_bottles_ordered, Drink drink_ordered
							Values for the members of an Order object.
		Return:				None, initializes an Order object. */
	public Order(String customer_ID, int num_bottles_ordered, Drink drink_ordered)
	{
		customerID = customer_ID;
		drink = drink_ordered;
		numBottlesOrdered = num_bottles_ordered;
		totalPrice = numBottlesOrdered * drink.getTotalPrice();
	}



	/*	Function Type:		constructor (overloaded)
		Descr:				This overloading of the constructor uses the Drink constructor to set class members to the parameters supplied.
		Param:    			String customer_ID, int num_bottles_ordered, String drink_type_name, char drink_size_letter, float drink_price_per_square_inch
							Values for the members of an Order object.
							Some are members of a Drink object.
		Return:				None, initializes an Order object. */
	public Order(String customer_ID, int num_bottles_ordered, String drink_type_name, char drink_size_letter, float drink_price_per_square_inch)
	{
		customerID = customer_ID;
		drink = new Drink(drink_type_name, drink_size_letter, drink_price_per_square_inch);
		numBottlesOrdered = num_bottles_ordered;
		totalPrice = numBottlesOrdered * drink.getTotalPrice();
	}



	/*	Functions 3-5:		getCustomerID(), getTotalPrice(), getNumBottlesOrdered()
		Descr:				These 3 functions are accessors.
		Param:    			None for all.
		Return:				The corresponding members accessed by each accessor. */
	public String getCustomerID()
	{
		return customerID;
	}
	public float getTotalPrice()
	{
		return totalPrice;
	}
	public int getNumBottlesOrdered()
	{
		return numBottlesOrdered;
	}



	/*	Function 6:			toString()
		Descr:				This function concatenates drink information into a string.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toString()
	{
		return "Order for customer: " + customerID 
			+ " " + drink.getBriefString();
	}

}	// end of class Order
