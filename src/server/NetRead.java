/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.Packet.ClientReady;
import networking.Packet.InputPressed;
import networking.Packet.InputReleased;
import networking.Packet.MyAbstractMessage;
import networking.Packet.TimeDiff;
import networking.Packet.TimeSync;

/**
 *
 * @author mrowlie
 */
public class NetRead implements Runnable, MessageListener<HostedConnection>, ConnectionListener {

    GameServer server;
    
    boolean exit = false;
    
    static ConcurrentLinkedQueue<MessageConnectionPair> messageQueue;
    
    public NetRead(GameServer server) {
        messageQueue = new ConcurrentLinkedQueue();
        this.server = server;
    }
    
    private void initialize() {
        this.server.server.addMessageListener(this);
        this.server.server.addConnectionListener(this);
    }
    
    private void update() {
        while(!this.exit) {
            if(!messageQueue.isEmpty()) {
                System.out.println("Message recived");
                MessageConnectionPair pair = NetRead.messageQueue.remove();
                MyAbstractMessage m = (MyAbstractMessage) pair.m;
                if(m instanceof TimeSync) {
                    TimeSync p = (TimeSync) m;
                    float diff = (Main.timeElapsed - p.getTime()) / 2;
                    NetWrite.sendTimeDiff(diff, Filters.in(pair.c));
                }
                if(m instanceof ClientReady) {
                    ClientReady p = (ClientReady) m;
                    Disk disk = Disk.diskMap.get(p.getDiskID());
                    if (disk instanceof PlayerDisk) {
                        PlayerDisk player = (PlayerDisk) disk;
                        player.ready = true;
                        
                    }
                }
                if(m instanceof InputPressed) {
                    InputPressed p = (InputPressed) m;
                    Disk disk = Disk.diskMap.get(p.getDiskID());
                    if(disk instanceof PlayerDisk) {
                        PlayerDisk player = (PlayerDisk) disk;
                        player.keyPressed[p.getKey()] = true;
                    }
                }
                if(m instanceof InputReleased) {
                    InputReleased p = (InputReleased) m;
                    Disk disk = Disk.diskMap.get(p.getDiskID());
                    if(disk instanceof PlayerDisk) {
                        PlayerDisk player = (PlayerDisk) disk;
                        player.keyPressed[p.getKey()] = false;
                    }
                    
                }
                
            } else {
                try {
                    Thread.sleep((long) 10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NetRead.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void destroy() {
        this.server.server.removeMessageListener(this);
    }
    
    @Override
    public void run() {
        initialize();
        update();
        destroy();
    }
    
    @Override
    public void messageReceived(HostedConnection source, Message m) {
        NetRead.messageQueue.add(new MessageConnectionPair(m, source));
    }

    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
        PlayerDisk player1 = new PlayerDisk(conn);
        PlayerDisk player2 = new PlayerDisk(conn);
        
        NetWrite.syncTime(Filters.in(conn));
        
        Random rand = new Random();
        
        int random = rand.nextInt(PlayerDisk.busyPos.length);
        while(PlayerDisk.busyPos[random]){
            random = rand.nextInt(PlayerDisk.busyPos.length);
            
        }
        PlayerDisk.busyPos[random] = true;
        player1.setPosition(PlayerDisk.playerPos.get(random));
        NetWrite.initClient(player1.diskID, random, Filters.in(conn));
        
        random = rand.nextInt(PlayerDisk.busyPos.length);
        while(PlayerDisk.busyPos[random]){
            random = rand.nextInt(PlayerDisk.busyPos.length);
            
        }
        PlayerDisk.busyPos[random] = true;
        player2.setPosition(PlayerDisk.playerPos.get(random));
        NetWrite.initClient(player2.diskID, random, Filters.in(conn));
        
        player1.startingPos = player1.pos;
        player2.startingPos = player2.pos;
        
        //inform other clients
        NetWrite.joiningClient(player1.diskID, PlayerDisk.playerPos.indexOf(player1.pos), Filters.notIn(conn));
        NetWrite.joiningClient(player2.diskID, PlayerDisk.playerPos.indexOf(player2.pos), Filters.notIn(conn));
        //inform connector of other clients
        for(PlayerDisk player : PlayerDisk.playerDisks) {
            if(player != player1 && player != player2) {
                NetWrite.joiningClient(player.diskID, PlayerDisk.playerPos.indexOf(player.pos), Filters.in(player1.conn, player2.conn));
            }
        }
        
        
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        Enumeration<Disk> players = PlayerDisk.diskMap.elements();
        while(players.hasMoreElements()) {
            Disk disk = players.nextElement();
            if(disk instanceof PlayerDisk){
                PlayerDisk player = (PlayerDisk) disk;
                if(player.conn.getId() == conn.getId()) {
                    PlayerDisk.busyPos[(player.diskID - 16)] = false;
                    Disk.disks.remove(player);
                    Disk.diskMap.remove(player.diskID);
                    PlayerDisk.playerDisks.remove(player);
                    NetWrite.disconnectClient(player.diskID, null);
                }
            }
        }
    }
    
}
