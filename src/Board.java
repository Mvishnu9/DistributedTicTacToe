/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.*;
import java.rmi.server.*;
/**
 *
 * @author vishnum
 */
public class Board extends UnicastRemoteObject implements RMI_interface
{
    private char board[][]={
                                {'1','2','3'},
                                {'4','5','6'},
                                {'7','8','9'}
                           };
    private int MovNum;
    private int CurrPlayer;
    private int Loss;
    public Board() throws RemoteException
    {
        super();
        this.MovNum = 0;
        this.CurrPlayer = 0;
        this.Loss = -1;
    }
    
    @Override
    public int getPlayer() throws RemoteException
    {
        return CurrPlayer;
    }
    
    @Override
    public String getBoard() throws RemoteException
    {
		StringBuilder ConstBoard = new StringBuilder();
		ConstBoard.append("\n---+---+---\n ");
		synchronized (this) {
			ConstBoard.append(board[0][0]).append(" | ");
			ConstBoard.append(board[0][1]).append(" | ");
			ConstBoard.append(board[0][2]).append(" ");
			ConstBoard.append("\n---+---+---\n ");
			ConstBoard.append(board[1][0]).append(" | ");
			ConstBoard.append(board[1][1]).append(" | ");
			ConstBoard.append(board[1][2]).append(" ");
			ConstBoard.append("\n---+---+---\n ");
			ConstBoard.append(board[2][0]).append(" | ");
			ConstBoard.append(board[2][1]).append(" | ");
			ConstBoard.append(board[2][2]).append("\n---+---+---\n ");
		}
                return ConstBoard.toString();
    }
    
    @Override
    public void SetLoss(int Player)
    {
        Loss = Player;       
    }
    
    @Override
    public Boolean Move(int Player, int mov)
    {
        if(mov > 9 || mov < 0)
        {
            return false;
        }
        int col = (mov-1)%3;
        int row = (mov-1)/3;
        synchronized(this)
        {
            if(board[row][col] == 'x' || board[row][col] == 'o')
            {
                return false;
            }
            else
            {
                if(Player != CurrPlayer)
                    return false;
                if(Player == 0)
                    board[row][col] = 'x';
                else
                    board[row][col] = 'o';
                MovNum++;
                CurrPlayer = (CurrPlayer+1)%2;
                return true;
            }
        }
    }
    
    @Override
    public synchronized int GameOver()
    {
        if(Loss != -1)
        {
            if(Loss == 0)
                return 2;
            else 
                return 1;
        }
        for(int i=0; i<3; i++)
        {
            if(board[i][0] == board[i][1])
            {
                if(board[i][1]==board[i][2])
                {
                    if(board[i][0] == 'x')
                        return 1;
                    else
                        return 2;
                }
            }
            else if(board[0][i]==board[1][i])
            {
                if(board[1][i]==board[2][i])
                {
                    if(board[0][i]=='x')
                        return 1;
                    else
                        return 2;
                }
            }
        }
        if(MovNum == 9)
            return 3;
        return 0;
    }
    
}
