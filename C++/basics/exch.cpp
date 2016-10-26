#include<iostream>
using namespace std;

void swap(int& a, int& b) {
	int temp  = a;
	a = b;
	b = temp;
}

void swapstr(char * &str1, char * &str2)
{
  char *temp = str1; // temp now points to value of str1
  str1 = str2;
  str2 = *temp;
}
 
int main() {
	int a = 2, b = 3;
	swap(a, b);
	cout << a << " " << b << endl;

	char *str1 = "Hello";
  	char *str2 = "World";
  	swapstr(str1, str2);
  	cout << "str1 is " << str1 << endl;
  	cout << "str2 is " << str2 << endl;

	return 0;
}

/* Demo of reference variables (less powerful than pointers)
------------------------------------------------------------
1. References don’t need dereferencing operator to access the value. 
They can be used like normal variables. ‘&’ operator is needed only at the time of declaration. Also, members of an object reference can be accessed with dot operator (‘.’), unlike pointers where arrow operator (->) is needed to access members.

3. Reference or pointer is also used for passing large sized arguments to functions because they only point to a memory location not the actual value. The value can be then changed by the called fucntion easily.

2. References are also used to modify variable of caller function. In this case, local variables of main() are modified by the function it called.
*/