
import com.sun.corba.se.impl.io.IIOPInputStream;
import com.sun.corba.se.impl.io.OutputStreamHook;
import server2.MSG.MSGStruct;
import com.sun.glass.events.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author progr
 */
public class ChatMain extends javax.swing.JFrame {

    /**
     * Creates new form ChatMain
     */
    static String name;
    static ObjectInputStream in, locin;
    static ObjectOutputStream out, locout;
    static String mygroup;
    static LinkedList packet = new LinkedList();
    static boolean badge = false;
    static int id = -1;
    static int last=0;
    static int port = 5555;
    static MulticastSocket mcs;
    static DatagramSocket sock;
    static InetAddress local;
//    static HashMap<String,Integer> matching = new HashMap<String,Integer>();
//    static LinkedList queue = new LinkedList();
    static int[] queue = new int[5];
    static LinkedList tokenqueue = new LinkedList();

    public class Token implements Serializable {

        int destination;
        int source;
        int type; // 0 for broadcast, 1 for require, 2 for passing, 3 for ack
        int lastindex;
        int[] queue = new int[5];

        public int getLastindex() {
            return lastindex;
        }

        public void setLastindex(int lastindex) {
            this.lastindex = lastindex;
        }


        public int getDestination() {
            return destination;
        }

        public void setDestination(int destination) {
            this.destination = destination;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int[] getQueue() {
            return queue;
        }

        public void setQueue(int[] queue) {
            this.queue = queue;
        }
        private int pop(){
            int ret=0;
            if(queue[0]>0){
                ret=queue[0];
                for(int i=0;i<queue.length;i++){
                    int j=i+1;
                    queue[i]=queue[j];
                    if(j+1<queue.length&&j+1==0){
                        queue[j]=0;
                        break;
                    }    
                    if(queue[i]<=0)
                        break;
                    if(j>queue.length)
                        break;
                }
            }
            return ret;
        }
    }
        private int pop(){
            int ret=0;
            if(queue[0]>0){
                ret=queue[0];
                for(int i=0;i<queue.length;i++){
                    int j=i+1;
                    queue[i]=queue[j];
                    if(j+1<queue.length&&j+1==0){
                        queue[j]=0;
                        break;
                    }    
                    if(queue[i]<=0)
                        break;
                    if(j>queue.length)
                        break;
                }
            }
            return ret;
        }

    public ChatMain(String Name, ObjectInputStream in, ObjectOutputStream out) {
//        boolean okay = false;
        try {
//            while (!okay) {
//                try {
//                    port = Integer.parseInt(JOptionPane.showInputDialog("Enter your local port"));
//                    okay = true;
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(this, "You need to input a number");
//                    continue;
//                }
//            }
            local = InetAddress.getByName("224.0.0.1");
            sock = new DatagramSocket();
            this.out = out;
            this.in = in;
            MSGStruct pack = new MSGStruct();
            pack.setSender(Name);
            pack.setType(2);
            pack.setOption(4);
            this.out.writeObject(pack);
            this.out.flush();
            mcs = new MulticastSocket(5555);
            mcs.joinGroup(local);
        } catch (IOException io) {
        }
        this.name = Name;
        initComponents();
        this.Name.setText(name);
        new recvthread().start();
        new locrecvthread().start();
        new procthread().start();
//        new refthread().start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatView = new javax.swing.JTextArea();
        ChatText = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        GroupList = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        UserList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Dest_Dropdown = new javax.swing.JComboBox();
        Send = new javax.swing.JButton();
        CreateGroupButton = new javax.swing.JButton();
        LeaveGroupButton = new javax.swing.JButton();
        JoinGroupButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        GroupUser = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        Acquire_Token = new javax.swing.JButton();
        Send_Token = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ChatView.setEditable(false);
        ChatView.setColumns(20);
        ChatView.setRows(5);
        jScrollPane1.setViewportView(ChatView);

        ChatText.setText("Enter Text here");
        ChatText.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ChatText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ChatTextFocusGained(evt);
            }
        });
        ChatText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChatTextActionPerformed(evt);
            }
        });
        ChatText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChatTextKeyPressed(evt);
            }
        });

        GroupList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings={""};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(GroupList);

        UserList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings={""};
            public void setStrings(String[] items){this.strings=items;}
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(UserList);

        jLabel1.setText("Hello");

        Name.setText("");

        jLabel3.setText("Made by SI Kim");

        Dest_Dropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BroadCast","" }));
        Dest_Dropdown.setSelectedIndex(0);
        Dest_Dropdown.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Dest_Dropdown.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Dest_DropdownItemStateChanged(evt);
            }
        });
        Dest_Dropdown.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                Dest_DropdownPopupMenuWillBecomeVisible(evt);
            }
        });
        Dest_Dropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dest_DropdownActionPerformed(evt);
            }
        });

        Send.setText("Send");
        Send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SendMouseClicked(evt);
            }
        });

        CreateGroupButton.setText("Create Group");
        CreateGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreateGroupButtonMouseClicked(evt);
            }
        });

        LeaveGroupButton.setText("Leave Group");
        LeaveGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LeaveGroupButtonMouseClicked(evt);
            }
        });

        JoinGroupButton.setText("Join Group");
        JoinGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JoinGroupButtonMouseClicked(evt);
            }
        });

        GroupUser.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(GroupUser);

        jLabel2.setText("Group User Details");

        Acquire_Token.setText("Acquire Token");
        Acquire_Token.setEnabled(false);
        Acquire_Token.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Acquire_TokenMouseClicked(evt);
            }
        });

        Send_Token.setText("Send Token");
        Send_Token.setEnabled(false);
        Send_Token.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Send_TokenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(Dest_Dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ChatText, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(Send))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JoinGroupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CreateGroupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LeaveGroupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Name))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Acquire_Token)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Send_Token))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ChatText, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Send))
                            .addComponent(Dest_Dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CreateGroupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JoinGroupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LeaveGroupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Acquire_Token, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Send_Token, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChatTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChatTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChatTextActionPerformed

    private void ChatTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ChatTextFocusGained
        // TODO add your handling code here:
        ChatText.setText("");
    }//GEN-LAST:event_ChatTextFocusGained

    private void ChatTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChatTextKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            new sendmsg().start();
//            sendmsg();
        }
    }//GEN-LAST:event_ChatTextKeyPressed

    private void SendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendMouseClicked
        // TODO add your handling code here:
        new sendmsg().start();
//        sendmsg();
    }//GEN-LAST:event_SendMouseClicked

    private void Dest_DropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dest_DropdownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dest_DropdownActionPerformed

    private void Dest_DropdownPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_Dest_DropdownPopupMenuWillBecomeVisible
        // TODO add your handling code here:
    }//GEN-LAST:event_Dest_DropdownPopupMenuWillBecomeVisible

    private void Dest_DropdownItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Dest_DropdownItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_Dest_DropdownItemStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        MSGStruct pack = new MSGStruct();
        pack.setSender(name);
        pack.setType(2);
        pack.setOption(5);
        try {
            out.writeObject(pack);
            in.close();
            out.close();
            sleep(20);
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
        }


    }//GEN-LAST:event_formWindowClosing

    private void CreateGroupButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CreateGroupButtonMouseClicked
        // TODO add your handling code here:
        creategroup();
    }//GEN-LAST:event_CreateGroupButtonMouseClicked

    private void LeaveGroupButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LeaveGroupButtonMouseClicked
        // TODO add your handling code here:
        leavegroup();
    }//GEN-LAST:event_LeaveGroupButtonMouseClicked

    private void JoinGroupButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JoinGroupButtonMouseClicked
        // TODO add your handling code here:
        if (GroupList.getSelectedValue() != null) {
            leavegroup();
            getingroup();
        }
    }//GEN-LAST:event_JoinGroupButtonMouseClicked

    private void Acquire_TokenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Acquire_TokenMouseClicked
        // TODO add your handling code here:
        if (Acquire_Token.isEnabled()) {
            acquiretoken();
            JOptionPane.showMessageDialog(null, "You requested token.");
            Acquire_Token.setEnabled(false);
            Send_Token.setEnabled(false);
        }
    }//GEN-LAST:event_Acquire_TokenMouseClicked

    private void Send_TokenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Send_TokenMouseClicked
        // TODO add your handling code here:
        if (Send_Token.isEnabled()) {
            sendtoken();
            JOptionPane.showMessageDialog(null, "You have send the token.");
            Acquire_Token.setEnabled(true);
            Send_Token.setEnabled(false);
        }
    }//GEN-LAST:event_Send_TokenMouseClicked

    private void addqueue(int i){
        if(last>queue.length)
            return;
        queue[last++]=i;
    }
    private boolean equal(int[] a, int[]b){
        boolean ret=true;
        for(int i=0;i<a.length;i++){
            if(a[i]!=b[i])
                ret=false;
        }
        return ret;
    }
    private void acquiretoken() {
        boolean contains=false;
        for(int i=0;i<queue.length;i++){
            if(queue[i]==id)
                contains=true;
        }
        if (!contains) {
            queue[last++]=id;
            System.out.println("q ad");
            Token pack = new Token();
            pack.setSource(id);
            pack.setLastindex(last);
            pack.setType(1);
            pack.setQueue(queue);
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(pack);
                byte[] data = bos.toByteArray();
                DatagramPacket send = new DatagramPacket(data, data.length, local, port);
                sock.send(send);
            } catch (IOException io) {

            }
        }
    }

    private void sendtoken() {
        Token pack = new Token();
        pack.setQueue(queue);
        pack.setDestination(pop());
        pack.setSource(id);
        pack.setLastindex(--last);
        pack.setType(2);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(pack);
            byte[] data = bos.toByteArray();
            DatagramPacket send = new DatagramPacket(data, data.length, local, port);
            sock.send(send);
        } catch (IOException io) {

        }
    }

    /**
     * @param args the command line arguments
     */
    private void creategroup() {
        if (ChatText.getText().equals("") || ChatText.getText().equals("Enter Text here")) {
            JOptionPane.showMessageDialog(null, "You have to put your groupname on chat to create a new group");
            return;
        }
        if (mygroup == null) {
            MSGStruct packet = new MSGStruct();
            packet.setType(1);
            packet.setOption(1);
            packet.setSender(name);
            packet.setGroupName(ChatText.getText());
            ChatText.setText("");
            try {
                out.writeObject(packet);
            } catch (IOException io) {
            }
        } else {
            leavegroup();
            creategroup();
        }
    }

    private void succrtgrp(String groupname) {
        mygroup = groupname;
        Dest_Dropdown.removeAllItems();
        Dest_Dropdown.addItem("Broadcast");
        Dest_Dropdown.addItem(mygroup);
        Dest_Dropdown.setSelectedIndex(1);
        badge = true;
        Acquire_Token.setEnabled(false);
        Send_Token.setEnabled(true);
    }

    private void leavegroup() {
        if (mygroup == null) {
            return;
        }
        MSGStruct packet = new MSGStruct();
        packet.setType(1);
        packet.setOption(4);
        packet.setSender(name);
        packet.setGroupName(mygroup);
        try {
            out.writeObject(packet);
        } catch (IOException io) {
        }
        mygroup = null;
        Dest_Dropdown.removeAllItems();
        Dest_Dropdown.addItem("Broadcast");
        Dest_Dropdown.addItem("");
        Dest_Dropdown.setSelectedIndex(0);
        if (badge) {
            Send_TokenMouseClicked(null);
            Acquire_Token.setEnabled(false);
        }
        GroupUser.setListData(new String[]{""});
    }

    private void getingroup() {
        MSGStruct packet = new MSGStruct();
        packet.setType(1);
        packet.setSender(name);
        packet.setOption(2);
        if (GroupList.getSelectedValue() == null) {
            return;
        }
        if (GroupList.getSelectedValue() != null) {
            packet.setGroupName((String) GroupList.getSelectedValue());
        } else if (GroupList.getSelectedValue().equals(mygroup)) {
            return;
        }
        packet.setMSG(mygroup);
        try {
            out.writeObject(packet);
        } catch (IOException io) {
        }
        Acquire_Token.setVisible(true);
    }

    private void succnggrp() {
        Dest_Dropdown.removeAllItems();
        Dest_Dropdown.addItem("Broadcast");
        Dest_Dropdown.addItem(mygroup);
        Dest_Dropdown.setSelectedIndex(1);
        Acquire_Token.setEnabled(true);

    }

    private class sendmsg extends Thread {

        public void run() {
            String msg = ChatText.getText();
            if (msg.isEmpty()) {
                return;
            }
            ChatText.setText("");
            MSGStruct pack = new MSGStruct();
            pack.setSender(name);
            pack.setMSG(msg);
            pack.setBadge(badge);
            pack.setType(0);
            switch (Dest_Dropdown.getSelectedIndex()) {
                case 0:
                    pack.setOption(1);
                    break;
                case 1:
                    pack.setOption(2);
                    if (Dest_Dropdown.getItemAt(1).equals("")) {
                        pack.setOption(1);
                        Dest_Dropdown.setSelectedIndex(0);
                    } else {
                        pack.setGroupName((String) Dest_Dropdown.getItemAt(1));
                    }
                    break;
                case 2:
                    pack.setOption(0);
                    if (Dest_Dropdown.getItemAt(2).equals("")) {
                        pack.setOption(1);
                        Dest_Dropdown.setSelectedIndex(0);
                    } else {
                        pack.setRecieve((String) Dest_Dropdown.getItemAt(2));
                        ChatView.append("(W to " + pack.getRecieve() + ")\t : " + pack.getMSG() + "\n");
                    }
                    break;
            }
            try {
                synchronized (out) {
                    out.writeObject(pack);
                    out.flush();
                }
            } catch (IOException io) {

            }
        }
    }

    private class recvthread extends Thread {

        public void run() {
            synchronized (in) {
                while (true) {
                    MSGStruct msg = new MSGStruct();
                    try {
                        msg = (MSGStruct) in.readObject();
                    } catch (EOFException eof) {
                        return;
                    } catch (StreamCorruptedException sce) {
                        System.err.println(sce);
                        continue;
                    } catch (SocketException se) {
                        return;
                    } catch (IOException ex) {
                        Logger.getLogger(ChatMain.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChatMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (msg == null) {
                        try {
                            in.reset();
                        } catch (IOException io) {
                        }
                    } else {
                        packet.add(msg);
                    }
                }
            }
        }
    }

    private class locrecvthread extends Thread {

        public void run() {
            byte[] indata = new byte[1024];
            while (true) {
                try {
                    DatagramPacket inpack = new DatagramPacket(indata, indata.length);
                    mcs.receive(inpack);
                    byte[] data = inpack.getData();
                    ByteArrayInputStream bin = new ByteArrayInputStream(data);
                    ObjectInputStream is = new ObjectInputStream(bin);
                    tokenqueue.add((Token) is.readObject());
                } catch (IOException io) {
                } catch (ClassNotFoundException ex) {
                    System.err.println("packet crashed");
                }
            }
        }
    }

    private class locprocthread extends Thread {

        public void run() {
            Token pop;
            while (true) {
                if (tokenqueue.size() > 0) {
                    pop = (Token) tokenqueue.pop();
                    switch (pop.getType()) {
                        case 0:
                        case 1:
                            addqueue(pop.getSource());
                            
                            if (pop.getLastindex()>last) {
                                queue = pop.getQueue();
                                last = pop.getLastindex();
                            }
                            break;
                        case 2:
                            if (pop.destination == id) {
                                badge=true;
                                last=pop.getLastindex();
                                queue=pop.getQueue();
                                Token pack = new Token();
                                pack.setQueue(queue);
                                pack.setSource(id);
                                pack.setDestination(pop.source);
                                pack.setType(3);
                                try {
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    ObjectOutputStream os = new ObjectOutputStream(bos);
                                    os.writeObject(pack);
                                    byte[] data = bos.toByteArray();
                                    DatagramPacket send = new DatagramPacket(data, data.length, local, port);
                                    sock.send(send);
                                } catch (IOException io) {

                                }
                            }
                            break;
                        case 3:
                            if (pop.getDestination() == id) {
                                badge = false;
                                if(!(last<=pop.getLastindex()))
                                    last=pop.getLastindex();
                                Acquire_Token.setEnabled(true);
                                Send_Token.setEnabled(badge);
                            } else if (pop.getSource() == queue[0]) {
                                queue=pop.getQueue();
                            }
                    }// 0 for broadcast, 1 for require, 2 for sending, 3 for ack
                }
            }
        }
    }

    private class procthread extends Thread {

        public void run() {
            while (true) {
                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                }
                if (!packet.isEmpty()) {
                    MSGStruct msg = (MSGStruct) packet.pop();
                    System.out.println("Queue is handled");
                    if (msg == null) {
                        continue;
                    }
                    System.out.println(msg.getType() + " : " + msg.getOption());
                    switch (msg.getType()) {
                        case 0:
                            switch (msg.getOption()) {
                                case 0:
                                    ChatView.append("(W)\t" + msg.getSender() + "\t : " + msg.getMSG() + "\n");
                                    break;
                                case 1:
                                    ChatView.append("(B)\t" + msg.getSender() + "\t : " + msg.getMSG() + "\n");
                                    break;
                                case 2:
                                    ChatView.append("(G)\t" + msg.getSender() + "\t : " + msg.getMSG() + "\n");
                                    break;
                                default:
                                    ChatView.append("(E)\t" + msg.getSender() + "\t : " + msg.getMSG() + "\n");
                                    break;
                            }
                            break;
                        case 1:
                            switch (msg.getOption()) {
                                case 0:
                                    StringTokenizer st;
                                    String[] items;
                                    st = new StringTokenizer(msg.getMSG());
                                    items = new String[st.countTokens()];
                                    int i = 0;
                                    while (st.hasMoreTokens()) {
                                        items[i++] = st.nextToken();
                                    }
//                                    DefaultListModel model = new DefaultListModel();
//                                    for (String j : items) {
//                                        model.addElement(j);
//                                    }
                                    GroupList.setListData(items);
                                    break;
                                case 1:
                                    JOptionPane.showMessageDialog(null, msg.getMSG());
                                    badge = true;
                                    succrtgrp(msg.getGroupName());
                                    break;
                                case 2:
                                    JOptionPane.showMessageDialog(null, msg.getMSG());
                                    mygroup = msg.getGroupName();
                                    succnggrp();
                                    break;
                                case 3:
                                    StringTokenizer sto;
                                    String[] lists;
                                    sto = new StringTokenizer(msg.getMSG());
                                    lists = new String[sto.countTokens()];
                                    int j = 0;
                                    while (sto.hasMoreTokens()) {
                                        lists[j++] = sto.nextToken();
                                    }
                                    GroupUser.setListData(lists);
                                    break;
                                case 4:
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, msg.getMSG());
                                    break;
                            }
                            break;
                        case 2:
                            switch (msg.getOption()) {
                                case 3:
                                    StringTokenizer st;
                                    String[] items;
                                    st = new StringTokenizer(msg.getMSG());
                                    items = new String[st.countTokens()];
                                    int i = 0;
                                    while (st.hasMoreTokens()) {
                                        items[i++] = st.nextToken();
                                    }
                                    UserList.setListData(items);
                                    break;
                                case 4:
                                    id = Integer.parseInt(msg.getMSG());
                                    break;
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, msg.getMSG());
                            break;

                    }
                }
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Acquire_Token;
    private javax.swing.JTextField ChatText;
    protected static javax.swing.JTextArea ChatView;
    private javax.swing.JButton CreateGroupButton;
    private static javax.swing.JComboBox Dest_Dropdown;
    protected static javax.swing.JList GroupList;
    private javax.swing.JList GroupUser;
    private javax.swing.JButton JoinGroupButton;
    private javax.swing.JButton LeaveGroupButton;
    private javax.swing.JLabel Name;
    private javax.swing.JButton Send;
    private javax.swing.JButton Send_Token;
    protected static javax.swing.JList UserList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

}
