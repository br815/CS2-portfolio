/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	PlatinumCustomer.java:	This source file defines the PlatinumCustomer subclass of Customer, which includes 11 function definitions. */

package DrinkRewards;



// Beginning of subclass definition of PlatinumCustomer
public class PlatinumCustomer extends Customer
{
	// package-wide member constants
	public static final float PLATINUM_MIN_AMOUNT_SPENT = 200.0f;
	public static final float PLATINUM_MIN_AMOUNT_SPENT_INT = 200_000;

	// subclass-specific member variables
	private float bonusBucks;
	private int calculateBonusBucksFrom;



	/*	Function Type:		constructor
		Descr:				This constructor uses super() to invoke the constructor
							of this subclass's superclass, Customer().
		Param:    			None.
		Return:				None, initializes a PlatinumCustomer object. */
	private PlatinumCustomer()
	{
		super();
	}



	/*	Function Type:		constructor (overloaded)
		Descr:				This overloading of the constructor uses super() to invoke the overloaded constructor
							of this subclass's superclass, Customer().
		Param:    			String guest_ID, String first_name, String last_name, float amount_spent, float bonus_bucks
							Values for the members of a Customer object.
							All these params but 1 are supplied to the superclass's overloaded constructor,
							and the remaining 1, bonus_bucks, is used to initialize a subclass-specific member variable.
		Return:				None, initializes a PlatinumCustomer object. */
	public PlatinumCustomer(String guest_ID, String first_name, String last_name, float amount_spent, float bonus_bucks)
	{
		// Spec says no invalid data in preferred.dat so amount_spent >= 200.00 (no need to check)
		super(guest_ID, first_name, last_name, amount_spent);
		bonusBucks = bonus_bucks;
		int calculateFrom;
		calculateFrom = ((int) (Math.round(amount_spent*100))) / 100; 
		calculateBonusBucksFrom = (calculateFrom/5) * 5;	// 204/5 = 40*5 = 200
	}



	/*	Function Type:		constructor (overloaded)
		Descr:				This overloading of the constructor uses super() to invoke the overloaded constructor
							of this subclass's superclass, Customer().
		Param:    			GoldCustomer fromGoldCustomer, float amount_spent
							A GoldCustomer object and its amount spent.
							They are supplied to this constructor so that the GoldCustomer's members can be accessed (using its accessors)
							to initialize a PlatinumCustomer object from the superclass's overloaded constructor.
		Return:				None, initializes a PlatinumCustomer object. */
	private PlatinumCustomer(GoldCustomer fromGoldCustomer, float amount_spent)
	{
		super(fromGoldCustomer.getGuestID(), fromGoldCustomer.getFirstName(), fromGoldCustomer.getLastName(), amount_spent);
		processedLastOrder = true;
		calculateBonusBucksFrom = 200;
		bonusBucks = 0.00f;
		setBonusBucks();
	}



	/*	Function 4:			toDebugString()
		Descr:				This function concatenates customer information
							given by its corresponding superclass method, along with bonusBucks,
							into a string for debugging.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toDebugString()
	{
		return super.toDebugString() 
			+ " bonusBucks: " + bonusBucks;
	}



	/*	Function 5:			toOutputString()
		Descr:				This function concatenates customer information
							given by its corresponding superclass method, along with strBonus,
							first converting the float for bonusBucks to strBonus for output.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toOutputString()
	{
		String strBonus = ((int) (Math.round(bonusBucks))) + "";
		return super.toOutputString() 
			+ " " + strBonus;
	}



	/*	Functions 6-8:		getBonusBucks(), getsDiscountOrReward(), getSaleFactor()
		Descr:				These 3 functions are accessors.
		Param:    			None for all.
		Return:				The corresponding members accessed by each accessor. */
	public float getBonusBucks()
	{
		return bonusBucks;
	}
	public boolean getsDiscountOrReward()
	{
		return true;	// this accessor returns a static value
	}
	public float getSaleFactor()
	{
		return 1.0F;	// this accessor returns a static value
	}



	/*	Function 9:			setBonusBucks()
		Descr:				This function is a mutator.
		Param:    			The corresponding member mutated by this mutator.
		Return:				None. */
	private void setBonusBucks()
	{
		boolean DEBUG = false;

		int amount_spent_round_dollars = ((int) (Math.round(getAmountSpent()*100)))/100; 
		int calculateTo = (amount_spent_round_dollars/5) * 5; 
		bonusBucks = bonusBucks + (calculateTo - calculateBonusBucksFrom) / 5;

		if(DEBUG) System.out.println("bonus bucks = " + bonusBucks + " from: " + calculateBonusBucksFrom + " to: " + calculateTo);

		calculateBonusBucksFrom = calculateTo;
	}



	/*	Function 10:		promote()
		Descr:				This function promotes a GoldCustomer object to PlatinumCustomer.
		Param:    			GoldCustomer fromGoldCustomer, float order_total_price
							A GoldCustomer object representing the customer, and amount ordered by the customer.
		Return:				PlatinumCustomer pc
							An object of the PlatinumCustomer subclass. */
	protected static PlatinumCustomer promote (GoldCustomer fromGoldCustomer, float order_total_price)
	{

		PlatinumCustomer pc = new PlatinumCustomer();
		float tentativeAmountSpent = fromGoldCustomer.getAmountSpent() + order_total_price*fromGoldCustomer.getSaleFactor();
		int int_tentativeAmountSpent = (int) (Math.round(tentativeAmountSpent*1000.0f)); 
		
		if(int_tentativeAmountSpent < (PlatinumCustomer.PLATINUM_MIN_AMOUNT_SPENT_INT - 5))
		{
			return pc;
		} 

		pc = new PlatinumCustomer(fromGoldCustomer, tentativeAmountSpent);
		fromGoldCustomer.setPromoted();
		/// pc.setAmountSpent(tentativeAmountSpent);
		
		return pc;
	}



	/*	Function 11:		processOrder()
		Descr:				This function processes a customer's order in accordance with their customer status.
							`returned object is always an "uninitialized" PlatinumCustomer object`
							`as there is no possibility of promoting a PlatinumCustomer object.`
							`example  :   c2 = pc.processOrder(10.00f);`
							`if(pc.isProcessedLastOrder() == true) : order processed successfully`
							`if(pc.isProcessedLastOrder() == false) : order process failed`
		Param:    			float order_total_price
							Amount ordered by the customer.
		Return:				PlatinumCustomer()
							An object of the PlatinumCustomer class. */
	public Customer processOrder(float order_total_price)
	{
		float tentativeAmountSpent; 
		tentativeAmountSpent = getAmountSpent() + order_total_price*getSaleFactor();
		int int_tentativeAmountSpent = (int) (Math.round(tentativeAmountSpent*1000.0f)); 

		// this check is redundant because preferred.dat has no invalid data 
		if(int_tentativeAmountSpent >= (PlatinumCustomer.PLATINUM_MIN_AMOUNT_SPENT_INT - 5))
		{
			// BONUS TO DO
			int int_order_total_price = (int) (Math.round(order_total_price*1000.0f));
			int int_bonus_bucks = (int) (Math.round(bonusBucks*1000.0f));

			if(int_order_total_price >= int_bonus_bucks)
			{
				tentativeAmountSpent = tentativeAmountSpent - bonusBucks;
				bonusBucks = 0.00f;	// reset bonusBucks because it's all used up
				setAmountSpent(tentativeAmountSpent);
				setBonusBucks();
			}
			else
			{
				bonusBucks = bonusBucks - order_total_price;
			}

			processedLastOrder = true;
			/// return this;
			return new PlatinumCustomer();
		}
		else
		{
			PlatinumCustomer pc = new PlatinumCustomer();	// "un-initialized" object
			pc.processedLastOrder = false;
			return new PlatinumCustomer();
		}
		
	}

}	// end of class PlatinumCustomer
