//RocketFlight.h
#ifndef RocketFlight_H
#define RocketFlight_H

#include"ascentcalc.h"
#include"descentcalc.h"
#include<string>
#include<cmath>
#include"MonteFy.h"

#ifndef PI
#define PI 3.14159265
#endif

class Rocket_Flight{
public:
	INTAB
		IntabTR,
		IntabBS,
		IntabUS;
	vector<double>
		tt,
		z0;
	vector3
		X0;
	double
		RL,
		Az,
		De,
		Tspan,
		Tsep,
		IgDelay;
	bool
		ballisticfailure,
		ShortData;
        string FilePath;
	Rocket_Flight();
	Rocket_Flight(INTAB);
	Rocket_Flight(INTAB,INTAB,INTAB,double,double);
	Rocket_Flight(string);
	void InitialConditionsCalc(void);
	OutputData OneStageFlight(void);
	OutputData TwoStageFlight(void);
	OutputData OneStageMonte(int);
	OutputData TwoStageMonte(int);
	void StageTransfer(vector<double>*,vector<double>*,vector<double>*,RKF_data);
	void ParachuteTransfer(vector<double>*,vector<double>*,RKF_data);
	void BallisticSwitch(ascent *);
        void setFilePath(string path);
};

#endif


