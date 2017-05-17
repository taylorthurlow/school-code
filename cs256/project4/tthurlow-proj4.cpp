/**
 * Name: Thurlow, Taylor
 * Project: 4
 * Due: 12/08/2016
 * Course: cs25602-f16
 *
 * Description:
 * 		Basic inheritance with virtual function.
 */

#include <iostream>

using namespace std;

class Ship {
protected:
	string name, year;

public:
	Ship() {
		name = "Unnamed";
		year = "0";
	}

	~Ship() {
		
	}

	Ship(const Ship &s1) {
		name = s1.getName();
		year = s1.getYear();
	}

	Ship(std::string name, std::string year) {
		this->name = name;
		this->year = year;
	}

	std::string getName() const {
		return name;
	}

	void setName(std::string name) {
		this->name = name;
	}

	std::string getYear() const {
		return year;
	}

	void setYear(std::string year) {
		this->year = year;
	}

	virtual void print() {
		std::cout << name << " was built in " << year << "." << std::endl;
	}
};

class CruiseShip : public Ship {
private:
	int maxCapacity;

public:
	CruiseShip() {
		name = "Unnamed";
		year = "0";
		maxCapacity = 1000;
	}

	~CruiseShip() {
		
	}

	CruiseShip(const CruiseShip &s1) {
		name = s1.getName();
		year = s1.getYear();
		maxCapacity = s1.getMaxCapacity();
	}

	CruiseShip(std::string name, std::string year, int maxCapacity) {
		this->name = name;
		this->year = year;
		this->maxCapacity = maxCapacity;
	}

	void setMaxCapacity(int maxCapacity) {
		this->maxCapacity = maxCapacity;
	}

	int getMaxCapacity() const {
		return maxCapacity;
	}

	void print() {
		std::cout << name << " has a max capacity of " << maxCapacity << " passengers." << std::endl;
	}
};

class CargoShip : public Ship {
private:
	int cargoCapacity;

public:
	CargoShip() {
		name = "Unnamed";
		year = "0";
		cargoCapacity = 150000;
	}

	~CargoShip() {
		
	}

	CargoShip(const CargoShip &s1) {
		name = s1.getName();
		year = s1.getYear();
		cargoCapacity = s1.getCargoCapacity();
	}

	CargoShip(std::string name, std::string year, int cargoCapacity) {
		this->name = name;
		this->year = year;
		this->cargoCapacity = cargoCapacity;
	}

	void setCargoCapacity(int cargoCapacity) {
		this->cargoCapacity = cargoCapacity;
	}

	int getCargoCapacity() const {
		return cargoCapacity;
	}

	void print() {
		std::cout << name << " has a max capacity of " << cargoCapacity << " tons." << std::endl;
	}
};

int main() {
	const int NUM_SHIPS = 4;
	Ship *theShips[NUM_SHIPS] =
		{
			new Ship("Lame", "1956"),
			new CruiseShip("Titanic", "1909", 3327),
			new CruiseShip("Allure of the Seas", "2008", 6296),
			new CargoShip("The Big One", "2016", 678000)
		};

	for (int i = 0; i < NUM_SHIPS; i++)
		theShips[i]->print();

	return 0;
}