/*
%## Copyright (C) 2008 S.Box
%## 
%## This program is free software; you can redistribute it and/or modify
%## it under the terms of the GNU General Public License as published by
%## the Free Software Foundation; either version 2 of the License, or
%## (at your option) any later version.
%## 
%## This program is distributed in the hope that it will be useful,
%## but WITHOUT ANY WARRANTY; without even the implied warranty of
%## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%## GNU General Public License for more details.
%## 
%## You should have received a copy of the GNU General Public License
%## along with this program; if not, write to the Free Software
%## Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

%## RocketFlight.h

%## Author: S.Box
%## Created: 2008-05-27
*/

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
		IntabTR, // first stage
		IntabBS, // booster stage
		IntabUS; // second stage
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
	//void StageTransfer(vector<double>*,vector<double>*,vector<double>*,RKF_data);
	//void ParachuteTransfer(vector<double>*,vector<double>*,RKF_data);
	void TimeTransfer(vector<double>* ttp, RKF_data Stage);
	void StateTransferRocket(vector<double>* zp, RKF_data Stage, INTAB IntabPrevious, INTAB IntabNext);
	void StateTransferParachute(vector<double>* zp, RKF_data Stage);
	void BallisticSwitch(ascent *);
    void setFilePath(string path);
    double getDeploymentAltitude(INTAB thisINTAB);
};

#endif


