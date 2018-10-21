## Compiling
This code was written and run using JDK 10, so support for any previous versions of Java is uncertain.

Change directory into the directory containing the provided source `.java` files and run the following command to compile:

    javac *.java

This will generate `.class` files for each source file. Run the program using:

    java Main

## Usage Instructions
Upon running the program, a menu will prompt for a choice. The choices are:

1) Generate a random 21-Queen board and solve it. This will generate a single board state and solve it with both Simulated Annealing and Genetics. If a solution was found before the predetermined cutoff, the program will print the search cost, the solution board, and time taken in milliseconds. If no solution was found, this information is omitted.

2) Generate and solve 500 random boards. This option will generate 500 boards and solve them with each algorithm. It prints each board's results in a CSV format for easy data analysis.