## Copyright (C) 2010 S.Box
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
