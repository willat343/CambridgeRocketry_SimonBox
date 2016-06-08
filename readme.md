Information on the folder structure

***

Data/
Contains the information exchange, including atmospheres, simulation input and output. This file should be copied to the location of the compiled .JAR

simulator/
this folder contains the binary, to be compiled via running "make" in cpp/
"
Plotter/
This holds the Python code that provides the user with information. When writing an extension on the analysis or want to do anything with the data, this would be the best place to do so, leaving the Java and c++ code as it is.

***

- These two folders are essential to executing the program, to execute the program in eclipse, please run "prepare_linux.sh" included in this folder.
- if determined to develop the program in Windows, please consult SystemInfo.java on where the Installation/Application folder is located on the computer.

cpp/
This holds the c++ code, the simulation core of the project. The simulator has been verified and tested, and the model has been peer-reviewed and published in a scientific journal. 

gui/
This holds all the Java code related to the GUI - most of this work comes from OpenRocket and additional functions have been added to suit the format for camrocksim. Several extra functions have been removed, as camrocksim doesn't model these or they were simply redundant. The starting file is located at:
gui/swing/src/net/sf/openrocket/startup/SwingStartup.java

[note that there have been problems compiling the Java code using OpenJDK, even when using version 1.7+

