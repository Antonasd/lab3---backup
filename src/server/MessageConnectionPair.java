/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;

/**
 *
 * @author mrowlie
 */
public class MessageConnectionPair {
    Message m;
    HostedConnection c;
    
    public MessageConnectionPair(Message m, HostedConnection c) {
        this.m = m;
        this.c = c;
    }
}
