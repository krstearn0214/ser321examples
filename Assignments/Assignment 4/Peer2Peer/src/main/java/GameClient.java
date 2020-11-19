//package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import game.GameProtos.*;
import com.google.protobuf.Any;

/**
 * Client 
 * This is the Client thread class, there is a client thread for each peer we are listening to.
 * We are constantly listening and if we get a message we print it. 
 */

public class GameClient extends Thread {
	private BufferedReader bufferedReader;
	private Socket s;
	
	public GameClient(Socket socket) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		s = socket;
	}
	public void run() {
		while (true) {
			try {
				Any any = Any.parseDelimitedFrom(s.getInputStream());
				if (any.is(Question.class))
				{
					Question q = any.unpack(Question.class);
					System.out.println(q.toString());
				}
			} catch (Exception e) {
				interrupt();
				break;
			}
		}
	}
	public Socket getSocket()
	{
		return s;
	}
}
