package mergeSort;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.net.InetAddress;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MergeSort {
  /**
   * Thread that declares the lambda and then initiates the work
   */

  public static int message_id = 0;

  public static JSONObject init(int[] array) {
    JSONArray arr = new JSONArray();
    for (var i : array) {
      arr.put(i);
    }
    JSONObject req = new JSONObject();
    req.put("method", "init");
    req.put("data", arr);
    return req;
  }

  public static JSONObject peek() {
    JSONObject req = new JSONObject();
    req.put("method", "peek");
    return req;
  }

  public static JSONObject remove() {
    JSONObject req = new JSONObject();
    req.put("method", "remove");
    return req;
  }
  
  public static void Test(int port) {
    String host = "localhost";
	Random r = new Random();
	int goBig = 5000;
	int[] a = new int[goBig];
	for(int i = 0; i < goBig; i++)
	{//50000 piece array
	a[i] = r.nextInt(goBig);
	}//of 0-4999 int for longer sort times
	long sT = System.currentTimeMillis();
    //int[] a = { 5, 1, 6, 2, 3, 4, 10,634,34,23,653, 23,2 ,6 };
    JSONObject response = NetworkUtils.send(host, port, init(a));
    
    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        //System.out.println(response);;
 	//don't need 5000 lines
      } else{
        break;
      }
    }
	long eT = System.currentTimeMillis();
	long timed = eT - sT;
	System.out.println("Elapsed time was : " + timed);
  }

  public static void main(String[] args) {
    // all the listening ports in the setup
    int hostPort = 0;
    String c1host = null;
    int c1port = 0;
    String c2host = null;
    int c2port = 0;
    int bPort = 0;
    /*
    InetAddress sortIp = null;
    try{
      sortIp = InetAddress.getLocalHost();
    }catch(Exception e){
    }
    */
    if(args.length == 1)
    {
      hostPort = Integer.parseInt(args[0]);
      new Thread(new Sorter(hostPort)).start();
      System.out.println("making sorter");
    }
    
    if(args.length == 5)
    {
      System.out.println("marking brancher");
      c1host = args[0];
      c1port = Integer.parseInt(args[1]);
      c2host = args[2];
      c2port = Integer.parseInt(args[3]);
      bPort = Integer.parseInt(args[4]);
      new Thread(new Branch(bPort, c1port, c2port, c1host, c2host)).start();
      // Above is not suitable for hosting - Node needs to be refined to Socket, not ServerSocket.
    }
    /*
    System.out.println("enter y when ready");
    String ready = null;
    try{
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    ready = bufferedReader.readLine(); }
    catch(Exception e){

    }
    if (ready != "y")
    {
      System.exit(0);
    }
    */
    System.out.println("here we go");
    Test(bPort);
    ArrayList<Integer> ports = new ArrayList<>(Arrays.asList(8000, 8001, 8002, 8003, 8004, 8005, 8006));

    // setup each of the nodes
    //      0
    //   1     2
    // 3   4 5   6

    /*
    new Thread(new Branch(ports.get(0), ports.get(1), ports.get(2))).start();
    
    new Thread(new Branch(ports.get(1), ports.get(3), ports.get(4))).start();
    new Thread(new Sorter(ports.get(3))).start();
    new Thread(new Sorter(ports.get(4))).start();
    
    new Thread(new Branch(ports.get(2), ports.get(5), ports.get(6))).start();
    new Thread(new Sorter(ports.get(5))).start();
    new Thread(new Sorter(ports.get(6))).start();
    */
    // make sure we didn't hang
    System.out.println("started");
    // One Sorter
    //Test(ports.get(3));

    // One branch / Two Sorters
    //Test(ports.get(2));

    // Three Branch / Four Sorters
    //Test(ports.get(0));
	//System.exit(0);
  }
}
