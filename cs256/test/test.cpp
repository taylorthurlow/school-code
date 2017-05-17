/*input
11449023
*/

#include <iostream>

using namespace std;

int main() {
	int x;
	cout << "Input a value for x: ";
	cin >> x;
	cout << endl;
	int y = 100;
	int sum = x + y;
	cout << x << " + " << y << " = " << sum << endl;
	return 0;
}