/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.*;
/**
 *
 * @author vishnum
 */
public class Server {

    public static void main(String[] args) throws Exception {        
        Board curr = new Board();
        Naming.rebind("temp", curr);      
        System.out.println("Binding complete...\n");
    }
}
