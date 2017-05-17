/**
 * Name: Thurlow, Taylor
 * Project: xtra 2
 * Due: 12/08/2016
 * Course: cs25602-f16
 *
 * Description:
 * 		Implementation of stack datatype using std::vector
 */

#include <iostream>
#include <vector>

using namespace std;

template <typename T>
class Stack {
private:
	int size;
	vector<T> theStack;

public:
	Stack() {
		size = 16;
	}

	~Stack() {

	}

	Stack(const Stack &s1) {
		size = s1.getSize();
		theStack = s1.getStack();
	}

	Stack(int size) {
		this->size = size;
	}

	vector<T> getStack() {
		return theStack;
	}

	int getSize() const {
		return size;
	}

	void setSize(int size) {
		this->size = size;
	}

	T pop() {
		if (theStack.empty()) {
			throw out_of_range("tried to pop off empty sack");
		}

		T popped = theStack.back();
		theStack.pop_back();
		return popped;
	}

	void push(const T &toPush) {
		if (theStack.size() == size) {
			throw out_of_range("tried to push to full stack");
		}

		theStack.push_back(toPush);
	}
};

// Push item onto the top of the stack
template <typename T>
Stack<T>& operator>>(T toPush, Stack<T> &stk) {
	stk.push(toPush);
	return stk;
}


// Pop item off of the top of the stack
template <typename T>
T& operator>>(Stack<T> &stk, T &popped) {
	popped = stk.pop();
	return popped;
}

int main() {
	cout << "Extra Credit Project #2 by Taylor Thurlow" << endl;

	// Sample implementation from PDF

	Stack<int> iStack(32);
	Stack<string> sStack; // default size 16
	int item = 12;

	try {
		(item + 12) >> iStack; // push
	} catch (char *msg) {
		cerr << msg << endl; // msg is “stack full”
		exit(EXIT_FAILURE);
	}

	try {
		iStack >> item; // pop
		cout << item << endl;
	} catch (char *msg) {
		cerr << msg << endl; // msg is “stack empty”
		exit(EXIT_FAILURE);
	} 

	return 0;
}