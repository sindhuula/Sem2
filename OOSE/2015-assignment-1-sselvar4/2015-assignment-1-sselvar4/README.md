Object Oriented Software Engineering
Assignment -1 : Hare and Hounds game
Submitted by: Sindhuula Selvaraju(sselvar4)
Submitted on: 16 September 2015
Collaboration with: Rupali Saboo 
=================

This repository contains my implementation of the hare and hounds game for the assignment 1.
The front-end is as was provided.
For the backend I have implemented the following .java files:
1. Bootstrap.java             : This file has the main() and instantiate a game and runs the web sever
2. HnHBoard.java            : This file has a class that saves the positions of the pieces on the board based on game and  move ids
3. HnHController.java      : This file acts as a controller that interacts with the front-end  
4. HnHPiece.java             : This file has a class that saves information about a piece 
5. HnHPlayer.java            : This file has a class that saves information about the player
6. HnHService.java          :  This file gets inputs from the HnHController.java and performs functions like creating a game etc.
7. Json Transformer        : This file is a wrapper around  the GSon library to generically convert java objects to (JSON) Strings

Setup
-----

It depends only on Java 8 and Maven to build and run.

Usage
-----

The code should be directly importable as an existing Maven project into Eclipse or IntelliJ, and should directly build and run from within the IDE.  

To compile from Command Line:

```console
mvn package
java -jar target/todoapp1-1.0-SNAPSHOT.jar
```
