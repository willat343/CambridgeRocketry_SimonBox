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

## FlightPotter.py

## Author: S.Box
## Created: 2011-10-27

import getopt
import Decider
import sys


def main(argv,dir):
    try:
        opts, args = getopt.getopt(argv, "f:x:y:", ["file=","Xaxis=","Yaxis"])
    except getopt.GetoptError:
        print("Incorrect specification of command line arguments")
        sys.exit(2)

   
    FileName = "/usr/local/CambridgeRocketrySimulator/data/SimulationOutput.cro"
    Xlb = ""
    Ylb = ""

    for opt, arg in opts:
        if opt in ("-f", "--file"):
            FileName = arg
        elif opt in ("-x", "--Xaxis"):
            Xlb = arg
        elif opt in ("-y", "--Yaxis"):
            Ylb = arg

    if (Xlb != "" and Ylb != ""):
        Decider.LaunchUDataFig(FileName,Xlb,Ylb)
    else:
        D = Decider.Decider(FileName)
        D.FlightPlots()




if __name__ == "__main__":
    main(sys.argv[1:],sys.argv[0])
