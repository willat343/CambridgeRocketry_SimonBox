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

%## FlightData.h.cpp

%## Author: S.Box
%## Created: 2008-05-27
*/
//FlightData.h

#ifndef FlightData_H
#define FlightData_H

//Headers*********************************************
#include<vector>
#include<string>
#include<sstream>
#include<time.h>
#include"vectorops.h"
#include"vmaths.h"
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/foreach.hpp>

using namespace std;
//****************************************************

class FlightData{
public:
	//Class members
	vector<double> time;
	vector<double> events;
	vector<vector3> X;

	//Constructor
	FlightData(){};

	//Class Functions
	virtual boost::property_tree::ptree BuildPropertyTree(int)=0;
	void ApogeeLanding(vector<double> *,vector<double> *,double *);
	void TreeIfy(boost::property_tree::ptree *,vector<double>,string);
	void TreeIfy(boost::property_tree::ptree *,vector<vector3>,string);
};



class FlightDataShort:public FlightData{
public:
	FlightDataShort(){};
	FlightDataShort operator + (FlightDataShort);
	boost::property_tree::ptree BuildPropertyTree(int);
};

class FlightDataLong:public FlightData{
public:
	vector<double> 
		alpha,
		Thrust,
		Mass,
		CofM,
		aDensity,
		aTemp;
	vector<vector3>
		Raxis,
		Xdot,
		Thetadot,
		Xddot,
		Thetaddot,
		Force,
		Torque,
		Wind;
	vector<matrix3x3>
		Inertia;
	FlightDataLong(){};
	FlightDataLong operator + (FlightDataLong);
	boost::property_tree::ptree BuildPropertyTree(int);
	void SpeedAndG(double *,double *);
};

class OutputData{
public:
	boost::property_tree::ptree PropTree;

	//Constructors
	OutputData(){};
	
	//Functions
	void InitializePropertyTree(string);
	void FillPropertyTree(FlightData *,int);
	void FillPropertyTree(vector<FlightData *>,int);
	void WriteToXML(string);
};


#endif
