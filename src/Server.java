/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.rmi.*;
/**
 *
 * @author vishnum
 */
public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private Socket psocket = null;
    private DataOutputStream out = null;
    private DataOutputStream pout = null;
    int players;
    int gameroom;
    String id = "";
    
    public Server(int port)
    {
        try
        {
            server = new ServerSocket(port);
            players = -1;
            gameroom = 0;
            id = "0";
            Board curr = new Board();
            Naming.rebind(id, curr);
            System.out.println("Server started");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        Thread AcceptComms = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    while(true)
                    {
                        psocket = socket;
                        pout = out;
                        socket = server.accept();
                        System.out.println("New client request received : " + socket);
                        players++;                       
                        
                        System.out.println(players);
                        id = String.valueOf(gameroom);
                        out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF(id);
                        out.writeUTF(Integer.toString((players)%2));
                        
                        System.out.println("Creating a new handler for this client...");
                  
                        if(players%2 != 0)
                        {
                            out.writeUTF("start");
                            pout.writeUTF("start");
                            gameroom++; 
                            id = String.valueOf(gameroom);
                            Board curr = new Board();
                            Naming.rebind(id, curr);      
                            System.out.println("Binding complete...\n");                            
                        }
                    }
                }
                catch(IOException i)
                {
                    i.printStackTrace();
                }
            }
        });
        AcceptComms.start();
    }

    public static void main(String[] args) throws Exception 
    {
        Server ser = new Server(5000);
    }
}
