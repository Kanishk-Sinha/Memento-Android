#include<iostream>
#include<stdlib.h>
using namespace std;

// allocate a pointer first and then assign its value
int main () {
	int *p = (int *)malloc(sizeof(int)); // implicit typecast not supported in c++
	*p = 12;
	cout << "Value of p is now " << p << " and p points to value " << *p << endl;
	// free up memory
	free(p);
	cout << "Value of p after freeing up memory is now " << *p << endl;
}