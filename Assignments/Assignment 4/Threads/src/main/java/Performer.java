import java.net.*;
import java.io.*;

class Performer {

    StringList  state;
    Socket      sock;
    boolean     done;


    public Performer(Socket sock, StringList strings) {
        this.sock = sock;    
        this.state = strings;
        this.done = false;
    }

    private void getCommand(String cmd, String input, PrintWriter pOut)
    {
        switch (cmd)
        {
            case "add":
                state.add(input);
                pOut.println("Server state is now: " + state.toString());
                break;
            case "remove":
                state.remove(Integer.parseInt(input));
                pOut.println("Server state is now: " + state.toString());
                break;
            case "display":
                pOut.println("Server state is now: " + state.toString());
                break;
            case "count":
                pOut.println("Count of characters is: " + state.count().toString());
                break;
            case "reverse":
                state.reverse(Integer.parseInt(input));
                pOut.println("Server state is now: " + state.toString());
                break;
            default:
                done = true;
        }
    }

    public void doPerform() {
        
        BufferedReader in = null;
        PrintWriter out = null;
        try {

            in = new BufferedReader(
                        new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
            out.println("Enter command and data (. to disconnect):");

            while (!done) {
                String str = in.readLine();

                if (str == null || str.equals("."))
                {
                    done = true;
                }
                else {
                    int i = str.indexOf(' ');
                    String request;
                    String data;
                    if (i > -1)
                    {
                        request = str.substring(0, i);
                        data = str.substring(i+1);
                    }
                    else
                    {
                        request = str;
                        data = null;
                    }
                    getCommand(request, data, out);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            try {
                in.close();
            } catch (IOException e) {e.printStackTrace();}
            try {
                sock.close();
            } catch (IOException e) {e.printStackTrace();}
        }
    }
}

