/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author progr
 */
public class Group {

    private static final int USERLIMIT = 100;
    String groupName;
    ArrayList members = new ArrayList();

    Group(String name) {
        groupName = name;
    }
    boolean hasMember(String Name){
        if(members.contains(Name))
            return true;
        else
            return false;
    }

    void addMember(String Name) {
        members.add(Name);
    }

    void removeMember(String Name) {
        members.remove(Name);
    }
    String[] getmemberlist(){
        String[] list = new String[members.size()];
        for(int i=0;i<members.size();i++){
            list[i]=(String)members.get(i);
        }
        return list;
    }

    String getGroupName() {
        return groupName;
    }

    int getMemberNum() {
        return members.size();
    }
}
