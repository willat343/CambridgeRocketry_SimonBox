/*in this header file the bearing class is declared and so are functions for converting 
two element vectors to a bearing and vice versa*/
#ifndef vectorbearing_H
#define vectorbearing_H

//Headers*****************************************************
#include"vmaths.h"
#include<cmath>
using namespace std;
#ifndef PI
#define PI 3.14159265
#endif
//************************************************************

class vector2;// predeclared to avoid conflicts
//Bearing class definition************************************

class bearing{
public:
	double bea,range;
	bearing(){};
	bearing(double a,double b){
		bea=a; 
		range=b;
	}
	bearing(vector2);
	vector2 to_vector();
};
//************************************************************

#endif
