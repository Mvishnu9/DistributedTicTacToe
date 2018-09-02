/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.*;
import java.net.*;
/**
 *
 * @author vishnum
 */
public class Client 
{
    
    RMI_interface board;
    Scanner sc;
    String id;
    private Socket socket = null;
    private DataInputStream dis = null;
    private InetAddress ip = null;
    int gamestate = 0;
    int player = 0;
    
    public Client(String address, int port) throws NotBoundException, MalformedURLException, RemoteException
    {
        try
        {
            ip = InetAddress.getByName("localhost");
            socket = new Socket();
            socket.connect(new InetSocketAddress(address, port), 1000);
            dis = new DataInputStream(socket.getInputStream());
            id = dis.readUTF();
            
            String pl = dis.readUTF();
            player = Integer.valueOf(pl);
            
            board = (RMI_interface)Naming.lookup(id);
            sc = new Scanner(System.in);
        }
        catch(SocketTimeoutException ste)
        {
            System.out.println("Connection timed out, please verify if the Server is running at the given ip and port");
            return;
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    
    public void StartGame() throws RemoteException, IOException
    {
        System.out.println("Please wait. Searching for players.");
        String starter = dis.readUTF();
        if(starter.equalsIgnoreCase("start"));
        {
            boolean flag = false;
            int move;
            gamestate = 0;
            while(gamestate == 0)
            {
                System.out.println(board.getBoard());
                if(board.getPlayer() != player) 
                {
                    System.out.println("Please wait for opponent's turn");
                    while(board.getPlayer() != player)
                    {

                    }
                }
                boolean valid = false;
                gamestate = board.GameOver();
                if(gamestate!=0)
                    break;
                long start = System.currentTimeMillis();
                long end = start + 10*1000;
                while(!valid && (System.currentTimeMillis() < end))
                {

                    System.out.println(board.getBoard());
                    move = -1;
                    while(move>9 || move<0 && (System.currentTimeMillis() < end))
                    {
                        gamestate = board.GameOver();
                        if(gamestate!=0)
                        {
                            break;
                        }
                        System.out.printf("Player %d - Please select a square where you want to place your token - %c \n", player, (player==0)?'x':'o');
                        move = sc.nextInt();
                    }
                    if(flag)
                        break;
                    valid = board.Move(player, move);
                    if(!valid)
                    {
                        System.out.println("Invalid Move ");
                    }
                }
                if(flag)
                    break;
                if(System.currentTimeMillis() > end)
                {
                    board.SetLoss(player);
                }
                gamestate = board.GameOver();

            }
            System.out.println(board.getBoard());

            if(gamestate == 3)
            {
                System.out.println("Game was a draw");
            }
            else
            {
                System.out.printf("Game over, Winner is Player %d having the token %c \n", gamestate-1,((gamestate-1)==0)?'x':'o');
            }
        }
    }
    
    public static void main(String[] args)
    {
        BufferedReader reader = 
                   new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter IP Address of server to connect to -");
        String ip = "";
        try
        {
            ip = reader.readLine();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        String port = "";
        int portno = 5000;
        System.out.println("Enter port of server to connect to -");
        try
        {
            port = reader.readLine();
            portno = Integer.parseInt(port);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        try
        {
            
            Client cl = new Client(ip, portno);        
            cl.StartGame();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
    }
    
}
