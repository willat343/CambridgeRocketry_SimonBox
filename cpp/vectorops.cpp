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

%## vectorops.cpp

%## Author: S.Box
%## Created: 2008-05-27
*/

/*Functions for basic scalar and vector operations (+,-,*,/)
for the "vector" class in the standard c++ library*/

#include"vectorops.h"

vector<double> vecop::scaladd(vector<double> vec,double S){
	int num=vec.size();
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec[i]+S;
	return(out);
}

vector<double> vecop::scalsub(vector<double> vec,double S){
	int num=vec.size();
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec[i]-S;
	return(out);
}

vector<double> vecop::scalmult(vector<double> vec,double S){
	int num=vec.size();
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec[i]*S;
	return(out);
}
vector<double> vecop::scaldiv(vector<double> vec,double S){
	int num=vec.size();
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec[i]/S;
	return(out);
}

vector<double> vecop::vecadd(vector<double> vec1,vector<double> vec2){
	int num=vec1.size();
	int num2=vec2.size();
	if(!num==num2)
		cout<<"Error vector sizes don't match\n";
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec1[i]+vec2[i];
	return(out);
}

vector<double> vecop::vecsub(vector<double> vec1,vector<double> vec2){
	int num=vec1.size();
	int num2=vec2.size();
	if(!num==num2)
		cout<<"Error vector sizes don't match\n";
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec1[i]-vec2[i];
	return(out);
}

vector<double> vecop::vecmult(vector<double> vec1,vector<double> vec2){
	int num=vec1.size();
	int num2=vec2.size();
	if(!num==num2)
		cout<<"Error vector sizes don't match\n";
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec1[i]*vec2[i];
	return(out);
}

vector<double> vecop::vecdiv(vector<double> vec1,vector<double> vec2){
	int num=vec1.size();
	int num2=vec2.size();
	if(!num==num2)
		cout<<"Error vector sizes don't match\n";
	vector<double> out(num);
	for (int i=0;i<num;i++)
		out[i]=vec1[i]/vec2[i];
	return(out);
}

