## Compiling
This code was written and run using JDK 8, so support for any previous versions of Java is uncertain.

Change directory into the directory containing the provided source `.java` files and run the following command to compile:

    javac *.java

This will generate `.class` files for each source file. Run the program using:

    java Main

## Usage Instructions
Upon running the program, the program will ask two questions. First, it will ask you how long to allow the computer player to spend on a turn, in seconds. The second will ask whether or not the opponent player is going first. Any input to this question other than `y` or `Y` will answer no to the question.

The program will initialize a new game, and start with the player depending upon your answer to the first question. To enter the move of the opponent, input a two-character move string, like `A5` or `f3` (case insensitive). If the move is valid, the move will be placed on the board. If it is invalid, you will be prompted for the move again.
