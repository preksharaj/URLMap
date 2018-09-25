# URLMap
Problem
------------
You’re given an input file. Each line consists of a timestamp (unix epoch in seconds) and a url separated by ‘|’ (pipe operator). The entries are not in any chronological order. Your task is to produce a daily summarized report on url hit count, organized daily (mm/dd/yyyy GMT) with the earliest date appearing first. For each day, you should display the number of times each url is visited in the order of highest hit count to lowest count. Your program should take in one command line argument: input file name. The output should be printed to stdout. You can assume that the cardinality (i.e. number of distinct values) of hit count values and the number of days are much smaller than the number of unique URLs. You may also assume that number of unique URLs can fit in memory, but not necessarily the entire file.

Run Instructions
-----------
Using IDE
-Import the project in any IDE(eg Eclipse)
-Run the project with Run as-> Java Application.

Command Prompt
-javac URLMap.java
-java URLMap.java

Time Complexity
-----------
O(n) where n = number of input lines in the input file
