// Demonstration of usage of basic constructer

#include "iostream"
using namespace std;

class test {
private:
	int x, y;
public:
	test(int x, int y) {
		this->x = x;
		this->y = y;
	}

	void getX() {
		cout << "x is " << x << endl;
	}

	void getY() {
		cout << "y is " << y << endl;
	}
};

int main() {
	test *t1 = new test(3, 3);
	t1->getX();
	t1->getY();
	return 0;
}