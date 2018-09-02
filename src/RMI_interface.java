/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vishnum
 */

import java.rmi.*;

public interface RMI_interface extends Remote
{
    public String getBoard() throws RemoteException;
    public Boolean Move(int player, int mov) throws RemoteException;
    public int getPlayer() throws RemoteException;
    public void SetLoss(int player) throws RemoteException;
    public int GameOver() throws RemoteException;
    
}
