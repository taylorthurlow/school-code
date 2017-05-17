/**
 * Name: Thurlow, Taylor
 * Project: xtra 1
 * Due: 12/08/2016
 * Course: cs25602-f16
 *
 * Description:
 * 		Template based-implementation of a function which returns the number of
 * 		occurrences of an item within an array of those items
 */

#include <iostream>

using namespace std;

template <typename T>
int frequency(T theArray[], int size, T find) {
	int count = 0;
	for (int i = 0; i < size; i++) {
		if (theArray[i] == find) {
			count++;
		}
	}
	return count;
}

int main() {
	cout << "Extra Credit Project #1 by Taylor Thurlow" << endl;
	char cArray[] = "this is a sample line of text";
	int cArraySize = sizeof(cArray);
	cout << "char: " << frequency<char>(cArray, cArraySize, 'i') << endl;

	int intArray[] = {1, 2, 3, 4, 5, 6, 6, 7, 7, 7, 0};
	int intArraySize = sizeof(intArray) / sizeof(int);
	cout << "int: " << frequency<int>(intArray, intArraySize, 6) << endl;

	double doubleArray[] = {0.05, 1.05, 1.05, 1.05, 6.555, 0.22};
	int doubleArraySize = sizeof(doubleArray) / sizeof(double);
	cout << "double: " << frequency<double>(doubleArray, doubleArraySize, 1.05) << endl;

	string stringArray[] = {
		"some first string",
		"some second string",
		"some duplicate string",
		"some duplicate string",
		"some duplicate string",
		"some duplicate string",
		"some other string"
	};
	int stringArraySize = sizeof(stringArray) / sizeof(string);
	cout << "string: " << frequency<string>(stringArray, stringArraySize, "some duplicate string") << endl;

	return 0;
}