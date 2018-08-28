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
    public Board() throws RemoteException
    {
        super();
        this.MovNum = 0;
        this.CurrPlayer=0;        
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
    public Boolean Move(int Player, int row, int col)
    {
        
    }
    
}
