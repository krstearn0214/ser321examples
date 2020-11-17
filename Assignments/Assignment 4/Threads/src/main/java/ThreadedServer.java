import java.net.*;
import java.io.*;

// Server class 
public class ThreadedServer  
{ 
    public static void main(String[] args) throws IOException  
    { 
        if (args.length != 1) {
            System.out.println("Usage: ThreadedServer <port>");
            System.exit(1);
        }
        ServerSocket ss = new ServerSocket(Integer.parseInt(args[0])); 
        StringList strings = new StringList();
          
        while (true)  
        { 
            Socket s = null; 
            try 
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                  
                System.out.println("client : " + s); 
                  
                // obtaining input and out streams 
                //DataInputStream dis = new DataInputStream(s.getInputStream()); 
                //DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                //System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object 
                Thread t = new PerformerHandler(s, strings); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
  
// ClientHandler class 
class PerformerHandler extends Thread  
{ 
    final Socket s; 
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
            //try { 
                Performer performer = new Performer(s, strings);
                performer.doPerform();
                
                
            //} catch (IOException e) { 
            //    e.printStackTrace(); 
            //} 
        } 
    } 
} 