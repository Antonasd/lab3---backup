/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mrowlie
 */
public class GameServer {
    
    protected Server server;
    
    protected Thread netReadThread;
    protected Thread netWriteThread;
    
    protected NetRead netRead;
    protected NetWrite netWrite;
    
    
    public GameServer(String gameName, int version, int port) {
        //Register packets
        Serializer.registerClass(networking.Packet.DiskUpdate.class);
        Serializer.registerClass(networking.Packet.ScoreUpdate.class);
        Serializer.registerClass(networking.Packet.TimeSync.class);
        Serializer.registerClass(networking.Packet.TimeDiff.class);
        Serializer.registerClass(networking.Packet.ChangeState.class);
        Serializer.registerClass(networking.Packet.ClientReady.class);
        Serializer.registerClass(networking.Packet.InitClient.class);
        Serializer.registerClass(networking.Packet.JoiningClient.class);
        Serializer.registerClass(networking.Packet.DisconnectClient.class);
        Serializer.registerClass(networking.Packet.InputPressed.class);
        Serializer.registerClass(networking.Packet.InputReleased.class);
        Serializer.registerClass(networking.Packet.UpdatePosDisk.class);
        
        

        //Start the server
        try {
            this.server = Network.createServer(gameName, version, port, port);
            this.server.start();
        } catch (IOException ex) {
            System.out.println("THLSEKOA");
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    public void startThreads() {
        
        this.netReadThread = new Thread(netRead = new NetRead(this));
        this.netReadThread.start();
        this.netWriteThread = new Thread(netWrite = new NetWrite(this));
        this.netWriteThread.start();
        
    }
    
    
    
    
    
}
