## Compiling
This code was written and run using JDK 10, so support for any previous versions of Java is uncertain.

Change directory into the directory containing the provided source `.java` files and run the following command to compile:

    javac *.java

This will generate `.class` files for each source file. Run the program using:

    java Main

## Usage Instructions
Upon running the program, a menu will prompt for a choice. The choices are:

1) Generate a random solvable 8-Puzzle and solve it. This will generate a single puzzle and solve it with both heuristics (Hamming and Manhattan). For each heuristic, it will print the depth of the solution and the number of states generated to reach that solution. It will then print the solution steps. Depending on your terminal environment, it may also show the tile which was moved relative to the previous state in a purple color.

2) Provide a specific puzzle configuration. This will allow you to provide a puzzle for solution. It will only accept puzzles in a space-delimited format such as: `1 4 0 2 3 5 7 6 8`. If the puzzle is not solvable, you will be notified and the program will terminate. Use a zero to represent the empty space in the puzzle. It will then print the solution information and steps exactly as option #1 does.

3) Perform a series of tests. This is the 2000-puzzle generator which was used to generate the data used in the analysis in the project report. If the solution to a puzzle reaches depth 23 or more, the puzzle is thrown out and does not count towards the 2000 puzzle count. This happens because generating 2000 puzzles takes too long otherwise. This mode will print out each result in a CSV format.
