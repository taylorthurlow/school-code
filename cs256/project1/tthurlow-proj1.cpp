/**
 *	Name:		Thurlow, Taylor
 *	Project:	1
 *	Due:		10/12/16
 *	Course:		cs25602-f16
 *
 *	Description:
 *		This program asks the user for console input of three coefficients
 *		to be placed into the quadractic equation and computed. The program
 *		will output the constructed quadratic equation, and then both
 *		computed answers.
**/

#include <iostream>
#include <cmath>

using namespace std;

int main() {
	double a, b, c;
	double x1, x2;
	string input;
	
	cout << "T. Thurlow's Quadratic Equation" << endl << endl;
	cout << "Please enter values for a, b, and c: ";
	cin >> a >> b >> c;

	// The following three blocks of code strip trailing zeroes from the
	// printed versions of a, b, and c. This is due to the fact that the
	// output of to_string() is not modifyable. C++11 comes in handy!
	
	string printableA = to_string(a);
	printableA.erase(printableA.find_last_not_of('0') + 1, string::npos);
	if(printableA.back() == '.') printableA.pop_back();

	string printableB = to_string(b);
	printableB.erase(printableB.find_last_not_of('0') + 1, string::npos);
	if(printableB.back() == '.') printableB.pop_back();

	string printableC = to_string(c);
	printableC.erase(printableC.find_last_not_of('0') + 1, string::npos);
	if(printableC.back() == '.') printableC.pop_back();
	
	// Legibly print the newly constructed quadratic equation
	
	cout << printableA + " x^2 + " + printableB + " x + " + printableC + " = 0" << endl;

	// Do each part (positive and negative) of the quadratic equation
	// seperately, and store their answers in x1 and x2
	x1 = ((-1 * b) + sqrt(pow(b, 2) - (4 * a * c))) / (2 * a);
	x2 = ((-1 * b) - sqrt(pow(b, 2) - (4 * a * c))) / (2 * a);
	
	cout << "x1 = " << x1 << endl;
	cout << "x2 = " << x2 << endl;
	
	return 0;
}