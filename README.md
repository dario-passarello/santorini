# Santorini
## Software engineering final project
Software Engineering Project 2020: GC24 - Passarello, Parisi, Rahel
##Implemented Features
- CLI
- GUI (java FX)
- Multiplayer Server-Client communicating via a TCP socket
- Normal ruleset
- Advanced Feature: Advanced Gods (Chronus,Hestia,Hera,Triton,Zeus)
- Advanced Feature: Multiple simultaneous games supported
## Unit Test Coverage Data
Line Coverage:
- package Controller: 100%
- package Model: 97%
## Installation (build from Maven)
If you use the ready to use jar in the /deliverables folder you can skip to the step 3.   
### 0. Requirements
- Java JDK >= 11
- A Maven version compatible with your installed JDK
### 1. Clone the repository
If you have git installed in your computer you can use this command in a new folder 
```
git clone https://github.com/dario-passarello/ing-sw-2020-parisi-passarello-rahel.git
```
You can also download the .zip of the repository directly from github and then unpack it 
in a new folder
### 2. Build the .jar executable
Use the command on the main folder (the one with pom.xml) 
```
mvn package
```
After downloading the dependencies, executing tests and shading the .jar the new jar will be 
located in the /target subfolder

NOTE: Even if shading task throws some warnings, the jar  
### 3. Testing if all works
When you are in the jar folder execute the command
```
java -jar <name_of_the_jar>.jar
```
The name of the jar usually is "GC24-1.0.jar"

If all worked properly the gui should start, and the main menu will be displayed
###4. Running the program
The program takes some arguments, these options are also listed after running the command
```
java -jar <name_of_the_jar>.jar -help (or -h)
```
#### Running the GUI
The GUI will start using no additional argument, or using -gui argument.
```
java -jar <name_of_the_jar>.jar [-gui]
```
#### Running the CLI
Appending the argument -cli, a command line interface will be started. 
The command line interface works in linux terminal. To use the CLI on windows you should install 
a terminal that supports ANSI escape codes (WSL or Windows Terminal).
```
java -jar <name_of_the_jar>.jar -cli
```
#### Running the Server
To start a server run the command
```
java -jar <name_of_the_jar>.jar -asServer [PORT NUMBER]
```
You could optionally add the TCP port number, if you don't specify it the default one
will be used (12345).
