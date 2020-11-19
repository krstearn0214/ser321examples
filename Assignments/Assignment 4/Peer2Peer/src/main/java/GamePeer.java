//package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.*;

import game.GameProtos.*;
import com.google.protobuf.Any;

/**
 * This is the main class for the peer2peer program.
 * It starts a client with a username and port. Next the peer can decide who to listen to. 
 * So this peer2peer application is basically a subscriber model, we can "blurt" out to anyone who wants to listen and 
 * we can decide who to listen to. We cannot limit in here who can listen to us. So we talk publicly but listen to only the other peers
 * we are interested in. 
 * 
 */

public class GamePeer {
	/**
	 *
	 */
	private String username;
	private BufferedReader bufferedReader;
	private GameServer serverThread;
	
	public GamePeer(BufferedReader bufReader, String username, GameServer serverThread){
		this.username = username;
		this.bufferedReader = bufReader;
		this.serverThread = serverThread;
	}
	/**
	 * Main method saying hi and also starting the Server thread where other peers can subscribe to listen
	 *
	 * @param args[0] username
	 * @param args[1] port for server
	 */
	public static void main (String[] args) throws Exception {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String username = args[0];
		System.out.println("Hello " + username + " and welcome! Your port will be " + args[1]);

		String filename = args[2];
		try {
			

			
		} catch (Exception e) {
			//TODO: handle exception
		}
		
		
		// starting the Server Thread, which waits for other peers to want to connect
		GameServer serverThread = new GameServer(args[1]);
		serverThread.start();
		GamePeer peer = new GamePeer(bufferedReader, args[0], serverThread);
		peer.updateListenToPeers();
	}
	
	/**
	 * User is asked to define who they want to subscribe/listen to
	 * Per default we listen to no one
	 *
	 */
	public void updateListenToPeers() throws Exception {
		System.out.println("> How many other players are playing?");
		int num = Integer.parseInt(bufferedReader.readLine());
		for(int j = 0; j < num; j++){
		System.out.println("> Who do you want to listen to? Enter host:port");
		String input = bufferedReader.readLine();
		String[] setupValue = input.split(" ");
		for (int i = 0; i < setupValue.length; i++) {
			String[] address = setupValue[i].split(":");
			Socket socket = null;
			try {
				socket = new Socket(address[0], Integer.valueOf(address[1]));
				new GameClient(socket).start();
			} catch (Exception c) {
				if (socket != null) {
					socket.close();
				} else {
					System.out.println("Cannot connect, wrong input");
					System.out.println("Exiting: I know really user friendly");
					System.exit(0);
				}
			}
		}
	}
	askForInput();
	}
	
	/**
	 * Client waits for user to input their message or quit
	 *
	 * @param bufReader bufferedReader to listen for user entries
	 * @param username name of this peer
	 * @param serverThread server thread that is waiting for peers to sign up
	 */
	public void askForInput() throws Exception {
		try {
			
			System.out.println("> You can now start chatting (exit to exit)");
			while(true) {
				String message = bufferedReader.readLine();
				if (message.equals("exit")) {
					System.out.println("bye, see you next time");
					break;
				} else {
					// we are sending the message to our server thread. this one is then responsible for sending it to listening peers
					Question q = Question.newBuilder()
						.setQuestion("What is 2 + 2?")
						.setAnswer("4")
						.build();
					Any any = Any.pack(q);
					System.out.println(any.toString());
					serverThread.messageOut(any);
				}	
			}
			System.exit(0);
			/*
			try {
				for (Socket s : serverThread.getListeners()) {
					
					//op.writeDelimitedTo(s.getOutputStream());
				 }
			} catch(Exception e) {
				e.printStackTrace();
			}
			*/
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
	private static JSONObject readJson(String filename) throws IOException, JSONException {
		// read json from build directory, so the getResource is needed
		File file = new File(
		  SockBaseClient.class.getResource("/"+filename).getFile()
		);
		Reader reader = new FileReader(file);
		JSONTokener jsonTokener = new JSONTokener(reader);
		return new JSONObject(jsonTokener);
	  }

	  private static Operation generateObjectFromPB(JSONObject data) {
		JSONObject header = (JSONObject)data.get("header");
		JSONObject payload = (JSONObject)data.get("payload");
  
		// create protobuf object
		Operation.Builder op = Operation.newBuilder()
		  .setVal1((String)payload.get("num1"))
		  .setVal2((String)payload.get("num2"))
		  .setBase((String)header.get("base"))
		  .setOperationType(getOperationType((String)header.get("operation")))
		  .setResponseType(getResponseType((String)header.get("response")));
		return op.build();
	  } 

	  		Operation op = null;
		JSONObject data = null;
		JSONParser parser = new JSONParser();
		try
		{//start here
			Object obj = parser.parse(new FileReader(filename));
			JSONObject jsonObject = (JSONObject) obj;
			data = readJson(filename);
			op = generateObjectFromPB(data);
*/
}
