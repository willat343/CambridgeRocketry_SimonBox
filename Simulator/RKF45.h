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

%## RKF45.h

%## Author: S.Box
%## Created: 2008-05-27
*/

/*Runge Kutta Fehlberg  4th/5th order numerical integration algorithm for systems of
ordinary differential equations */

#ifndef RKF45_H
#define RKF45_H

#include<vector>
#include"vectorops.h"
#include<cmath>
#include<algorithm>
using namespace std;


class RKF_data{
public:
	int num;
	vector<double> t;
	vector<vector<double> > z;
	vector<vector<double> > e;
	RKF_data(){};
	RKF_data(int a,vector<double> b,vector<vector<double> > c,vector<vector<double> > d){
		num=a;
		t=b;
		z=c;
		e=d;
	}
};

class integrator{
public:
	virtual vector<double> step (double,vector<double>)=0;
	virtual bool stop_flag(double t,vector<double> z){return(false);}//optional stop flag can be called in the step function
};

class RKF{
public:
	double Retol;//default relative error tolerance
	double Abtol;//default absolute error tolerance
	double h_init;//default initial step
	int max_it;//default maxixmum number of iterations
	double max_step;//default maximum step size
	RKF(){
		Retol=1.0e-3;
		Abtol=1.0e-6;
		h_init=0.01;
		max_it=1000;
		max_step=10.0;
	};
	RKF_data RKF45(vector<double>, vector<double>, integrator*);
};


#endif
