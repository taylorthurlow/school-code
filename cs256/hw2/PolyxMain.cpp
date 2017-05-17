#include <iostream>

#include "Polyx.h"

using namespace std;

int main(int argc, char *argv[]) {
	double c1[] = { 4, -5, 3 };
	Polyx p1(c1, sizeof(c1) / sizeof(double)), p2(p1);
	double eval = p1.f(5);
	cout << p1 << endl << "f(5)=" << p1.f(5) << endl;
	Polyx p3 = p1 + p2;
	cout << "p1 + p2 = " << p3 << endl;
	
	double c2[] = { 1, 2, 3 };
	Polyx polys[3];
	polys[0] = Polyx(c2, sizeof(c2) / sizeof(double));
	polys[1] = Polyx(polys[0]);
	polys[2] = Polyx(polys[1]);
	
	cout << polys[0] << endl;
	cout << polys[1] << endl;
	cout << polys[2] << endl;
}