#include <iostream>
#include <math.h>
#include <string>

#include "Complex.h"

Complex::Complex() {
	a = 0;
	b = 0;
}

Complex::Complex(const Complex &c1) {
	a = c1.getA();
	b = c1.getB();
}

Complex::Complex(int newA, int newB) {
	a = newA;
	b = newB;
}

int Complex::getA() const {
	return a;
}

int Complex::getB() const {
	return b;
}

void Complex::setA(int newA) {
	a = newA;
}

void Complex::setB(int newB) {
	b = newB;
}

bool Complex::equals(Complex c2) {
	return (a == c2.getA() && b == c2.getB());
}

Complex Complex::add(Complex c1, Complex c2) {
	return Complex(c1.getA() + c2.getA(), c1.getB() + c2.getB());
}

Complex Complex::multiply(Complex c2) {
	int newA = (a * c2.getA()) - (b * c2.getB());
	int newB = (b * c2.getA()) + (a * c2.getB());
	return Complex(newA, newB);
}

void Complex::print(std::string pre) {
	std::string signText = " + ";
	if (b < 0) signText = " - ";
	std::cout << pre << a << signText << abs(b) << "i" << std::endl;
}