<!-- To preview this markdown file in VSCode: Ctrl+Shift+V -->

# CS2 Portfolio | CS 2336 (Computer Science II)
## Author: Bushra Rahman
GitHub Pages link: https://br815.github.io/CS2-portfolio/

This repository contains university programming assignments from my [Computer Science II course](/CS2336).

The purpose of this repository is archival and educational:
- to track my progress learning Java
- to practice writing clear, correct, and well-structured code
- to reflect on design decisions and showcase my improved understanding of the language

## Highlights
- Implementation of fundamental data structures like linked lists, stacks, and queues
- Implementation and use of advanced data structures like binary search trees (BSTs), hash tables, and graphs
- Implementation and analysis of complex algorithms like recursive algorithms

## Instructions to Install Java
The Java Development Kit (JDK) is necessary to run these assignments (since it contains the `javac` bytecode compiler and the Java Runtime Environment (JRE), which is used to run Java applications and which contains the Java Virtual Machine (JVM), which is used to execute Java bytecode).

To install Java JDK on Windows 11 or less, follow [these instructions](https://github.com/br815/CS1-portfolio/blob/main/CS1337/Eclipse_for_Windows_with_MinGW.pdf) (refer to Pages 1-2 of the linked PDF):
1. Download the x64 installer (`.exe` extention) for the latest version of the Standard Java SE Development Kit from [this Oracle link](https://www.oracle.com/java/technologies/downloads/).
2. Run the installer. Don’t make any changes with the installer.
3. Add the Java JDK the `Path` Environment Variable. [This YouTube video](https://www.youtube.com/watch?v=uadGsNA6h5Q) has the steps to follow:
    * Open `Control Panel >> System and Security >> System >> Advanced System Settings`.\
    (Alternatively, open `Settings >> System >> About >> Advanced System Settings`.)\
    This opens a window for `System Properties`.
    * In `System Properties`, go to `Environment Variables`.\
    In `Environment Variables`, look down at `System variables` and scroll until you reach `Path`.\
    Select `Path` and hit `Edit`.
    * Once you’ve opened `Edit environment variable`, find the file path for Java JDK’s `/bin` folder in your File Explorer and copy it.\
    It should look similar to:
    <!-- CODE START -->
    ```
    C:\Program Files\Java\jdk-12\bin
    ```
    <!-- CODE END -->
    * Go back to `Edit environment variable`, hit `New`, and paste the file path for `/bin`.
    * Then hit `OK` in `Edit environment variable`, hit `OK` in `Environment Variables`, and hit `OK` in `System Properties`.
    * Java JDK should now be properly installed and locatable within `Path`. To test in Command Prompt, run:
    <!-- CODE START -->
    ```
    java --version
    ```
    <!-- CODE END -->
    You should see output similar to:
    <!-- CODE START -->
    ```
    java version "12" 2019-03-19
    Java(TM) SE Runtime Environment (build 12+23)
    Java HotSpot(TM) 64-Bit Server VM (build 12+23, mixed mode, sharing)
    ```
    <!-- CODE END -->
4. After installing Java and adding it to `Path`, make sure to restart your IDE to avoid compilation errors.

## Java Compilation & Execution Overview
(Using [HW0](/CS2336/HW0/) as an example. HW0 contains a [src](/CS2336/HW0/src/) folder,\
which contains a package called [Tickets](/CS2336/HW0/src/Tickets/),\
which contains 2 source files [Main.java](/CS2336/HW0/src/Tickets/Main.java) and [Auditorium.java](/CS2336/HW0/src/Tickets/Auditorium.java).)

Open terminal in `CS2-portfolio` and run:
<!-- CODE START -->
```
cd CS2336/HW0                           # cd to project root
mkdir build                             # make a build folder if it doesn't exist
javac -d build src/Tickets/*.java       # compile CLASS files into the build folder
java -cp build Tickets.Main             # provide the build folder as the classpath to Main
```
<!-- CODE END -->
Java compilation & execution can occur from anywhere through the use of relative paths & classpaths, as long as the compilation instruction specifies the relative path to the `.java` source files & the execution instruction specifies the classpath to the `.class` bytecode files. However, execution *should* occur from the project root (because that is the conventional location from which your program should retrieve any IO files).

In order to achieve both compilation & execution from the project root, this compilation instruction goes through `src/Tickets/` to compile all `.java` source files in the [Tickets](/CS2336/HW0/src/Tickets/) package that is in [src](/CS2336/HW0/src/).  Alternatively, each source file can be compiled individually through their paths:
<!-- CODE START -->
```
javac -d build src/Tickets/Main.java src/Tickets/Auditorium.java
```
<!-- CODE END -->
By default, Java produces `.class` files directly in the same package as the `.java` files; however, the provided compilation instruction uses the `-d` flag to produce `.class` files in a [build](/CS2336/HW0/build/) folder for clearer file organization. (A [same-name package](/CS2336/HW0/build/Tickets/) will be generated inside [build](/CS2336/HW0/build/) to contain the `.class` files.)

Then, execution can occur from the project root as long as the flag `-cp` or `-classpath` is specified. This flag specifies the path to the driver class `<packageName>.<className>` (ie. the `Main` CLASS file containing `public static void main(String[] args)`), which for this project is through `build/`. (The driver class's full name can also be given as `<packageName>/<className>`, but typically in Java, `.` is for runtime commands while `/` is used for filepaths during compilation.)

Similar compilation & execution behavior can be achieved through alternative commands, like:
<!-- CODE START -->
```
cd CS2336/HW0/src/Tickets           # cd to project package
javac *.java                        # or, javac Main.java Auditorium.java (note that here, .class files are produced directly in the package)
cd ../..                            # go back to the project root
java -classpath src Tickets.Main    # uses full name flag -classpath instead of the abbreviated flag -cp
```
<!-- CODE END -->

## Homework Instructions & Readme Files
* ...
    * ...

## Notes
As a learning exercise, some of my code explores alternative designs that go beyond what was required for this course.

This code is not intended as production software.
