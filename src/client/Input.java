/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet;
import networking.Packet.InputPressed;
import networking.Packet.InputReleased;
import networking.Packet.ClientReady;

/**
 *
 * @author mrowlie
 */
public class Input implements Runnable {
    boolean running =  true;
    static ConcurrentLinkedQueue<InputContainer> inputQueue = new ConcurrentLinkedQueue<InputContainer>();
    static PlayerDisk[] localPlayers = new PlayerDisk[2];
    @Override
    public void run() {
        update();
    }
    
    private void update(){
        while(running){
            while(!inputQueue.isEmpty()){
                handleMessage(inputQueue.remove());
            }
        }
    }
    
    public static void addMessage(InputContainer inputContainer){
        inputQueue.add(inputContainer);      
    }
    
    public void handleMessage(InputContainer inputContainer){
        if(inputContainer instanceof ActionInputContainer){
            ActionInputContainer input = (ActionInputContainer) inputContainer;
            switch(input.name){
                //p1
                case "p1Up": 
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[0].diskID, (byte)0));
                    else NetWrite.addMessage(new InputReleased(localPlayers[0].diskID, (byte)0));
                    break;
                case "p1Down":
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[0].diskID, (byte)2));
                    else NetWrite.addMessage(new InputReleased(localPlayers[0].diskID, (byte)2));
                    break;
                case "p1Left":
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[0].diskID, (byte)1));
                    else NetWrite.addMessage(new InputReleased(localPlayers[0].diskID, (byte)1));
                    break;
                case "p1Right":
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[0].diskID, (byte)3));
                    else NetWrite.addMessage(new InputReleased(localPlayers[0].diskID, (byte)3));
                    break;
                //p2    
                case "p2Up":
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[1].diskID, (byte)0));
                    else NetWrite.addMessage(new InputReleased(localPlayers[1].diskID, (byte)0));
                    break;
                case "p2Down":
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[1].diskID, (byte)2));
                    else NetWrite.addMessage(new InputReleased(localPlayers[1].diskID, (byte)2));
                    break;
                case "p2Left":
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[1].diskID, (byte)1));
                    else NetWrite.addMessage(new InputReleased(localPlayers[1].diskID, (byte)1));
                    break;
                case "p2Right":
                    if(input.isPressed)NetWrite.addMessage(new InputPressed(localPlayers[1].diskID, (byte)3));
                    else NetWrite.addMessage(new InputReleased(localPlayers[1].diskID, (byte)3));
                    break;
                //Ready
                case "Ready":
                    if(input.isPressed && localPlayers[0] != null && localPlayers[1] != null){
                        NetWrite.addMessage(new ClientReady(localPlayers[0].diskID));
                        NetWrite.addMessage(new ClientReady(localPlayers[1].diskID));
                    }
                    break;
                case "Debug":
                    System.out.println("Player 1 : "+localPlayers[0].pos+"\nPlayer 2  : "+localPlayers[1].pos
                    +"\nPlayer 1 velocity : "+localPlayers[0].getVelocity()+"\nPlayer 2 velocity : "+localPlayers[1].getVelocity());
                    break;
            }
        }
        
        else if (inputContainer instanceof AnalogInputContainer){
            AnalogInputContainer input = (AnalogInputContainer) inputContainer;
            switch(input.name){
                //p1
                case "p1Up": localPlayers[0].moveNorth(input.tpf);
                    break;
                case "p1Down": localPlayers[0].moveSouth(input.tpf);
                    break;
                case "p1Left": localPlayers[0].moveWest(input.tpf);
                    break;
                case "p1Right": localPlayers[0].moveEast(input.tpf);
                    break;
                //p2    
                case "p2Up": localPlayers[1].moveNorth(input.tpf);
                    break;
                case "p2Down": localPlayers[1].moveSouth(input.tpf);
                    break;
                case "p2Left": localPlayers[1].moveWest(input.tpf);
                    break;
                case "p2Right": localPlayers[1].moveEast(input.tpf);
                    break;
            }
        }
        
    }
    
    public static void addPlayer(PlayerDisk player){
        if(localPlayers[0] == null) localPlayers[0] = player;
        else if(localPlayers[1] == null) localPlayers[1] = player;
        else System.out.println("Error! /nTwo local players already exist");    
    }
}
