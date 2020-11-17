**Activity 1**

All gradle commands are to be executed within the Threads folder.

**Task 1**
Can be run by:
gradle runServer --args='<port number>'

Connecting via telnet or netcat allows for the client to use the
project required commands - any non-command entry disconnects the
client.  List of commands:
add <string> - adds string to list and displays list
remove <int> - removes string at specified index and displays list
display - displays the list
count - returns an array containing the character count of each
    string within the list
reverse <int> - reverses the string at the given index

**Task 2**
Can be run by:
gradle runThreadedServer --args='<port number>'

Connecting via telnet or netcat allows for the client to use the
project required commands - any non-command entry disconnects the
client.  List of commands is same as Task 1.

No client blocks connections from another client.
String list contents are properly managed such that each client
recieves and sends information to the same list.

**Task 3**
Can be run by:
gradle runThreadPoolServer --args='<port number> <connection limit>'

Connecting via telnet or netcat allows for the client to use the
project required commands - any non-command entry disconnects the
client.  List of commands is same as Task 1.

Server only allows connections up to the number indicated in run-
time arguements.  Otherwise, functions as runThreadedServer.

NOTE:
Given the starter code, finishing Task 1 led to issues completing
Task 3 cleanly.  Task 1 encourages connection management within the
Performer class, which raises issues with tracking client
disconnects.  