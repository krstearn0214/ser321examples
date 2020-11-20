//package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

import game.GameProtos.*;
import com.google.protobuf.Any;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
 
import java.io.FileReader;

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
	private Player p;
	private BufferedReader bufferedReader;
	private GameServer serverThread;
	private JSONArray qData;
	private Boolean[] isUsed;
	private String curAnswer;
	
	public GamePeer(BufferedReader bufReader, Player p, GameServer serverThread, JSONArray qData){
		this.p = p;
		this.bufferedReader = bufReader;
		this.serverThread = serverThread;
		this.qData = qData;
		Boolean[] isUsed = new Boolean[13];
		curAnswer = null;
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
		Player p = new Player(username);
		System.out.println("Hello " + username + " and welcome! Your port will be " + args[1]);
		JSONParser jp = new JSONParser();
		String filename = args[2];
		Object obj = null;
		try {
			obj = jp.parse(new FileReader(filename));
		} catch (Exception e) {
		}
		JSONArray array = (JSONArray) obj;
		JSONObject jQ = (JSONObject)array.get(1);
		//System.out.println(jQ.toString());
		//System.out.println(jQ.get("id"));

		GameServer serverThread = new GameServer(args[1]);
		serverThread.start();
		GamePeer peer = new GamePeer(bufferedReader, p, serverThread, array);
		peer.updateListenToPeers(peer);
	}
	
	/**
	 * User is asked to define who they want to subscribe/listen to
	 * Per default we listen to no one
	 *
	 */
	public void updateListenToPeers(GamePeer peer) throws Exception {
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
				new GameClient(socket, peer).start();
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
	gameStart();
	}
	
	/**
	 * Client waits for user to input their message or quit
	 *
	 * @param bufReader bufferedReader to listen for user entries
	 * @param username name of this peer
	 * @param serverThread server thread that is waiting for peers to sign up
	 */
	public void gameStart() throws Exception {
		try {
			System.out.println("> Choosing inital host...");
			/*
			int myVal = 0;
			for(int i = 0; i < p.getName().length(); i++)
			{
				myVal += (int) p.getName().charAt(i);
			}
				
				if(myVal > 97) //a=97 for testing confirmed host
				{//to be changed to username-grabbing method
					getPlayer().nowHost(true);
				}
				else
				{
					getPlayer().nowHost(false);
				}
			*/
			if (p.getName().equals("joe"))
			{
				p.nowHost(true);
			}
			else{
				p.nowHost(false);
			}
			while(true) {
				gameOn();
			}
			//System.exit(0);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gameOn() throws Exception
	{
		System.out.println(p.getName());
		try{
			if(p.getHost() == true)
			{
				askQuestion();
			}
			else
			{
				getQuestion();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void askQuestion() throws Exception
	{
		System.out.println("> Asking question...");
		int id = randQuest();
		try{
			JSONObject jsQuest = (JSONObject)getArray().get(id);
			Question q = Question.newBuilder()
						.setQuestion(jsQuest.get("question").toString())
						.setAnswer(jsQuest.get("answer").toString())
						.setType(jsQuest.get("type").toString())
						.build();
					Any any = Any.pack(q);
					serverThread.messageOut(any);
		//String message = bufferedReader.readLine();
		//if (message.equals("exit")) System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		while(p.getHost() == true)
		{

		}
		getQuestion();
	}

	public void getQuestion() throws Exception
	{
		System.out.println("> Getting question...");
		try{

		String message = bufferedReader.readLine();
		if (message.equals("exit")) {System.exit(0);}
		else{
			if(message.equals(serverThread.getCurAns()))
			{
				p.score();
				if(p.getPoints() == 5)
				{
					Winner w = Winner.newBuilder()
						.setName(p.getName())
						.build();
					Any any = Any.pack(w);
					serverThread.messageOut(any);
				}
				else{
				
				Answer a = Answer.newBuilder()
					.setIsCorrect(true)
					.build();
				Any any = Any.pack(a);
				serverThread.messageOut(any);
				p.nowHost(true);
				}

			}
			else{
				Answer a = Answer.newBuilder()
					.setIsCorrect(false)
					.build();
				Any any = Any.pack(a);
				serverThread.messageOut(any);
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int randQuest()
	{
		Random rand = new Random();
		int id = 0;
		boolean useable = false;
		while(useable = false)
		{
			id = rand.nextInt(12) + 1;
			if(isUsed[id] = false){
				useable = true;
			}
		}
		return id;
	}

	public Player getPlayer()
	{
		return p;
	}

	public void setAnswer(String ans)
	{
		curAnswer = ans;
	}

	public void notHost()
	{
		getPlayer().nowHost(false);
	}

	public JSONArray getArray()
	{
		return qData;
	}
	/*
					if(getPlayer().getHost() == true)
				{
					System.out.println("> Waiting on player answers...");
					Question q = Question.newBuilder()
						.setQuestion("What is 2 + 2?")
						.setAnswer("4")
						.build();
					Any any = Any.pack(q);
					serverThread.messageOut(any);
				}
				else
				{
					System.out.println("> Answer the following question: ");
					String message = bufferedReader.readLine();
					if (message.equals("exit")) {
						System.out.println("bye, see you next time");
					break;
					} 
					else
					{
						boolean cor = false;
						if(message.equals("4"))
						{
							cor = true;
						}
						Answer a = Answer.newBuilder()
							.setIsCorrect(cor)
							.build();
						Any any = Any.pack(a);
						serverThread.messageOut(any);
					}
				}
	*/
}
