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

%## SimulatorInterface.java

%## Author: S.Box
%## Created: 2011-10-27

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rocket;

import java.io.*;

/**
 *
 * @author sb4p07
 */
public class SimulatorInterface {
    ///**class members
    File SimPath;
    File SimulationOut;
    File SimulationIn;
    File MultiSimOut;
    File TheSimulator;
    PlotLauncher ThrowFigs;
    public boolean Success;

    //constructor
    public SimulatorInterface(String Path, PlotLauncher tF){
        Success = false;
        ThrowFigs = tF;
        SimPath = new File(Path);
        SimulationOut = genFile("SimulationOutput.xml");
        MultiSimOut = genFile("MultiSimOut.xml");
        SimulationIn = genFile("SimulationInput.xml");
        TheSimulator = genFile("rocketc");
        if (!TheSimulator.exists()){
            TheSimulator = genFile("rocketc.exe");
        }
    }

    public void RunSim(RocketDescription TheRocket, AtmosphereData TheAtmos, LaunchData LaunchPadSet, boolean Monte, boolean Para, int iterations){
        SimRunningInfo Window = new SimRunningInfo(null,true);
        if(Monte){
            RWsiminXML WriteSim = new RWsiminXML(SimulationIn.getAbsolutePath());
            WriteSim.WriteSimDataToXML(TheRocket, TheAtmos, LaunchPadSet, Monte, iterations, Para);
            Window.Fire(this);
            if(Window.Success){
                Success=true;
                MoveOutputFile();

                WriteSim.WriteSimDataToXML(TheRocket, TheAtmos, LaunchPadSet, false, iterations, Para);
                Window.Fire(this);
                ThrowFigs.MakePlots(MultiSimOut);
            }

        }
        else{
            RWsiminXML WriteSim = new RWsiminXML(SimulationIn.getAbsolutePath());
            WriteSim.WriteSimDataToXML(TheRocket, TheAtmos, LaunchPadSet, Monte, iterations, Para);
            Window.Fire(this);
            if(Window.Success){
                Success = true;
                ThrowFigs.MakePlots(SimulationOut);
            }
        }


    }

    private File genFile(String fName){
        return(new File(SimPath.getAbsolutePath() + File.separator + fName));

    }

    private void MoveOutputFile(){
        try{
            FileReader in = new FileReader(SimulationOut);
            FileWriter out = new FileWriter(MultiSimOut);
            int c;
            while ((c = in.read()) != -1){
              out.write(c);
            }
            in.close();
            out.close();
        }
        catch(Exception e){

        }
    }


}
