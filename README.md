Virtual-Memory-Management-Simulation
I made this project with two of my colleagues for the ENCS3390 Operating Systems course. Eclipse development environment was used in the coding process of our virtual memory simulator, Using Java programming language.

The project illustrates the concept of virtual memory management in a simulation that includes several aspects and concepts of an Operating System. It deals with memory traces, Page Faults, Scheduling using both Round Robin Algorithm and First-In-First-Out algorithm and different page replacement Algorithms, namely: FIFO, LRU, Second-chance FIFO.

PROJECT IMPLEMENTAION:

several classes were made for each section of the simulation as below:

Process class: Represents an object of a process with its corresponding attributes, such as Process ID, Arrival Time, Duration, and Page References.

PageReplacement class: Implements the page replacement policies and algorithms.

FileInput class: Reads the configuration file, or generates a random configuration file with respect to certain limitation in limits of each random element.

UserInterface class: The Graphical User Interface (GUI) of the memory manager simulator. It calls all algorithms codes and functions from the different classes.

MyThread class: Extends (Thread), is a subclass of the super class Thread. Aims to create Threads for the different functions used in the program. And also creates threads for each process.

FCFS class: Implements the FCFS scheduling Algorithm, and calculates the waiting time and turn-around time for each process, as well as their average values for all processes.

HOW THE PROGRAM WORKS?

An interface appears to the user and gives him/her the ability to choose to work on data read from either a configuration file or a randomly generated file. Then the user selects the memory size and the page replacement algorithms to work on. Finally, by pressing the Simulate button, the results will appear on the interface.
