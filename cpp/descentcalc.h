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

%## descentcalc.h

%## Author: S.Box
%## Created: 2008-05-27
*/
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
