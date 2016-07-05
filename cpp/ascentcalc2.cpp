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

%## ascentcalc2.cpp

%## Author: S.Box
%## Created: 2008-05-27
*/

#include "ascentcalc.h"

//functions for extracting Rocket flight data

FlightDataShort ascent::getShortData(void)
{
	FlightDataShort tempdat;
	try{
		if (DatPop != true){throw 20;}

		tempdat.time=RKd1.t;

		// store last time only as event
		double end_time = RKd1.t.back();
		//cout << end_time << endl;
		tempdat.events.push_back( end_time );

		vector<vector<double> >::iterator z_it;
		for (z_it=RKd1.z.begin(); z_it!=RKd1.z.end(); z_it++)
		{
			vector<double> B = *z_it;
			vector3 A(B[0],B[1],B[2]);
			tempdat.X.push_back(A);
		}
	}
	catch (int ErrorCode){
		cout<<"Error "<<ErrorCode<<": Attempt to populate flight data before the simulation has been run"<<endl;
	}

	return(tempdat);
}

FlightDataLong ascent::getLongData(void)
{
	FlightDataLong tempdat;

	try{
		if(DatPop != true){throw 20;}

		tempdat.time=RKd1.t;

		// store last time only as event
		double end_time = RKd1.t.back();
		//cout << end_time << endl;
		tempdat.events.push_back( end_time );


		vector<vector<double> >::iterator z_it;
		vector<double>::iterator t_it;
		t_it=tempdat.time.begin();
		for (z_it=RKd1.z.begin(); z_it!=RKd1.z.end(); z_it++)
		{
			vector<double> z = *z_it;
			double tt = *t_it;

			EqMotionData EMD = SolveEqMotion(tt,z);
			
			//Outputs*************************************************
			tempdat.alpha.push_back(EMD.alpha);
			tempdat.Thrust.push_back(EMD.Thrust);
			tempdat.Mass.push_back(EMD.Mass);
			tempdat.CofM.push_back(EMD.CofM);
			tempdat.aDensity.push_back(EMD.aDensity);
			tempdat.aTemp.push_back(EMD.aTemp);
			tempdat.X.push_back(EMD.X);
			tempdat.Raxis.push_back(EMD.Raxis);
			tempdat.Xdot.push_back(EMD.Xdot);
			tempdat.Thetadot.push_back(EMD.Thetadot);
			tempdat.Xddot.push_back(EMD.Xddot);
			tempdat.Thetaddot.push_back(EMD.Thetaddot);
			tempdat.Force.push_back(EMD.Force);
			tempdat.Torque.push_back(EMD.Torque);
			tempdat.Wind.push_back(EMD.Wind);
			tempdat.Inertia.push_back(EMD.Inertia);
			//****************************************************
			
			  t_it++;
			  
			
			}
			}
			catch (int ErrorCode){
				cout<<"Error "<<ErrorCode<<": Attempt to populate flight data before the simulation has been run"<<endl;
			}


		return(tempdat);

}

