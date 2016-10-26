#include<iostream>
using namespace std;

int main () {
	int *p; // this is a wild pointer
	int a = 10;
	p = &a; // p now references a
	cout << "value of p is " << p << endl;
	*p = 20;
	cout << "value of a is now " << a << endl;
}