#include <iostream>
#include <string>
#include <math.h>
#include <assert.h>

#include "Polyx.h"

Polyx::Polyx() {
	size = 1;
	coeffs.push_back(1);
}

Polyx::Polyx(const Polyx &p1) {
	size = p1.getSize();
	for (int i = 0; i < size; i++) {
		coeffs.push_back(p1.coeffs[i]);
	}
}

Polyx::~Polyx() {
	// Vector deletion will be handled automatically
}

Polyx::Polyx(double newCoeffs[], int newSize) {
	assert (newSize > 0);
	size = newSize;
	for (int i = 0; i < newSize; i++) {
		coeffs.push_back(newCoeffs[i]);
	}
}

int Polyx::getSize() const {
	return size;
}

double Polyx::f(int xVal) {
	double sum = 0;
	for (int i = size - 1; i >= 0; i--) {
		sum += (coeffs[i] * pow(static_cast<float>(xVal), i));
	}
	return sum;
}

std::ostream& operator<<(std::ostream& stream, const Polyx& polyx) {
	int theSize = polyx.getSize();
	for (int i = theSize - 1; i >= 0; i--) {
		
		bool nonZero = true;
		int exponent = i;
		std::string addSub = " + ";
		
		if (polyx.coeffs[i] < 0) {
			addSub = " - ";
		} else if (polyx.coeffs[i] == 0) {
			nonZero = false;
		}
		
		if (nonZero) {

			if (i != theSize - 1) { // Not on first coeff print
				stream << addSub;
			}
			
			stream << fabs(polyx.coeffs[i]);
			
			if (exponent == 1) {
				stream << "x";
			} else if (exponent > 1) {
				stream << "x^" << exponent;
			}
		}
	}
	return stream;
}

Polyx operator+(const Polyx &p1, const Polyx &p2) {
	// Find smaller of two that are being added, and copy it to p3,
	// this intentionally makes the result not include any terms that
	// the bigger polynomial may have.
	
	Polyx p3(p1.coeffs.size() > p2.coeffs.size() ? p2 : p1);
	
	// Loop through and add 
	for (int i = 0; i < p3.getSize(); i++) {
		p3.coeffs[i] += p2.coeffs[i];
	}
	
	return p3;
}