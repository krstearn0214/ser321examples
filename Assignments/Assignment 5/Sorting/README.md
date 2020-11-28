Assignment 5 - Version 2

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
    data to those nodes and receive the data back.

2 AND 3. 
As the initial array was not significantly large enough to show difference between the three settings of sorting, I set an experiment of creating an array of 1000 and 5000 random int arrays with numbers between 0-999 and 0-4999, respectively.  Psudocode follows:
<make big array using Random.nextInt(x)>
long sT = System.currentTimeMillis();
<while loop to send, receive and sort data>
long eT = System.currentTimeMillis();
long timed = eT – sT;

The results are as follows:
1000 piece array of int's randomized from 1-999

One sorter - Elapsed time 1511 MS, 787255 bytes networked
One branch, two sorter - 3406 MS, 3071651
3 branch 4 sort - 3122 MS, 3072650

5000 piece array
0-1 4545  3891827
1-2 11707 15317947
3-4 22952 50282311

The results indicate that the distribution hinders, instead of helps, the sorting. The distribution helped in one specific case – 1000 piece array with 3 branches and 4 sorters.  In all other instances, the combination of distribution method and algorithm choice hindered the sorting.

The cause of these rapid increases in time and data is twofold.  First, the sorting algorithm is not memory efficient.  Second, the method of distribution requires a large amount of small communications, as opposed to a smaller amount of large ones.  These communications all require synchronization, forcing each to run at the speed only of the slowest fellow node.

4. The amount of packets and number of bytes being put through the network increase exponentially with the number of branches and nodes utilized.  There are a couple of ways to decrease this traffic.  First, methods can be changed to include less parameters (and potentially use an alternative to JSON objects).  Alternatively, if data were to be sent and received in a large block, it would reduce the number of bytes being utilized due to not having to pass methods around.  

**Task 2**

1.  Both gradle tasks are included within the build file.

2.  Attempted to connect distribution - was not able to figure out that ServerSocket within Node.java needed to be changed to Socket to accomidate nonlocal hosts.  Code submitted as-is.
EDIT:
3.  Was not able to have secondary local system accept socket requests, through AWS or VM.  VM did respond to pings of average 70ms response time, and the Branch remove() method sends 3 NetworkUtils.send() requests each call.  Each send() request ends in a return, giving 6 transmits over network per remove() call.  The amount of bytes would remain unchanged.  Thus, the following is extrapolated:

The results are as follows:
1000 piece array of int's randomized from 1-999

One sorter - Elapsed time 1511 + **0** ms, 787255 bytes networked
One branch, two sorter - 3406 + **4200** = 7606 ms, 3071651 bytes
3 branch 4 sort - not programmed

5000 piece array
0-1 4545 + **0** ms, 3891827 bytes
1-2 11707 + **21000** = 32707 ms, 15317947 bytes
3-4 not programmed

5.  The above would be for a local system.

7.  See above.

8.  A significant amount of time is lost by having each remove() call send three send() calls.  This is not efficient.  A live version of this sorting would split the list into equal parts, send as a JSON object, have each Sorter node merge sort while the branch was also merge sorting, then have the Branch node insertion sort the returned presorted objects.  

**Task 3**

1.  