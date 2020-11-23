**Task 1**

1.  
MergeSort.java, which contains the main function, sets up 7 nodes including itself.
It then sets up multiple Branches, then runs three different tests, involving:
One Sorter
One Branch, Two Sorters
Three Branches, Four Sorters.

Branch.java contains the function to split a JSON Object into two JSON Arrays; then
    send the JSON Object data to left and right Nodes.

Node.java allows for a case of one individual connection, used as a communication object
    for the Sorter class.   

NetworkUtils.java allows for the sending and recieving of JSON Objects between Nodes.

Sorter.java is the working method for sorting using a priority queue.

Using Wireshark and only using one sorter, the total bytes transmitted was 29214.
With one branch and two sorters, byte total was 67017.
With three branches and four sorters, byte total was 152123.

This distributed system has the advantage of using multiple nodes to do it's calculations,
    saving time during it's sorting process.  It has the disadvantage of needing to transmit
    data to those nodes and recieve the data back.

2. 
1000 piece array of int's randomized from 1-999

One sorter - Elapsed time 1511 MicroSeconds, 787255 bytes networked
One branch, two sorter - 3406 MicroSeconds, 3071651
3 branch 4 sort - 3122 MS, 3072650

