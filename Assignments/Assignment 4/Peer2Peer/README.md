**Activity 2** - Kyle Stearns (krstearn)

All gradle commands are to be executed within the Peer2Peer folder.

Can be run by:
gradle runPeer --args="<username> <portnumber> data.json"

To have host functionality, one <username> entry MUST be "joe".

I've not tested the program anywhere other than localhost:port,
however the deserialization of the data should allow for ease
over any connection protocol.

When prompted, enter the number of expected connections in INT format.
Enter each <address>:<portnumber> individually.

The game will then prompt "joe", the host, to broadcast a question.
All other players will recieve the question.  Players may then send
an answer.

**BUGS**
-Randomization of questions does not occur, sticks on first question.
-Question does not accept answer, even when correct.
-Due to nonacceptance of answers, winning state cannot be reached.

**THINGS THAT WORK**
-Protobuf and deserialization function as intended.
-Host exchange can work (attempting to add random question functions
    caused issues, left as is).
-All peers have full client/server functionality.
