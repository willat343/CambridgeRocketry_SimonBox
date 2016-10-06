Cambridge Rocketry Simulator v3
==========

Overview
--------

The Cambridge rocketry simulator can be used to produce six-degree-of-freedom simulations of rocket flights, including the parachute descent. Simulations can also be run stochastically, generating splash-down plots.

The software can be used to simulate many different types of rocket (including single and two-stage rockets) and many different scenarios (including uncertain launch- and atmospheric conditions).

License
-------

The Cambridge rocketry simulator is an Open Source project licensed under the [GNU GPL]. This means that the software is free to use for whatever purposes, and the source code is also available for studying and extending.

Contributing
------------

The Cambridge rocketry simulator needs users, developers, students and rocketeers to contribute, in a variety of different ways, and create an even better rocket flight simulator.


***

[FOLDER STRUCTURE]

Data/
Contains the information exchange, including atmospheres, simulation input and output.

simulator/
this folder contains the binary, to be compiled via running "make" in cpp/
"

Plotter/
This holds the Python code that provides the user with information. When writing an extension on the analysis or want to do anything with the data, this would be the best place to do so, leaving the Java and c++ code as it is.

cpp/
This holds the c++ code, the simulation core of the project. The simulator has been verified and tested, and the model has been peer-reviewed and published in a scientific journal.

gui/
This holds all the Java code related to the GUI - most of this work comes from OpenRocket and additional functions have been added to suit the format for camrocksim. Several extra functions have been removed, as camrocksim doesn't model these or they were simply redundant. The starting file is located at:
gui/swing/src/net/sf/openrocket/startup/SwingStartup.java

[note that there have been problems compiling the Java code using OpenJDK, even when using version 1.7+]

***

[NOTES]

- These two folders are essential to executing the program, to execute the program in eclipse, please run "prepare_linux.sh" included in this folder.

- if determined to develop the program in Windows, please consult SystemInfo.java on where the Installation/Application folder is located on the computer.

***

[[[BUILD INFORMATION]]]

[[LINUX]]

[GUI]

The JAR file is build via Eclipse (eclipse.org) by selecting

File -> Export, Java/Runnable JAR file

and input these data

Launch configuration: SwingStartup - OpenRocket Swing
Export destination: <user choice>
Library handling: Extract required libraries into generated JAR

Finish

[SIMULATOR CORE]

from the console, execute "make" in the cpp folder

this generates rocketc and moves it towards the /simulator folder

[PLOTTER]

no compiling required, as this is Python

[[WINDOWS]]
