#include "Complex.h"
#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
	Complex c1(1, 2), c2(2,3), c3(c1), sum;
	
	c1.print("c1: ");
	c2.print("c2: ");
	c3.print("c3: ");
	sum.print("sum: ");
	
	Complex c4(1, 1), c5(3, -5);
	Complex c6 = c4.multiply(c5);
	c6.print("c6: ");
	
	Complex c7(4, 3), c8(23, -20);
	Complex c9 = Complex::add(c7, c8);
	c9.print("c9: ");
	
	Complex c10(1, 1);
	cout << "are c4 and c10 equal?: ";
	if (c4.equals(c10)) {
		cout << "true" << endl;
	} else {
		cout << "false" << endl;
	}
}