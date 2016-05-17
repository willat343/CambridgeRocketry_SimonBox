## Copyright (C) 2010 S.Box
## 
## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program; if not, write to the Free Software
## Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

## Decider.py

## Author: S.Box
## Created: 2011-10-27


# To change this template, choose Tools | Templates
# and open the template in the editor.

__author__="SIMON"
__date__ ="$18-Jan-2010 13:10:01$"

import xml.dom.minidom as md
import Rdata as Rd
import PlotBase as Pb

class Decider:

    def __init__(self,Filename):
        self.Filename = Filename
        DOM = md.parse(Filename)
        Fnode = DOM.getElementsByTagName("Function")[0]
        self.Function = Fnode.firstChild.data
        

    def FlightPlots(self):
        {
        "OneStageFlight":self.OSFProcess,
        "TwoStageFlight":self.TSFProcess,
        "OneStageMonte":self.OSMProcess,
        "TwoStageMonte":self.TSMProcess
        }[self.Function]()



    def OSFProcess(self):
        Data = Rd.RdataOSF(self.Filename)
        Plotter = Pb.PlotBase()
        Fig = Plotter.NewFigWin()
        Plotter.OSFlightPlot(Fig,Data)
        Plotter.LaunchFigs()

    def TSFProcess(self):
        Data = Rd.RdataTSF(self.Filename)
        Plotter = Pb.PlotBase()
        Fig = Plotter.NewFigWin()
        Plotter.TSFlightPlot(Fig,Data)
        Plotter.LaunchFigs()

    def OSMProcess(self):
        Data = Rd.RdataOSM(self.Filename)
        Plotter = Pb.PlotBase()

        Fig1 = Plotter.NewFigWin()
        Plotter.OSMFlightPlot(Fig1,Data)

        Fig2 = Plotter.NewFigWin()
        Plotter.OSSplashPlot(Fig2,Data)

        Fig3 = Plotter.NewFigWin()
        Plotter.OSMCluster(Fig3,Data)
        Plotter.LaunchFigs()


    def TSMProcess(self):
        Data = Rd.RdataTSM(self.Filename)
        Plotter = Pb.PlotBase()

        Fig1 = Plotter.NewFigWin()
        Plotter.TSMFlightPlot(Fig1,Data)

        Fig2 = Plotter.NewFigWin()
        Plotter.TSSplashPlot(Fig2,Data)

        Fig3 = Plotter.NewFigWin()
        Plotter.TSMCluster(Fig3,Data)
        Plotter.LaunchFigs()



class LaunchUDataFig:
    def __init__(self,FileName,Label1,Label2):
        RD = Rd.Rdata(FileName)
        LabArr1 = Label1.split(',')
        LabArr2 = Label2.split(',')

        PB = Pb.PlotBase()
        Fig = PB.NewFigWin()

        i=0
        for Lab1 in LabArr1:
            Lab2 = LabArr2[i]
            if Lab2 != "":
                AxD = RD.UserPlotData(Lab1,Lab2)
                PB.DataPlot(Fig,AxD[0],Lab1,AxD[1],Lab2)
                i=i+1
        PB.LaunchFigs()

