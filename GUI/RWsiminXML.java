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

%## ascent_variables.m

%## Author: S.Box
%## Created: 2010-05-27
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import java.text.DateFormat;
import org.w3c.dom.*;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author simon
 */
public class RWsiminXML extends RWXML{
    GenerateSimData SimDataStruct;

    //*Class constructor
    public RWsiminXML(String fn){
        super(fn);
        
    }

    public void WriteSimDataToXML(GenerateSimData gsd){
        SimDataStruct = gsd;
        WriteInit();
        
        Node RootNode = Doc.appendChild(CreateNode("SimulationInput"));
        RootNode.appendChild(CreateDataNode("Time",tStamp.DateTimeNow()));
        RootNode.appendChild(CreateDataNode("Function","OneStageFlight"));//TODO unhardcode this

        RootNode.appendChild(GenerateSimSettings());
        RootNode.appendChild(GenerateLaunchSettings());
        RootNode.appendChild(GenerateIntabTR());

        
        WriteOutput();

    }

    private Node GenerateSimSettings()
    {
        Node RootNode = Doc.createElement("SimulationSettings");
        RootNode.appendChild(CreateDataNode("BallisticFailure","true"));//TODO unhardcode this
        RootNode.appendChild(CreateDataNode("ShortData","true"));//TODO unhardcode this
        RootNode.appendChild(CreateDataNode("NumberOfIterations","1"));//TODO unhardcode this
        RootNode.appendChild(CreateDataNode("MaxTimeSpan","2000"));//TODO unhardcode this
        return(RootNode);
    }

    private Node GenerateLaunchSettings()
    {
        Node RootNode = Doc.createElement("LaunchSettings");
        RootNode.appendChild(CreateDataNode("Eastings","0.0"));//TODO unhardcode this
        RootNode.appendChild(CreateDataNode("Northings","0.0"));//TODO unhardcode this
        RootNode.appendChild(CreateDataNode("Altitude","0.0"));//TODO unhardcode this
        RootNode.appendChild(CreateDataNode("LaunchRailLength",Double.toString(SimDataStruct.RailLength)));
        RootNode.appendChild(CreateDataNode("LaunchAzimuth",Double.toString(SimDataStruct.Azimuth)));
        RootNode.appendChild(CreateDataNode("LaunchDeclination",Double.toString(SimDataStruct.Declination)));

        return(RootNode);

    }

    private Node GenerateIntabTR()
    {
        Node RootNode = Doc.createElement("INTAB_TR");
        Node intab1N = RootNode.appendChild(CreateNode("intab1"));
        intab1N.appendChild(CreateDataNode("time",VectorToDstring(SimDataStruct.Time) + ";"));
        intab1N.appendChild(CreateDataNode("Thrust",VectorToDstring(SimDataStruct.Thrust) + ";"));
        intab1N.appendChild(CreateDataNode("Mass",VectorToDstring(SimDataStruct.Mass) + ";"));
        intab1N.appendChild(CreateDataNode("Ixx",VectorToDstring(SimDataStruct.Ixx) + ";"));
        intab1N.appendChild(CreateDataNode("Iyy",VectorToDstring(SimDataStruct.Iyy) + ";"));
        intab1N.appendChild(CreateDataNode("Izz",VectorToDstring(SimDataStruct.Izz) + ";"));

        Vector<Double> ZeroVec = new Vector<Double>();
        for(double d : SimDataStruct.Ixx){
            ZeroVec.add(0.0);
        }
        intab1N.appendChild(CreateDataNode("Ixy",VectorToDstring(ZeroVec) + ";"));
        intab1N.appendChild(CreateDataNode("Ixz",VectorToDstring(ZeroVec) + ";"));
        intab1N.appendChild(CreateDataNode("Iyz",VectorToDstring(ZeroVec) + ";"));
        intab1N.appendChild(CreateDataNode("CentreOfMass",VectorToDstring(SimDataStruct.CoM) + ";"));
        intab1N.appendChild(CreateDataNode("ThrustDampingCoefficient",VectorToDstring(SimDataStruct.TDC) + ";"));

        Node intab2N = RootNode.appendChild(CreateNode("intab2"));
        intab2N.appendChild(CreateDataNode("AngleOfAttack",VectorToDstring(SimDataStruct.AoA) + ";"));
        intab2N.appendChild(CreateDataNode("ReynoldsNumber",VectorToDstring(SimDataStruct.Re) + ";"));
        intab2N.appendChild(CreateDataNode("CD",MatrixToDstring(SimDataStruct.CD)));

        Node intab3N = RootNode.appendChild(CreateNode("intab3"));
        intab3N.appendChild(CreateDataNode("CN",VectorToDstring(SimDataStruct.Cn)));
        intab3N.appendChild(CreateDataNode("CentreOfPressure",VectorToDstring(SimDataStruct.Cp)));

        Node intab4N = RootNode.appendChild(CreateNode("intab4"));
        intab4N.appendChild(CreateDataNode("Altitude",VectorToDstring(SimDataStruct.Altitude) + ";"));
        intab4N.appendChild(CreateDataNode("Altitude",VectorToDstring(SimDataStruct.Altitude) + ";"));
        intab4N.appendChild(CreateDataNode("XWind",VectorToDstring(SimDataStruct.Xwind) + ";"));
        intab4N.appendChild(CreateDataNode("YWind",VectorToDstring(SimDataStruct.Ywind) + ";"));
        intab4N.appendChild(CreateDataNode("ZWind",VectorToDstring(SimDataStruct.Zwind) + ";"));
        intab4N.appendChild(CreateDataNode("AtmosphericDensity",VectorToDstring(SimDataStruct.rho) + ";"));
        intab4N.appendChild(CreateDataNode("AtmosphericTemperature",VectorToDstring(SimDataStruct.Theta) + ";"));

        Node LandA = RootNode.appendChild(CreateNode("LengthAndArea"));
        LandA.appendChild(CreateDataNode("RocketLength",Double.toString(SimDataStruct.Length)));
        LandA.appendChild(CreateDataNode("RocketXsectionArea",Double.toString(SimDataStruct.Xarea)));

        Node Parachute = RootNode.appendChild(CreateNode("ParachuteData"));
        Parachute.appendChild(CreateDataNode("ParachuteSwitchAltitude",VectorToDstring(SimDataStruct.SwitchAlt) + ";"));
        Parachute.appendChild(CreateDataNode("ParachuteCDxA", VectorToDstring(SimDataStruct.CDA) + ";"));

        return(RootNode);

    }



}
