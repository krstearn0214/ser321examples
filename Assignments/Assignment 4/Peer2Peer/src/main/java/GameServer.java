//package game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import game.GameProtos.*;
import com.google.protobuf.Any;

/**
 * SERVER
 * This is the GameServer class that has a socket where we accept clients contacting us.
 * We save the clients ports connecting to the server into a List in this class. 
 * When we wand to send a message we send it to all the listening ports
 */

public class GameServer extends Thread{
	private ServerSocket serverSocket;
	private Set<Socket> listeningSockets = new HashSet<Socket>();
	private String curAns;
	
	public GameServer(String portNum) throws IOException {
		serverSocket = new ServerSocket(Integer.valueOf(portNum));
		curAns = null;
	}
	
	/**
	 * Starting the thread, we are waiting for clients wanting to talk to us, then save the socket in a list
	 */
	public void run() {
		try {
			while (true) {
				Socket sock = serverSocket.accept();
				listeningSockets.add(sock);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	builds a message based on sent parameters
	*/
	void messageOut(Any any)
	{
		try {
		if (any.is(Question.class))
				{
					Question q = any.unpack(Question.class);
					curAns = q.getAnswer();
					
					System.out.println(curAns);
				}
			for (Socket s : listeningSockets) {
				any.writeDelimitedTo(s.getOutputStream());
		     }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getCurAns()
	{
		return curAns;
	}
}
