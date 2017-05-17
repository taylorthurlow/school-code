#ifndef POLYX_H
#define POLYX_H

#include <vector>
#include <iostream>

class Polyx {
	private:
		int size;
	
	public:
		std::vector<double> coeffs;
		
		int getSize() const;
		Polyx();
		Polyx(const Polyx &p1); // Copy constructor
		~Polyx();
		Polyx(double coeffs[], int size);
		double f(int xVal);
		friend std::ostream& operator<<(std::ostream& stream, const Polyx &polyx);
		friend Polyx operator+(const Polyx &p1, const Polyx &p2);
};

#endif