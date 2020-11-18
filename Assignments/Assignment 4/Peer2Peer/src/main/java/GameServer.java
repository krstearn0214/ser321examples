import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import game.GameProtos.*;

/**
 * SERVER
 * This is the GameServer class that has a socket where we accept clients contacting us.
 * We save the clients ports connecting to the server into a List in this class. 
 * When we wand to send a message we send it to all the listening ports
 */

public class GameServer extends Thread{
	private ServerSocket serverSocket;
	private Set<Socket> listeningSockets = new HashSet<Socket>();
	
	public GameServer(String portNum) throws IOException {
		serverSocket = new ServerSocket(Integer.valueOf(portNum));
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
	
	/**
	 * Sending the message to the OutputStream for each socket that we saved
	 */
	void sendMessage(String msg) {
		Message message; //work this and client and peer
		try {
			for (Socket s : listeningSockets) {
				Question.Builder builder = Question.newBuilder()
						.setQuestion("What is 2 + 2?")
						.setAnswer("4");
				message.writeTo(s.getOutputStream());
				//out.println(message);
		     }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	

	public Set <Socket> getListeners()
	{
		return listeningSockets;
	}
}
