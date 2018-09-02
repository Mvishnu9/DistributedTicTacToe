/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.*;
/**
 *
 * @author vishnum
 */
public class Client 
{
    RMI_interface board;
    Scanner sc;
    int gamestate = 0;
    int player = 1;
    
    public Client() throws NotBoundException, MalformedURLException, RemoteException
    {
        board = (RMI_interface)Naming.lookup("temp");
        sc = new Scanner(System.in);
    }
    
    public void StartGame() throws RemoteException
    {
        int move;
        gamestate = 0;
        boolean valid = false;
        while(gamestate == 0)
        {
            player = (player+1)%2;
            while(!valid)
            {
                System.out.println(board.getBoard());
                move = -1;
                while(move>9 || move<0)
                {
                    System.out.printf("Player %d - Please select a square where you want to place your token - %c \n", player, (player==0)?'x':'o');
                    move = sc.nextInt();
                }
                valid = board.Move(player, move);
                if(!valid)
                {
                    System.out.println("Invalid Move");
                }
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
            System.out.printf("Game over, Winner is Player %d havingthe token %c \n", gamestate-1,((gamestate-1)==0)?'x':'o');
        }
    }
    
    public static void main(String[] args)
    {
        try
        {
            Client cl = new Client();
            cl.StartGame();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
    }
    
}
