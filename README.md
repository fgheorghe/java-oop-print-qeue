# Author #

Grosan Flaviu Gheorghe.

# Entry point #

src/Final/Main.java is the main entry point.

# How to run #

If this application is run on a UNIX system, with the make command available,
then it can be built and run using the Makefile provided, by running the 'make'
command run from this directory.

This code has been built without an IDE, however, an Eclipse .project file is provided,
in this folder. Once the project is loaded it can be run without modifications.

Compiled on Java 1.8 should run on 1.6 and 1.7.

# Output Content #
Printer application started.

Author: Grosan Flaviu Gheorghe

Number of events in queue: 9

Event name: add-printer, timestamp: 1419187384, subject: Test - EPSON

Event name: add-printer, timestamp: 1419187412, subject: Test - HP

Event name: add-print-job, timestamp: 1419187413, subject: print job TWO

Event name: add-print-job, timestamp: 1419187414, subject: print job ONE

Event name: set-print-job-priority, timestamp: 1419187415, subject: print job FOUR

Event name: add-print-job, timestamp: 1419187416, subject: print job THREE

Event name: add-print-job, timestamp: 1419187417, subject: print job FOUR

Event name: add-print-job, timestamp: 1419187418, subject: print job FIVE

Event name: remove-printer, timestamp: 1449187412, subject: Test - HP

Printing job id print job FOUR: -----

Printing job id print job TWO: Hello

Printing job id print job ONE: World

Exception while handling a queue: No printer can print the current jobs. Add a new printer or remove jobs.

Application terminated.

Number of events in queue: 0

Number of items not printed: 2

