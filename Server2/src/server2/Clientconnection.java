/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Holics
 */
public class Clientconnection {
    Socket sock;
    ObjectOutputStream out;
    ObjectInputStream in;
    public Clientconnection(Socket sock){
        this.sock=sock;
        try{
        out=new ObjectOutputStream(this.sock.getOutputStream());
//        in = new ObjectInputStream(this.sock.getInputStream());
        }catch(IOException io){}
    }
    public ObjectOutputStream out(){
        return out;
    }
    public ObjectInputStream in(){
        return in;
    }
    public void setin(){
        try{
        in = new ObjectInputStream(this.sock.getInputStream());
        }catch(IOException io){}
        
    }
}
