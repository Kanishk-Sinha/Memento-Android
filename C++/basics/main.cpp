// Demonstrate overloading of main()

#include<iostream>
using namespace std;

class test {
	public :

		int main(int s) {
			cout << "value of s " << s << endl;
			return 0;
		}

		int main(char *s) {
			cout << "value of s " << s << endl;
			return 0;
		}

		int main(int s, int p) {
			cout << "value of s " << s << " and value of p is " << p << endl;
			return 0;
		}
};

int main() {
	test obj;
	obj.main(1);
	obj.main("hello world");
	obj.main(7, 6);
	return 0;
}
