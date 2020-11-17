import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.*;

import buffers.OperationProtos.Operation;
import buffers.ResponseProtos.Response;
/**
 * Client 
 * This is the Client thread class, there is a client thread for each peer we are listening to.
 * We are constantly listening and if we get a message we print it. 
 */

public class GameClient extends Thread {
	private BufferedReader bufferedReader;
	
	public GameClient(Socket socket) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public void run() {
		while (true) {
			try {
			    JSONObject json = new JSONObject(bufferedReader.readLine());
			    System.out.println("[" + json.getString("username")+"]: " + json.getString("message"));
			} catch (Exception e) {
				interrupt();
				break;
			}
		}
	}

}
