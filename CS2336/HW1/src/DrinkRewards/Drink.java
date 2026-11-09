/*	Author:					Bushra Rahman
	Course:					CS 2336 (Spring 2020)
   	Date:					2/8/2020
	Drink.java:				This source file defines the Drink class, which includes 14 function definitions. */

package DrinkRewards;



// Beginning of class definition of Drink
public class Drink
{
	// class-specific member variables
	private String type;				// Soda,  Tea, or   Fruit punch
	private float pricePerOunce;		// $0.20, $0.12, or $0.15
	private float priceOfDrink;
	private float pricePerSquareInch;	// for personalization
	private float priceOfPersonalization;
	private float totalPrice;

	private char sizeLetter;			// S, M, or L
	private float diameter;
	private float height;
	private float area;
	private float sizeVolume;			// 12, 20, or 32 oz



	/*	Function Type:		constructor
		Descr:				This constructor initializes class members.
		Param:    			String type_name, char size_letter, float price_per_square_inch
							Values for the members of a Drink object.
							Other members are initialized inside the constructor based on these values.
		Return:				None, initializes a Drink object. */
	public Drink(String type_name, char size_letter, float price_per_square_inch)
	{
		type = type_name;
		sizeLetter = size_letter;
		pricePerSquareInch = price_per_square_inch;
		
		if(sizeLetter == 'S')
		{
			diameter = 4.0F;
			height = 4.5F;
			sizeVolume = 12.0F;
		}
		else if(sizeLetter == 'L')
		{
			diameter = 5.5F;
			height = 7.0F;
			sizeVolume = 32.0F;
		}
		else 
		{
			diameter = 4.5F;
			height = 5.75F;
			sizeVolume = 20.0F;
		}
	
		if(type.equalsIgnoreCase("Soda"))
		{
			pricePerOunce = 0.20F;
		}
		else if(type.equalsIgnoreCase("punch"))
		{
			pricePerOunce = 0.15F;
		}
		else 
		{
			pricePerOunce = 0.12F;
		}
		priceOfDrink = pricePerOunce * sizeVolume;
		
		area = (float) (Math.PI * diameter * height);
		priceOfPersonalization = area * pricePerSquareInch;

		totalPrice = pricePerOunce * sizeVolume + area * pricePerSquareInch;
		/// totalPrice = pricePerSquareInch;	// This line FOR DEBUG ONLY 
		/// System.out.println("size: " + sizeLetter + " area: " + area);
	}



	/*	Functions 2-12:		getType(), getPricePerOunce(), getPriceOfDrink(), getPricePerSquareInch(), getPriceOfPersonalization(), 
							getTotalPrice(), getSizeLetter(), getDiameter(), getHeight(), getArea(), getSizeVolume()
		Descr:				These 11 functions are accessors.
		Param:    			None for all.
		Return:				The corresponding members accessed by each accessor. */
	public String getType()
	{
		return type;
	}
	public float getPricePerOunce()
	{
		return pricePerOunce;
	}
	public float getPriceOfDrink()
	{
		return pricePerOunce * sizeVolume;									// calculation required for accessor
	}
	public float getPricePerSquareInch()
	{
		return pricePerSquareInch;
	}
	public float getPriceOfPersonalization()
	{
		return area * pricePerSquareInch;									// calculation required for accessor
	}
	public float getTotalPrice()
	{
		return pricePerOunce * sizeVolume + area * pricePerSquareInch;		// calculation required for accessor
	}
	public char getSizeLetter()
	{
		return sizeLetter;
	}
	public float getDiameter()
	{
		return diameter;
	}
	public float getHeight()
	{
		return height;
	}
	public float getArea()
	{
		return area;
	}
	public float getSizeVolume()
	{
		return sizeVolume;
	}



	/*	Function 13:		getBriefString()
		Descr:				This function concatenates drink information into a brief string.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String getBriefString()
	{
		return "Drink: " + type
			+ " size: " + sizeLetter + " "
			+ " $" + pricePerSquareInch + " per square inch";
	}



	/*	Function 14:		toString()
		Descr:				This function concatenates drink information into a string.
		Param:    			None.
		Return:				An unnamed concatenated string. */
	public String toString()
	{
		return "Drink: " + type 
			+ " size: " + sizeLetter 
			+ " " + sizeVolume 
			+ " $" + totalPrice;
	}

} // end of class Drink
