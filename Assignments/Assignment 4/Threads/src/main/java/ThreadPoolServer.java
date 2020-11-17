import java.net.*;
import java.io.*;

public class ThreadPoolServer  
{
    private static int connectCount = 0;

    public static void addCount()
    {
        connectCount++;
    }

public static int getCount()
    {
        return connectCount;
    }
    public static void main(String[] args) throws IOException  
    {
        if (args.length != 2) {
            System.out.println("Usage: ThreadPoolServer <port> <connectionLimit>");
            System.exit(1);
        }
        int conLimit = Integer.parseInt(args[1]);
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0])); 
        StringList strings = new StringList();
          
        while (true)  
        { 
            Socket s = null;
            if(getCount() < conLimit)
            {
                try 
                { 
                    s = serverSocket.accept(); 
                    System.out.println("client : " + s); 
                    Thread t = new PerformerHandlerPool(s, strings); 
                    addCount();
                    t.start(); 
                } 
                catch (Exception e){ 
                    s.close(); 
                    e.printStackTrace(); 
                } 
            }
        } 
    } 
} 

class PerformerHandlerPool extends Thread  
{ 
    Socket s; 
    StringList strings;
  
    // Constructor 
    public PerformerHandlerPool(Socket s, StringList strings)  
    { 
        this.s = s; 
        this.strings = strings;
    } 
  
    @Override
    public void run()  
    { 
        while (true)  
        { 
            Performer performer = new Performer(s, strings);
            performer.doPerform();
        } 
    } 
} 