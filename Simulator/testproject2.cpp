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

%## testproject2.cpp

%## Author: S.Box
%## Created: 2008-05-27
*/

#include<iostream>
//#include"vmaths.h"
//#include<cmath>
//#include"intabread.h"
#include"ascentcalc.h"
#include"descentcalc.h"
#include"RocketFlight.h"
#include"MonteFy.h"
#include"HandleInputFile.h"


using namespace std;


int main(int argc, char* argv[]){

    string filename;
	if (argc ==1){
		filename = "./SimulationInput.xml";
	}
	else if (argc ==2){
		filename = argv[1];
	}
	else{
		cout<<"Unrecognized command line options"<<endl;
	}
	
	cout<<filename<<endl;
	HandleInputFile HI(filename);
	return(0);
}

