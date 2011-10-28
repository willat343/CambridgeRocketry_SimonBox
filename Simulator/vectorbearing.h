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

%## vectorbearing.h

%## Author: S.Box
%## Created: 2008-05-27
*/

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
