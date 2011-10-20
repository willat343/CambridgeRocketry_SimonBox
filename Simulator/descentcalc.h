//descentcalc.h
#ifndef descentcalc_H
#define descentcalc_H

//Headers*********************************************
#include<iostream>
#include<cmath>
#include<vector>
#include"vectorops.h"
#include"interpolation.h"
#include"RKF45.h"
#include"vmaths.h"
#include"intabread.h"
#include"FlightData.h"
using namespace std;
//****************************************************

class EqMotionData2{
public:
	double
		Mass,
		aDensity,
		aTemp;
	vector3
		X,
		Xdot,
		Xddot,
		Force,
		Wind;
	EqMotionData2(){};
};


class descent{
public:
	vector<double>
		tt,
		z0;
	INTAB4
		intab4;
	INTAB1 
		intab1;
	ParaTab
		paratab;

	bool DatPop;
	RKF_data RKd1;

	descent(){DatPop=false;};
	descent(vector<double>,vector<double>,INTAB);
	RKF_data fall(void);
	EqMotionData2 EqMotionSolve(double, vector<double>);
	FlightDataShort getShortData(void);
	FlightDataLong getLongData(void);
};

class floatdown:public integrator, public RKF, public descent{
public:
	floatdown(){};
	floatdown(INTAB4,INTAB1,ParaTab);
	vector<double> step(double, vector<double>);
	bool stop_flag(double,vector<double>);
};

#endif