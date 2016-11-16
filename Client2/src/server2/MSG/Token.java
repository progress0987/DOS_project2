/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2.MSG;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Holics
 */
public class Token implements Serializable {

    int destination;
    int source;
    int type; // 0 for broadcast, 1 for require, 2 for passing, 3 for ack
    int lastindex;
    LinkedList queue = new LinkedList();

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

    public LinkedList getQueue() {
        return queue;
    }

    public void setQueue(LinkedList queue) {
        this.queue = queue;
    }

}
