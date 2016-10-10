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
This holds the c++ code, the simulation core of the project. The simulator  model has been peer-reviewed and published in a scientific journal.
( free copy found at http://eprints.soton.ac.uk/73938/ )


gui/
This holds all the Java code related to the GUI - most of this work comes from OpenRocket and additional functions have been added to suit the format for camrocksim. Several extra functions have been removed, as camrocksim doesn't model these or they were simply redundant because of the modifications. The starting file is located at:
gui/swing/src/net/sf/openrocket/startup/SwingStartup.java


***

[NOTES]

- These two folders are essential to executing the program, to execute the program in eclipse, please run "prepare_linux.sh" included in this folder.

- if determined to develop the program in Windows, please consult SystemInfo.java on where the Installation/Application folder is located on the computer.

***

[[[BUILD INFORMATION]]]

[CPP - LINUX]

1) install Boost library (if required, this method is Ubuntu specific)
>> sudo apt-get install libboost-all-dev

2) compile binary in <main folder>/cpp/
>> make

[Python - LINUX]

1) install Python 2.7 (no compiling required)

[CPP - WINDOWS] via Visual Studio

1) install Visual Studio

2) install boost library (boost.org)

2) compile binary in <main folder>/cpp/ , by typing in the Developer Command Prompt (installed with Visual Studio)
>> cl /EHsc /I "<BOOST LIBRARY>" *.cpp

3) rename output file to rocketc.exe

[Python - Windows]

1) in <main folder>\Plotter
>> pyinstaller.exe --onefile --windowed FlightPlotter.py

[JAVA] - [LINUX & WINDOWS] via Eclipse

The JAR file is build via Eclipse (eclipse.org) by selecting

File -> Export, Java/Runnable JAR file

and input these data:
Launch configuration: SwingStartup - OpenRocket Swing
Export destination: <user choice>
Library handling: Extract required libraries into generated JAR

Finish

***

[TESTING - CPP]

1) prepare googletest environment
>> wget https://github.com/google/googletest/archive/release-1.8.0.tar.gz
>> tar -xvzf release-1.8.0.tar.gz
>> cd googletest-release-1.8.0/googletest
>> cmake .
>> make

2) build test binaries (from cpp directory)
>> make tests

3) run tests
>> ./runtests

[TESTING - JAVA]
