CSC1035: Practical 2
====================
This package allows  for the use and analysis of directories. Directories are a class of containers designed to contain 
information about staff including surname, initials and telephone extensions.

Dependencies:
-------------
None

How to use:
-----------
Run the CollectionAnalysis.java file to begin execution of the program.

The program will ask for the location of an input csv file so that it may extract staff entries to put into a directory.
If the file input does not exist, a pop-up window will appear and ask you to search manually through the computer to 
find an input file. If you press cancel on this window, the program defaults to using a default csv file called 
"test_data.csv". You may enter additional entries via program arguments providing that they follow csv format: 
"surname,initials,extension".

After this the program will ask you in which type of Directory you would like to store your entries. Select the number 
that corresponds to that type. Using that type of directory, it then prints the results in an ASCII table to the console.
The same results are also printed to a configurable output csv file in csv format. Similarly to the input file, there is
a default file if you enter an invalid file name and close the file chooser.

Following this the program performs performance tests on all directory types, comparing how fast eah type of directory 
performs each method. The fastest time, average time and slowest types that each type of directory takes to execute 
each method are printed to console, then written to a configurable performance results text file. Similarly to the 
other files used in this program, there is a default file if you enter an invalid file name and close the file chooser.

Running the tests:
------------------
Run the Testing.java file to begin execution of tests on the program.

When the tests are run, there will be 26 print statements which will print either true or false depending on whether the
methods function correctly or not. If all methods function correctly within the directories, then all the print 
statements should be true. 

Built With:
-----------
IntelliJ - IDE

License:
--------
See [LICENSE.md](LICENSE.md) for more details

Acknowledgements:
-----------------
- [Harold Fellerman: progress.py (progress bar from CSC1034 practical 5)](https://nucode.ncl.ac.uk/scomp/stage1/csc1034/practicals/practical-5/blob/master/progress.py)