/*
%## Copyright (C) 2011 S.Box
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

%## RWsimOutXML.java

%## Author: S.Box
%## Created: 2011-10-27
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;
import java.util.Vector;
import org.w3c.dom.*;

/**
 *
 * @author simon
 */
public class RWsimOutXML extends RWXML {
    Vector<SimulationOutputData> DataList = new Vector<SimulationOutputData>();

    RWsimOutXML(String fn){
        super(fn);
        
    }
    
    public void ReadXMLtoSimOdata(){
        ReadInit();

        NodeList Runs = Doc.getElementsByTagName("Run");

        Element FirstRun = (Element)Runs.item(0);

        Vector<Element> ElList = new Vector<Element>();
        
        ElList.add((Element)FirstRun.getElementsByTagName("FlightData").item(0));

        NodeList Stages = FirstRun.getElementsByTagName("LowerStage");
        if(Stages.getLength() != 0){
                ElList.clear();

                Element MainStage =(Element)FirstRun.getElementsByTagName("UpperStage").item(0);
                ElList.add((Element)MainStage.getElementsByTagName("FlightData").item(0));

                Element BoosterStage =(Element)FirstRun.getElementsByTagName("LowerStage").item(0);
                ElList.add((Element)BoosterStage.getElementsByTagName("FlightData").item(0));
        }
        
        for(Element Dat : ElList){
            SimulationOutputData SOD = new SimulationOutputData();

            SOD.mTime = MatDbyName(Dat,"Time");
            SOD.mAoA = MatDbyName(Dat,"AngleOfAttack");
            SOD.mThrust = MatDbyName(Dat,"Thrust");
            SOD.mMass = MatDbyName(Dat,"Mass");
            SOD.mCom = MatDbyName(Dat,"CentreOfMass");
            SOD.mDensity = MatDbyName(Dat,"AtmosphericDensity");
            SOD.mTemp = MatDbyName(Dat,"AtmosphericTemperature");
            SOD.mPosition = MatDbyName(Dat,"Position");
            SOD.mVelocity = MatDbyName(Dat,"Velocity");
            SOD.mAccel = MatDbyName(Dat,"Accelleration");
            SOD.mPose = MatDbyName(Dat,"Pose");
            SOD.mAngV = MatDbyName(Dat,"AngularVelocity");
            SOD.mAndA = MatDbyName(Dat,"AngularAccelleration");
            SOD.mForce = MatDbyName(Dat,"Force");
            SOD.mTorque = MatDbyName(Dat,"Torque");
            SOD.mWind = MatDbyName(Dat,"Wind");
            
            DataList.add(SOD);
        }
    }



    //function to return Vector<double> under Doc given a name
    protected Vector<Vector<Double> > MatDbyName(Element e, String name)
    {
        return(DstringToMatrix(e.getElementsByTagName(name).item(0).getTextContent()));
    }


    protected Vector<Vector<Double> > DstringToMatrix(String Dstring)
    {
        String[] SuperSplitString = Dstring.split(";");

        Vector<Vector<Double> > Temp = new Vector<Vector<Double> >();

        for(String S : SuperSplitString)
        {
            Vector<Double> miniTemp = new Vector<Double>();
            String[] SplitString = S.split(",");
            for(String sS : SplitString){
                miniTemp.add(Double.parseDouble(sS));
            }
            Temp.add(miniTemp);
        }
        return(Temp);
    }



}
