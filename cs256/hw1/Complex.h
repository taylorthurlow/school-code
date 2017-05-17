#ifndef COMPLEX_H
#define COMPLEX_H

#include <string>

class Complex {
	private:
		int a;
		int b;
	
	public:
		Complex();
		Complex(const Complex &c1);
		Complex(int newA, int newB);
		int getA() const;
		int getB() const;
		void setA(int newA);
		void setB(int newB);
		bool equals(Complex c2);
		static Complex add(Complex c1, Complex c2);
		Complex multiply(Complex c2);
		void print(std::string pre);
};

#endif
