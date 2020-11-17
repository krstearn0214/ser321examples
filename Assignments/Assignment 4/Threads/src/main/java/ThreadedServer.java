import java.net.*;
import java.io.*;

public class ThreadedServer  
{ 
    public static void main(String[] args) throws IOException  
    { 
        if (args.length != 1) {
            System.out.println("Usage: ThreadedServer <port>");
            System.exit(1);
        }
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0])); 
        StringList strings = new StringList();
          
        while (true)  
        { 
            Socket s = null; 
            try 
            { 
                s = serverSocket.accept(); 
                System.out.println("client : " + s); 
                Thread t = new PerformerHandler(s, strings); 
                t.start(); 
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 

class PerformerHandler extends Thread  
{ 
    Socket s; 
    StringList strings;
  
    // Constructor 
    public PerformerHandler(Socket s, StringList strings)  
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