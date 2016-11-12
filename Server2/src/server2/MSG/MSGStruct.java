/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2.MSG;

import java.io.Serializable;
import java.net.SocketAddress;

/**
 *
 * @author progr
 */
public class MSGStruct implements Serializable{
    private int type=0; // 0 for ordinary,1 for group, 2 for processes
    private int option; //-1 for all errors /// 0: 0 for unicast, 1 for broadcast /// 1 :0 for request group list, 1 for group open, 2 for group in 3 for group change,
                        //4 for group out  /// 2 : 0 for authentication(check duplication),1 for token request, 2 for token released, 3 for queue info
    private String Sender;
    private String Recieve;
    private String MSG;
    private String GroupName;
    private boolean badge;

    public boolean getBadge() {
        return badge;
    }

    public void setBadge(boolean badge) {
        this.badge = badge;
    }

    public String getRecieve() {
        return Recieve;
    }

    public void setRecieve(String Recieve) {
        this.Recieve = Recieve;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
    
}