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
	private String curAns;
	private GamePeer peer;
	
	public GameClient(Socket socket, GamePeer peer) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		s = socket;
		curAns = null;
		this.peer = peer;
	}
	public void run() {
		while (true) {
			try {
				Any any = Any.parseDelimitedFrom(s.getInputStream());
				if (any.is(Question.class))
				{
					Question q = any.unpack(Question.class);
					curAns = q.getAnswer();
					System.out.println(q.getQuestion().toString());
				}
				else if (any.is(Answer.class))
				{
					Answer a = any.unpack(Answer.class);
					if(a.getIsCorrect() == true)
					{
						System.out.println("> Answer correct!");
						peer.notHost();
					}
					else
					{
						System.out.println("> Answer incorrect!");
					}
				}
				else if (any.is(Winner.class))
				{
					Winner w = any.unpack(Winner.class);
					
					System.out.println("> " + w.getName() + "has won the game!");
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
