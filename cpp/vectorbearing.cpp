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

%## vectorbearing.cpp

%## Author: S.Box
%## Created: 2008-05-27
*/

/*in this header file the bearing class is declared and so are functions for converting 
two element vectors to a bearing and vice versa*/

#include"vectorbearing.h"

//Functions***************************************************
//vector_to_bearing
bearing::bearing(vector2 vec){
	
	if (vec.e1>0)
		if(vec.e2>0)
			bea=((PI/2)-atan(vec.e2/vec.e1))*180/PI;
		else
			bea=((PI/2)-atan(vec.e2/vec.e1))*180/PI;
	else
		if(vec.e2>0)
			bea=((3*PI/2)-atan(vec.e2/vec.e1))*180/PI;
		else
			bea=((3*PI/2)-atan(vec.e2/vec.e1))*180/PI;

	range=sqrt(pow(vec.e1,2)+pow(vec.e2,2));

}

//bearing_to_vector
vector2 bearing::to_vector(){
	vector2 temp;
	temp.e1=range*sin(bea*PI/180);
	temp.e2=range*cos(bea*PI/180);
	return (temp);
}
