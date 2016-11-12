/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server2.MSG.MSGStruct;
import server2.QStruct;

/**
 *
 * @author Holics
 */
public class Server2 {

    /**
     * @param args the command line arguments
     */
    static final int USERLIMIT = 100;
    private static int PORT = 1;
    private static LinkedList prequeue = new LinkedList();
    private static LinkedList queue = new LinkedList();
    private static HashMap<String, Clientconnection> logedlist = new HashMap<String, Clientconnection>();
    private static HashMap<String, Group> grouplist = new HashMap<String, Group>();
    static int index=0;

    public static void main(String[] args) throws IOException {
//        Scanner scan = new Scanner(System.in);
//        System.out.print("Enter port to open : ");
//        PORT = scan.nextInt();
        System.out.println("Server Port is " + PORT + ".");
        ServerSocket listener = new ServerSocket(PORT);
        System.out.println("The chat server is running.");
        new Sender().start();
        new Packhandler().start();
        while (true) {
            new Clientconnection(listener.accept()).start();
        }
    }

    static class Packhandler extends Thread {

        static boolean refreshuser() {
            boolean retval = true;
            String temp = "";
            for (String k : logedlist.keySet()) {
                temp = temp + k + "\t";
            }
            if (temp.equals("\t")) {
                retval = false;
            }
            MSGStruct newmsg = new MSGStruct();
            newmsg.setMSG(temp);
            newmsg.setType(1);
            newmsg.setOption(3);
            for (Clientconnection t : logedlist.values()) {
                if (t == null) {
                    break;
                }
                queue.add(new QStruct(newmsg, t));
            }
            return retval;
        }

        static void refreshgroup() {
            MSGStruct newmsg = new MSGStruct();
            String temp = "";
            for (String k : grouplist.keySet()) {
                temp = temp + k + "\t";
            }
            newmsg.setMSG(temp);
            newmsg.setType(1);
            newmsg.setOption(0);
            for (Clientconnection t : logedlist.values()) {
                if (t == null) {
                    break;
                }
                queue.add(new QStruct(newmsg, t));
            }
        }

        static void refreshGmembers(String groupname) {
            if (grouplist.get(groupname).getMemberNum() <= 0) {
                return;
            }
            MSGStruct newmsg = new MSGStruct();
            MSGStruct queuemsg = new MSGStruct();
            String temp = "";
            String temp2 = "";
            for (String k : grouplist.get(groupname).getmemberlist()) {
                temp = temp + k + "\t";
                temp2 = temp2 + k + "\t  " + logedlist.get(k).sock.getInetAddress() + "\t" + logedlist.get(k).sock.getPort() + "\t";
            }
            newmsg.setMSG(temp);
            newmsg.setType(1);
            newmsg.setOption(3);
            queuemsg.setMSG(temp2);
            queuemsg.setType(2);
            queuemsg.setOption(3);
            for (String k : grouplist.get(groupname).getmemberlist()) {
                if (logedlist.containsKey(k)) {
                    queue.add(new QStruct(newmsg, logedlist.get(k)));
                    queue.add(new QStruct(queuemsg, logedlist.get(k)));
                }
            }
        }

        public void run() {
            while (true) {
                try {
                    sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!prequeue.isEmpty()) {
                    QStruct pops = (QStruct) prequeue.pop();
                    MSGStruct packet = pops.getMsg();
                    System.out.println("process : " + packet.getMSG() + "\t : " + packet.getType() + " : " + packet.getOption());
                    Clientconnection cli = pops.getcli();
                    switch (packet.getType()) {
                        case 0:
                            queue.add(pops);
                            break;
                        case 1:
                            MSGStruct send = new MSGStruct();
                            if (packet.getOption() == 1) {//if its opening a group
                                Group newgroup = new Group(packet.getGroupName());
                                if (grouplist.containsKey(packet.getGroupName())) {
                                    send.setType(1);
                                    send.setMSG("This group is already exist");
                                    send.setOption(-1);
                                } else {
                                    grouplist.put(packet.getGroupName(), newgroup);
                                    grouplist.get(packet.getGroupName()).addMember(packet.getSender());
                                    send.setType(1);
                                    send.setOption(1);
                                    send.setGroupName(packet.getGroupName());
                                    send.setBadge(true);
                                    send.setMSG("The new group is opened and you have a badge");
                                    refreshgroup();
                                    refreshGmembers(packet.getGroupName());
                                }
                            } else if (packet.getOption() == 2) {
                                grouplist.get(packet.getGroupName()).addMember(packet.getSender());
                                send.setMSG("Now you're in Group " + packet.getGroupName());
                                send.setType(1);
                                send.setOption(2);
                                send.setGroupName(packet.getGroupName());
                                refreshGmembers(packet.getGroupName());
                            } else if (packet.getOption() == 4) {
                                try {
                                    grouplist.get(packet.getGroupName()).removeMember(packet.getSender());
//                                    send.setMSG("You're successfully out");
                                    send.setType(1);
                                    send.setOption(4);
                                    boolean deleted = false;
                                    for (String gn : grouplist.keySet()) {
                                        if (grouplist.get(gn).getMemberNum() <= 0) {
                                            grouplist.remove(gn);
                                            deleted = true;
                                        }
                                    }
                                    if (deleted) {
                                        refreshgroup();
                                    }
                                    if (!deleted && grouplist.get(packet.getGroupName()).getMemberNum() > 0) {
                                        refreshGmembers(packet.getGroupName());
                                    }
                                } catch (Exception ex) {
                                    send.setType(1);
                                    send.setMSG("Error");
                                    send.setOption(-1);
                                }
                            }
                            queue.add(new QStruct(send, cli));
                            break;
                        case 2:
                            if (packet.getOption() == 0) {
                                if (!logedlist.containsKey(packet.getSender())) {
                                    packet.setMSG("Welcome!");
                                } else {
                                    packet.setMSG("This username is already taken. Try other");
                                    packet.setOption(-1);
                                }
                            } else if (packet.getOption() == 4) {
                                logedlist.put(packet.getSender(), cli);
                                packet.setMSG(""+index++);
                                System.out.println("index="+(index-1));
                                System.out.println("id : " + packet.getSender() + "\tSocket : " + cli.sock + " is registered");
                                refreshuser();
                                refreshgroup();
                            }
                            packet.setRecieve(packet.getSender());
                            packet.setSender("Server");
                            queue.add(new QStruct(packet, cli));
                            break;
                    }
                }
            }
        }

    }

    static class Sender extends Thread {

        public void run() {
            while (true) {
                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!queue.isEmpty()) {
                    try {
                        QStruct qs = (QStruct) queue.pop();
                        MSGStruct msg = qs.getMsg();
                        System.out.println("Sending  : " + msg.getSender() + "\t : " + msg.getMSG());
                        if (msg.getType() == 0) {
                            if (msg.getOption() == 0) {
                                if (logedlist.containsKey(msg.getRecieve())) {
                                    Clientconnection cli = logedlist.get(msg.getRecieve());
                                    try {
                                        cli.out().writeObject(msg);
                                    } catch (Exception ex) {
                                    }
                                }
                            } else if (msg.getOption() == 1) {

                                if (!qs.getMsg().getBadge()) {
                                    qs.getMsg().setMSG("Please acquire token");
                                    qs.getMsg().setSender("Server");
                                    qs.getMsg().setOption(-1);
                                    try {
                                        qs.getcli().out.writeObject(qs.getMsg());
                                    } catch (IOException io) {

                                    }
                                } else {
                                    for (String s : logedlist.keySet()) {
                                        if (s == null) {
                                            break;
                                        }
                                        logedlist.get(s).out().writeObject(msg);
                                        logedlist.get(s).out().flush();
                                    }
                                }
                            } else if (msg.getOption() == 2) {
                                String grpname = msg.getGroupName();
                                if (grouplist.containsKey(grpname)) {
                                    Group tempg = grouplist.get(grpname);
                                    for (String t : tempg.getmemberlist()) {
                                        if (logedlist.containsKey(t)) {
                                            MSGStruct tmp = msg;
                                            logedlist.get(t).out().writeObject(tmp);
                                            logedlist.get(t).out().flush();
                                        }
                                    }
                                }
                            }
                        } else if (msg.getType() == 1 || msg.getType() == 2) {
                            synchronized (qs.getcli().sock) {
                                if (qs.getcli().sock.isConnected()) {
                                    qs.getcli().out().writeObject(msg);
                                }
                            }
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static class Clientconnection extends Thread {

        Socket sock;
        ObjectOutputStream out;
        ObjectInputStream in;

        public Clientconnection(Socket sock) {
            this.sock = sock;
            System.out.println("Socket : " + this.sock + " has been created");
            try {
                out = new ObjectOutputStream(this.sock.getOutputStream());
            } catch (IOException io) {
            }
        }

        public ObjectOutputStream out() {
            return out;
        }

        public ObjectInputStream in() {
            return in;
        }

        public void setin() {
            try {
                in = new ObjectInputStream(this.sock.getInputStream());
            } catch (IOException io) {
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                sock.close();
            } catch (IOException io) {
            }
        }

        public void send(MSGStruct msg) {
            try {
                out.writeObject(msg);
            } catch (IOException io) {
                return;
            }
        }

        public void run() {
            setin();
            while (true) {
                try {
                    sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
                }
                MSGStruct msg = null;
                try {
                    msg = (MSGStruct) in.readObject();
                } catch (EOFException eof) {
                    synchronized (this) {
                        for (String t : logedlist.keySet()) {
                            if (logedlist.get(t).equals(this)) {
                                close();
                                logedlist.remove(t);
                            }
                        }
                    }
                    String temp = "";
                    for (String k : logedlist.keySet()) {
                        temp = temp + k + "\t";
                    }
                    MSGStruct newmsg = new MSGStruct();
                    newmsg.setMSG(temp);
                    newmsg.setType(2);
                    newmsg.setOption(3);
                    synchronized (logedlist) {
                        for (Clientconnection t : logedlist.values()) {
                            if (t == null) {
                                break;
                            }
                            if (t.sock.isConnected()) {
                                queue.add(new QStruct(newmsg, t));
                            }
                        }
                    }
                    return;
                } catch (StreamCorruptedException sce) {
                    continue;
                } catch (SocketException se) {
                    synchronized (this) {
                        for (String t : logedlist.keySet()) {
                            if (logedlist.get(t).equals(this)) {
                                close();
                                logedlist.remove(t);
                            }
                        }
                    }
                    String temp = "";
                    for (String k : logedlist.keySet()) {
                        temp = temp + k + "\t";
                    }
                    MSGStruct newmsg = new MSGStruct();
                    newmsg.setMSG(temp);
                    newmsg.setType(2);
                    newmsg.setOption(3);
                    for (Clientconnection t : logedlist.values()) {
                        if (t == null) {
                            break;
                        }
                        queue.add(new QStruct(newmsg, t));
                    }
                    return;
                } catch (IOException ex) {
                    Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server2.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("recieved  : " + msg.getSender() + "\t : " + msg.getMSG());
                QStruct que = new QStruct(msg, this);
                prequeue.add(que);
            }
        }
    }

}
