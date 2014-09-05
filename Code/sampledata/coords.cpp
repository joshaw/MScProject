// Filename: gen-coords.cpp
// Compile: g++ gen-coords.cpp -o gen-coords
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <stdlib.h>

using namespace std;

int main(int argc, char* argv[]) {

	if (argc != 2) {
		cout << "Usage: " << argv[0] << " FILENAME" << endl;
		exit (EXIT_FAILURE);
	}

	string filename = argv[1];
	ifstream scores(filename.c_str());

	string line = "";

	// For each line, for each tab separated field, output coord if 0.
	int y = 0;
	while(getline(scores, line)) {

		string delimiter = "\t";
		size_t pos = 0;
		string token;
		int x = 0;
		while ((pos = line.find(delimiter)) != string::npos) {

			token = line.substr(0, pos);
			if (token == "0") {
				cout << x << "\t" << y << endl;
			}
			x++;
			line.erase(0, pos + delimiter.length());
		}

		y++;
	}
}
