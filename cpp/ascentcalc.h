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

%## ascentcalc.h

%## Author: S.Box
%## Created: 2008-05-27
*/

//ascentcalc.h
#ifndef ascentcalc_H
#define ascentcalc_H

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

class EqMotionData{
public:
	double
		alpha,
		Thrust,
		Mass,
		CofM,
		aDensity,
		aTemp;
	vector3
		Raxis,
		X,
		Xdot,
		Thetadot,
		Xddot,
		Thetaddot,
		Force,
		Torque,
		Wind;
	matrix3x3
		Inertia;
	quaternion
		Qdot;
	EqMotionData(){};
};



class KillSwitch{
public:
	int index;
	double Kval;
	KillSwitch(){};
	KillSwitch(int a, double b){
		index=a;
		Kval=b;
	};
};

class ascent{
public:
	vector<double>
		tt,
		z0;
	INTAB1 
		intab1;
	INTAB2
		intab2;
	INTAB3
		intab3;
	INTAB4
		intab4;
	double
		RBL,
		Ar,
		RL;
	vector3
		X0;
	KillSwitch
		Kill;
	
	bool DatPop;
	RKF_data RKd1;

	ascent(){DatPop=false;};
	ascent(vector<double>,vector<double>,INTAB,double);
	RKF_data fly(void);
	EqMotionData SolveEqMotion(double, vector<double>);
	FlightDataShort getShortData(void);
	FlightDataLong getLongData(void);
};

class blastoff:public integrator, public RKF, public ascent{
public:
	blastoff(){};
	blastoff(INTAB1,INTAB2,INTAB3,INTAB4,double,double,double,vector3,KillSwitch);
	vector<double> step(double, vector<double>);
	bool stop_flag(double,vector<double>);
};


#endif
