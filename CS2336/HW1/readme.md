<!-- To preview this markdown file in VSCode: Ctrl+Shift+V -->

# CS2 Portfolio | HW1: Customer Rewards System Using Inheritance & Polymorphism
## Author: Bushra Rahman
This is the repository for [`CS 2336/HW1/`](/CS2336/HW1).

## Compilation & Execution Instructions
Open terminal in `CS2-portfolio` and run:
<!-- CODE START -->
```
cd CS2336/HW1
mkdir build
javac -d build src/DrinkRewards/Main.java src/DrinkRewards/Drink.java src/DrinkRewards/Order.java src/DrinkRewards/OrderManager.java src/DrinkRewards/Customer.java src/DrinkRewards/GoldCustomer.java src/DrinkRewards/PlatinumCustomer.java
# Or use the *.java wildcard for a conciser compilation instruction:
javac -d build src/DrinkRewards/*.java
java -cp build DrinkRewards.Main
```
<!-- CODE END -->

## Homework Instructions
* [HW 1: Inheritance & Polymorphism](/CS2336/HW1/HW1_Inheritance_Polymorphism.pdf)

## `src/` Folder
### Source Files in the `DrinkRewards/` Package
* [Main.java](/CS2336/HW1/src/DrinkRewards/Main.java): program driver containing `main()`
* [Drink.java](/CS2336/HW1/src/DrinkRewards/Drink.java): contains the definition of a helper class, including 14 methods
* [Order.java](/CS2336/HW1/src/DrinkRewards/Order.java): contains the definition of a helper class, including 6 methods
* [OrderManager.java](/CS2336/HW1/src/DrinkRewards/OrderManager.java): contains the definition of a helper class, including 13 methods
* [Customer.java](/CS2336/HW1/src/DrinkRewards/Customer.java): contains the definition of a base class, including 24 methods
* [GoldCustomer.java](/CS2336/HW1/src/DrinkRewards/GoldCustomer.java): contains the definition of a Customer subclass, including 11 methods
* [PlatinumCustomer.java](/CS2336/HW1/src/DrinkRewards/PlatinumCustomer.java): contains the definition of a Customer subclass, including 11 methods

## `data/` Folder
When executing from the project root, input & output file names must be provided through their relative path (ie. with the prepend `data/`). (This functionality is hard-coded into [OrderManager.java](/CS2336/HW1/src/DrinkRewards/OrderManager.java).)
* [preferred.dat](/CS2336/HW1/preferred.dat): 
* [customers.dat](/CS2336/HW1/customers.dat): 
* [orders.dat](/CS2336/HW1/orders.dat): 
