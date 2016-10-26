// Demo To return reference to the calling object using this

#include "iostream"
using namespace std;

class test {
private:
	int x, y;
public:
	test(int x = 0, int y = 0) { // initialise when obj(5, 5) called from main()
		this->x = x;
		this->y = y;
	}
	test &setX (int x) {
		this->x = x;
		return *this; // returns current object pointer which is set as 10 by obj.setX(10) in main()
	}
	test &setY (int y) {
		this->y = y;
		return *this; // returns current object pointer which is set as 10 by obj.setY(13) in main()
	}
	void print() {
		cout << "x -> " << x << " y -> " << y << endl;
	}
};

int main() {
	test obj(5, 5);
	obj.print();
	obj.setX(10).setY(13);
	obj.print();
	return 0;
}
