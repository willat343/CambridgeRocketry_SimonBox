Information on the folder structure



gui/core/Data/
Contains the information exchange, including atmospheres, simulation input and output. This file should be copied to the location of the compiled .JAR

gui/
This holds all the Java code related to the GUI - most of this work comes from OpenRocket and additional functions have been added to suit the format for camrocksim. Several extra functions have been removed, as camrocksim doesn't model these or they were simply redundant. The starting file is located at:
gui/swing/src/net/sf/openrocket/startup/SwingStartup.java

gui/core/Data/Plotter
This holds the Python code that provides the user with information. When writing an extension on the analysis or want to do anything with the data, this would be the best place to do so, leaving the Java and c++ code as it is.

cpp/
This holds the c++ code, the simulation core of the project. The simulator has been verified and tested, and the model has been peer-reviewed and published in a scientific journal. 
