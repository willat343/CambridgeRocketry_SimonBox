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

%## HandleInputFile.h

%## Author: S.Box
%## Created: 2008-05-27
*/

//HandleInputfile.h
#ifndef HandleInputFile_H
#define HandleInputFile_H

#include <string>
#include <vector>
#include "vectorops.h"
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/foreach.hpp>
//#include <boost/filesystem.hpp>
#include <set>
#include <iostream>
#include "intabread.h"
#include "RocketFlight.h"

using namespace std;

//Class Definition****************
class HandleInputFile{
public:
	boost::property_tree::ptree PropTree;
        string FilePath;
        OutputData WriteData;

	//Construnctor
	HandleInputFile(string);
private:
	//Functions
	void DealWithOSF();
	void DealWithTSF();
	void DealWithOSM();
	void DealWithTSM();
	Rocket_Flight OneStageSetUp();
	Rocket_Flight TwoStageSetUp();
	Rocket_Flight TestMiscData(Rocket_Flight);
};

#endif
