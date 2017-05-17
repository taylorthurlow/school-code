//
//	 	   Name: Thurlow, Taylor
//		Project: 2
// 			Due: 10/26/2016
//  	 Course: cs25602
//
//  Description:
//          A guessing game. Generates a random number between 1 and 100, and asks the user
//			to guess the right value. It will tell you higher or lower. To quit, give the
//			number 0. After 8 incorrect answers, the game will quit and give you the correct
//			answer.
//

#include <iostream>
#include <cmath>
#include <cstdlib>

using namespace std;

int getGuess() {
	bool validGuess = false;
	while (!validGuess) {
		string guess;
		cout << "Please enter your guess? ";
		cin >> guess;
		int guessInt = stoi(guess);
		if (guessInt <= 100 && guessInt >= 0) return guessInt;
	}
	return 0;
}

int main(int argc, char *argv[]) {
	cout << "T. Thurlow's Guess-my-Number" << endl << endl;
	cout << "A secret number between 1-100 generated..." << endl << endl;
	bool win = false, quit = false;
	int incorrectGuesses = 0;
	srand(time(NULL));
	int secret = 1 + (rand() % 100);
	while (!win && !quit && incorrectGuesses != 8) {
		int guess = getGuess();
		if (guess == secret) {
			win = true;
		} else {
			if (guess > secret && guess != 0) cout << "Guess is high..." << endl;
			if (guess < secret && guess != 0) cout << "Guess is low..." << endl;
			if (guess == 0) quit = true;
			incorrectGuesses++;
		}
	}
	if (win) cout << "Correct in " << incorrectGuesses + 1 << " guesses." << endl;
	if (quit) cout << "The secret number is " << secret << "." << endl;
	if (incorrectGuesses == 8) cout << "Too many guesses, the secret number is " << secret << "." << endl;
	
	return 0;
}