// Run Time Polymorphism in a function

#include "iostream"
using namespace std;

class base {
	public :
		virtual void show() { // use of virtual keyword suggests run time polymorphism or dynamic binding.
			cout << "in base class \n";
		}
};

class derived : public base {
	public :
		void show() {
			cout << "in derived class \n";
		}
};

void print(base &b) {
	b.show();
}

int main() {
	base b;
	derived d;
	print(b);
	print(d);
	return 0;
}

/* FOOTNOTES :

We can make a function polymorphic by passing objects as reference (or pointer) to it. For example, in the following program, print() receives a reference to the base class object. print() calls the base class function show() if base class object is passed, and derived class function show() if derived class object is passed.

*/


