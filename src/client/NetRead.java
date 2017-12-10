/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.ChangeState;
import networking.Packet.DiskUpdate;
import networking.Packet.InitClient;
import networking.Packet.JoiningClient;
import networking.Packet.RoundWinner;
import networking.Packet.ScoreUpdate;
import networking.Packet.TimeDiff;
import networking.Packet.TimeSync;
import networking.Packet.UpdatePosDisk;

/**
 *
 * @author mrowlie
 */
public class NetRead implements Runnable, MessageListener<Client> {
    
    boolean exit = false;
    
    ConcurrentLinkedQueue<Message> messageQueue;
    
    public NetRead(){
        messageQueue = new ConcurrentLinkedQueue<>();
    }
    
    @Override
    public void run() {
        initialize();
        update();
        destroy();
    }
    
    private void initialize(){
        
    }
    
    private void update(){
        while(!this.exit) {
            if(!messageQueue.isEmpty()){
                Message message = messageQueue.remove();
                this.handlePacket(message);
                
                
            }
        }
    }
    
    private void handlePacket(Message message) {
        if(message instanceof DiskUpdate){
            Modeling.addMessage(message);
            
        } else if(message instanceof ScoreUpdate) {
            
            Modeling.addMessage(message); 
            
        } else if(message instanceof TimeSync) {
            
            Modeling.addMessage(message);
            
        } else if(message instanceof TimeDiff) {
            
            Modeling.addMessage(message);
            
        } else if(message instanceof ChangeState) {
            ChangeState packet = (ChangeState)message;
            System.out.println("State : "+packet.getState());
            switch(packet.getState()){
                case 0:
                    Main.gameState.setEnabled(false);
                    Main.initState.setEnabled(true);
                    Main.endState.setEnabled(false);
                    break;
                case 1:
                    Main.gameState.setEnabled(true);
                    Main.initState.setEnabled(false);
                    Main.endState.setEnabled(false);
                    break;
                case 2:
                    Main.gameState.setEnabled(false);
                    Main.initState.setEnabled(false);
                    Main.endState.setEnabled(true);
                    break;
            } 
            
        } else if (message instanceof InitClient){
            InitState.addMessage(message);
            
        } else if (message instanceof JoiningClient){
            InitState.addMessage(message);
        
        } else if (message instanceof UpdatePosDisk) {
            Modeling.addMessage(message);
            
        } else if (message instanceof RoundWinner) {
            EndState.addMessage(message);
        }
        
        
    }
    
    private void destroy(){
        
    }
    

    @Override
    public void messageReceived(Client source, Message m) {
        messageQueue.add(m);
    }
}
