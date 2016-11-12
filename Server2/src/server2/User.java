/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2;

/**
 *
 * @author progr
 */
public class User {
    private String Name;
    private int group=-1; // -1 for alone, other number for indicate other group
    private String addr;
    private int port;

    public int getGroup() {
        return group;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getName() {
        return Name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    User(String Name){
        this.Name=Name;
    }
}
