#include "iostream"
using namespace std;

class test {
private:
	int x = 10;
public:
	void setX(int x) {
		this->x = x; // local variable x which holds value 10 is overriden by 'this' object reference.
	}
	void print() {
		cout << "X value is " << x << endl;
	}
};

int main() {
	test obj;
	int x = 15;
	obj.setX(x);
	obj.print();
	return 0;
}