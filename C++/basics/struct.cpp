// Structure demo

#include "iostream"
using namespace std;

class test1 {
public:
	int y = 10;
};
struct test : test1 {}; // member variables and function in struct is public by default! Assumes public by default.

int main() {
	test obj;
	cout << obj.y; // prints 10
	return 0;
}
