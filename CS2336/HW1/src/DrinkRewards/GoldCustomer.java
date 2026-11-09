/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	GoldCustomer.java:		This source file defines the GoldCustomer subclass of Customer, which includes 11 function definitions. */

package DrinkRewards;



// Beginning of subclass definition of GoldCustomer
public class GoldCustomer extends Customer
{
	// package-wide member constants
	public static final float GOLD_MIN_AMOUNT_SPENT = 50.0f;
	public static final int GOLD_MIN_AMOUNT_SPENT_INT = 50_000;

	public static final float GOLD_MIN_AMOUNT_SPENT_LEVEL2 = 100.0f;
	public static final int GOLD_MIN_AMOUNT_SPENT_LEVEL2_INT = 100_000;
	
	public static final float GOLD_MIN_AMOUNT_SPENT_LEVEL3 = 150.0f;
	public static final int GOLD_MIN_AMOUNT_SPENT_LEVEL3_INT = 150_000;

	// subclass-specific member variables
	private float discountPercentage;
	/// private float saleFactor;



	/*	Function Type:		constructor
		Descr:				This constructor uses super() to invoke the constructor
							of this subclass's superclass, Customer().
		Param:    			None.
		Return:				None, initializes a GoldCustomer object. */
	GoldCustomer()
	{
		super();
	}



	/*	Function Type:		constructor (overloaded)
		Descr:				This overloading of the constructor uses super() to invoke the overloaded constructor
							of this subclass's superclass, Customer().
		Param:    			String guest_ID, String first_name, String last_name, float amount_spent, float discount_percentage
							Values for the members of a Customer object.
							All these params but 1 are supplied to the superclass's overloaded constructor,
							and the remaining 1, discount_percentage, is used to initialize a subclass-specific member variable.
		Return:				None, initializes a GoldCustomer object. */
	public GoldCustomer(String guest_ID, String first_name, String last_name, float amount_spent, float discount_percentage)
	{

		super(guest_ID, first_name, last_name, amount_spent);
		discountPercentage = discount_percentage;
	}



	/*	Function Type:		constructor (overloaded)
		Descr:				This overloading of the constructor uses super() to invoke the overloaded constructor
							of this subclass's superclass, Customer().
		Param:    			Customer fromCustomer
							A Customer object.
							It is supplied to this constructor so that the Customer's members can be accessed (using its accessors)
							to initialize a GoldCustomer object from the superclass's overloaded constructor.
		Return:				None, initializes a GoldCustomer object. */
	private GoldCustomer (Customer fromCustomer)
	{
		super(fromCustomer.getGuestID(), fromCustomer.getFirstName(), fromCustomer.getLastName(), fromCustomer.getAmountSpent());
		discountPercentage = 5.0f;
	}



	/*	Function 4:			toDebugString()
		Descr:				This function concatenates customer information
							given by its corresponding superclass method, along with discountPercentage,
							into a string for debugging.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toDebugString()
	{
		return super.toDebugString() 
			+ " discountPercentage: " + discountPercentage;
	}



	/*	Function 5:			toOutputString()
		Descr:				This function concatenates customer information
							given by its corresponding superclass method, along with strDiscount,
							first converting the float for discountPercentage to strDiscount for output.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toOutputString()
	{
		String strDiscount = ((int) (Math.round(discountPercentage))) + "%";
		return super.toOutputString() 
			+ " " + strDiscount;
	}



	/*	Functions 6-8:		getDiscountPercentage(), getsDiscountOrReward(), getSaleFactor()
		Descr:				These 3 functions are accessors.
		Param:    			None for all.
		Return:				The corresponding members accessed by each accessor. */
	public float getDiscountPercentage()
	{
		return discountPercentage;
	}
	public boolean getsDiscountOrReward()
	{
		return true;	// this accessor returns a static value
	}
	public float getSaleFactor()
	{
		float saleFactor = (100.0F - discountPercentage)/100.0F;
		boolean DEBUG = false;
		if(DEBUG) System.out.println("discountPercentage = " + discountPercentage + " saleFactor = " + saleFactor);
		return saleFactor;
	}



	/*	Function 9:			setDiscountPercentage()
		Descr:				This function is a mutator.
		Param:    			The corresponding member mutated by this mutator.
		Return:				None. */
	public void setDiscountPercentage(float discount_percentage)
	{
		discountPercentage = discount_percentage;
	}



	/*	Function 10:		promote()
		Descr:				This function promotes a base Customer object to GoldCustomer.
							`If c is qualified then promotes c from base customer level to gold level.`
							`goldCustomer is the new object created by this method.`
		Param:    			float order_total_price, Customer fromCustomer
							Amount ordered by the customer, and a base Customer object representing the customer.
		Return:				GoldCustomer promotedGoldCustomer
							An object of the GoldCustomer subclass. */
	protected static Customer promote(float order_total_price, Customer fromCustomer)
	{
		boolean DEBUG = false;

		boolean orderProcessed = false;
		Customer promotedGoldCustomer = new GoldCustomer(); // for return

		if(!fromCustomer.getsDiscountOrReward()) 
		{
			// c is a Customer class object only, so tentatively ok to promote

			float tentativeAmountSpent = fromCustomer.getAmountSpent() + order_total_price*fromCustomer.getSaleFactor();
			int int_tentativeAmountSpent = (int) (Math.round(tentativeAmountSpent*1000.0f)); // is it 49_995?

			if( int_tentativeAmountSpent >= (GOLD_MIN_AMOUNT_SPENT_INT - 5) )
			{
				// ok to promote - create object and process order at the same time
				int previous_amount_spent = Math.round(fromCustomer.getAmountSpent()*1000.0f);
				GoldCustomer goldCustomer = new GoldCustomer(fromCustomer);
				fromCustomer.setPromoted();
				fromCustomer.processedLastOrder = false;
				promotedGoldCustomer = goldCustomer.processOrder(order_total_price);
				if(! promotedGoldCustomer.isInitialized())
				{
					promotedGoldCustomer = goldCustomer;
				}

				int new_amount_spent = Math.round(goldCustomer.getAmountSpent()*1000.0f);
				if(new_amount_spent > previous_amount_spent)
				{
					orderProcessed = true;
				}

				if(DEBUG) System.out.println("{ Ok to promote Customer to GoldCustomer: " + goldCustomer.getFirstName().toUpperCase() + " GoldCustomer Amount Spent int: " + int_tentativeAmountSpent);
				if(DEBUG) System.out.println("previous_amount_spent: " + previous_amount_spent + " new_amount_spent: " + new_amount_spent + " orderProcessed: " + orderProcessed);
				if(DEBUG) System.out.println("fromCustomer.processedLastOrder = " + fromCustomer.processedLastOrder + " fromCustomer.isPromoted(): " + fromCustomer.isPromoted() );
				if(DEBUG) System.out.println("promotedGoldCustomer.processedLastOrder = " + promotedGoldCustomer.processedLastOrder + " promotedGoldCustomer.isPromoted(): " + promotedGoldCustomer.isPromoted() + "}" );
			}
			else 
			{
				if(DEBUG) System.out.println("** Cannot promote Customer to GoldCustomer** int_tentativeAmountSpent: " + int_tentativeAmountSpent);
			}
		}

		return promotedGoldCustomer;
	}



	/*	Function 11:		processOrder()
		Descr:				This function processes a customer's order in accordance with their customer status.
							`example  :   c2 = gc.processOrder(10.00f);`
							`if(gc.isProcessedLastOrder() == true && gc.isPromoted() == false) : order processed successfully`
							`if(gc.isProcessedLastOrder() == false && [gc.isPromoted() == true] && c2.isInitialized() == true) : order processed by c2 - after this c2 should be used, not cc. Condition checked within [] is redundant`
							`other cases of if(gc.isProcessedLastOrder() == false) : order process failed`
		Param:    			float order_total_price
							Amount ordered by the customer.
		Return:				GoldCustomer() or promotedCustomer
							An object of the GoldCustomer class or other Customer subclass:
							promotedCustomer = PlatinumCustomer subclass of Customer. */
	public Customer processOrder(float order_total_price)
	{

		processedLastOrder = false;

		// if object was already promoted then order should not be processed as a base customer 
		if(promoted)
		{
			/// return this; 			// order not processed
			return new GoldCustomer();	// order not processed
		}

		if(!isValidLastRequestedOrderAmount(order_total_price))
		{
			/// return this;			// order not processed because order is less than a penny
			return new GoldCustomer();	// order not processed because order is less than a penny
		}

		/// Customer promotedCustomer = new Customer();

		float tentativeAmountSpent = getAmountSpent() + order_total_price*getSaleFactor();
		int int_tentativeAmountSpent = (int) (Math.round(tentativeAmountSpent*1000.0f)); 
		
		if( int_tentativeAmountSpent < (GoldCustomer.GOLD_MIN_AMOUNT_SPENT_LEVEL2_INT - 5) )
		{
			setAmountSpent(tentativeAmountSpent);
			processedLastOrder = true;
			/// return this;
			return new GoldCustomer();	// return uninitialized object because "this" object is still valid
		}

		// eligible for at least Level 2 discounts (10%)
		discountPercentage = 10.0F;
		tentativeAmountSpent = getAmountSpent() + order_total_price*getSaleFactor();
		int_tentativeAmountSpent = (int) (Math.round(tentativeAmountSpent*1000.0f)); 

		if( int_tentativeAmountSpent < (GoldCustomer.GOLD_MIN_AMOUNT_SPENT_LEVEL3_INT - 5) )
		{
			setAmountSpent(tentativeAmountSpent);
			processedLastOrder = true;
			/// return this;
			return new GoldCustomer();	// return uninitialized object because "this" object is still valid
		}

		// eligible for at least Level 3 discounts (15%)
		discountPercentage = 15.0F;
		tentativeAmountSpent = getAmountSpent() + order_total_price*getSaleFactor();
		int_tentativeAmountSpent = (int) (Math.round(tentativeAmountSpent*1000.0f)); 

		if(int_tentativeAmountSpent < (PlatinumCustomer.PLATINUM_MIN_AMOUNT_SPENT_INT - 5))
		{
			setAmountSpent(tentativeAmountSpent);
			processedLastOrder = true;
			/// return this;
			return new GoldCustomer();	// return uninitialized object because "this" object is still valid
		} 

		Customer promotedCustomer = PlatinumCustomer.promote(this, order_total_price);
		setPromoted();

		return promotedCustomer;
	}
	
}	// end of class GoldCustomer
