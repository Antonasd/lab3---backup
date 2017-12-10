/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.network.Message;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.DiskUpdate;
import networking.Packet.ScoreUpdate;
import networking.Packet.TimeSync;
import networking.Packet.DisconnectClient;
import networking.Packet.TimeDiff;
import networking.Packet.UpdatePosDisk;

/**
 *
 * @author mrowlie
 */
public class Modeling{
    static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    
    boolean running = true;
    boolean scoreUpdate;
    private boolean timeDiffMeasured;
    
    float gameTimeElapsed = 0f;
    static float serverTimeDiff = 0f;
    
    BitmapFont font;
    
    BitmapText first;
    BitmapText second;
    BitmapText third;
    BitmapText timer;
    
    
    public void initialize(){
        font = Main.refAssetManager.loadFont("Interface/Fonts/Console.fnt");
        first = new BitmapText(font);
        second = new BitmapText(font);
        third = new BitmapText(font);
        timer = new BitmapText(font);
        
        Main.refGuiNode.attachChild(first);
        Main.refGuiNode.attachChild(second);
        Main.refGuiNode.attachChild(third);
        Main.refGuiNode.attachChild(timer);
        
        //set pos
        first.setLocalTranslation(5f, 400f, 0f);
        second.setLocalTranslation(5f, 370f, 0f);
        third.setLocalTranslation(5f, 340f, 0f);
        timer.setLocalTranslation(5f, 450f, 0f);
    }
    
    public void update(float tpf){
        while(!messageQueue.isEmpty()){
            handleMessage(messageQueue.remove());
        }            
        for(Disk d : Disk.disks){
            d.tick(tpf);
        }
        if(scoreUpdate){
            List<PlayerDisk> sortedPlayers = new ArrayList<>(PlayerDisk.playerMap.values());
            Collections.sort(sortedPlayers, scoreComparator);
            int amountOfPlayers = sortedPlayers.size();
            
            first.setText("1st: Player "+(sortedPlayers.get(amountOfPlayers-1).diskID-16)+" Score: "+sortedPlayers.get(amountOfPlayers-1).score);
            if(amountOfPlayers>1)second.setText("2nd: Player "+(sortedPlayers.get(amountOfPlayers-2).diskID-16)+" Score: "+sortedPlayers.get(amountOfPlayers-2).score);
            else second.setText("");
            if(amountOfPlayers>2)third.setText("3rd: Player "+(sortedPlayers.get(amountOfPlayers-3).diskID-16)+" Score: "+sortedPlayers.get(amountOfPlayers-3).score);
            else third.setText("");
            
            scoreUpdate = false;
        }
        //gameTimeElapsed+=tpf;
    }        
    
    public void handleMessage(Message message){
        if(message instanceof DiskUpdate) {
            DiskUpdate packet = (DiskUpdate) message;
            Disk disk = Disk.diskMap.get(packet.getDiskID());
            disk.setPosition(packet.getX(), packet.getY());
            disk.setVelocity(packet.getVX(), packet.getVY());
        
        } else if(message instanceof ScoreUpdate) {
            ScoreUpdate packet = (ScoreUpdate) message;
            PlayerDisk player = PlayerDisk.playerMap.get(packet.getPid());
            player.score = packet.getNewScore();
            scoreUpdate = true;
            
        } else if(message instanceof TimeSync) {
            TimeSync packet = (TimeSync) message;
            System.out.println("gameTime : "+packet.getTime());
            System.out.println("gameTimeElapsed : "+gameTimeElapsed);
            System.out.println("serverTimeDiff : "+serverTimeDiff);
            if(packet.getTime()+serverTimeDiff<30f)this.gameTimeElapsed = packet.getTime()+serverTimeDiff;
            else this.gameTimeElapsed = packet.getTime();
            timer.setText("Time : "+gameTimeElapsed);
            if(timeDiffMeasured){
                NetWrite.addMessage(new TimeSync(packet.getTime()));
                timeDiffMeasured = true;
            }
                        
        } else if(message instanceof DisconnectClient){
            DisconnectClient packet = (DisconnectClient)message;
            Disk.diskMap.remove(packet.getDiskID());
            
        } else if(message instanceof TimeDiff){
            TimeDiff packet = (TimeDiff)message;
            serverTimeDiff = packet.getDiff();
        } else if(message instanceof UpdatePosDisk){
            UpdatePosDisk packet = (UpdatePosDisk)message;
            PositiveDisk disk = (PositiveDisk)Disk.diskMap.get(packet.getDiskID());
            disk.subWorth();
        }       
    }
    
    public void startTimer(){
        gameTimeElapsed = serverTimeDiff;
        
    }
    

    public static void addMessage(Message message){
        messageQueue.add(message);
    }
    
    
    private final Comparator scoreComparator = new Comparator<PlayerDisk>(){
      @Override
      public int compare(PlayerDisk player1, PlayerDisk player2){
          return player1.score-player2.score;
      }  
    };
    
}
