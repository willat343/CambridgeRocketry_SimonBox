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

%## vectorops.h

%## Author: S.Box
%## Created: 2008-05-27
*/

/*Functions for basic scalar and vector operations (+,-,*,/)
for the "vector" class in the standard c++ library*/

#ifndef vectorops_H
#define vectorops_H

//Headers******************************************
#include<iostream>
#include<vector>
using namespace std;
//*************************************************

class vecop{
public:

	vecop(){};

	static vector<double> scaladd(vector<double>,double);

	static vector<double> scalsub(vector<double>,double);

	static vector<double> scalmult(vector<double>,double);

	static vector<double> scaldiv(vector<double>,double);

	static vector<double> vecadd(vector<double>,vector<double>);

	static vector<double> vecsub(vector<double>,vector<double>);

	static vector<double> vecmult(vector<double>,vector<double>);

	static vector<double> vecdiv(vector<double>,vector<double>);
};

#endif
