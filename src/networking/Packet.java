/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author mrowlie
 */
public class Packet {
    
    
    abstract public static class MyAbstractMessage extends AbstractMessage {
        
        public MyAbstractMessage() {
        }
    }
  
    
    @Serializable
    public static class DiskUpdate extends MyAbstractMessage {
        protected int diskID;
        protected float x; //horizontal position
        protected float y; //vertical position
        protected float vx; //horizontal velocity
        protected float vy; //vertical velocity
        
        public DiskUpdate(){
            
        }
        
        public DiskUpdate(int diskID, float x, float y, float vx, float vy) {
            this.diskID = diskID;
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }
        
        public int getDiskID() {
            return this.diskID;
        }
        public float getX() {
            return this.x;
        }
        public float getY() {
            return this.y;
        }
        public float getVX() {
            return this.vx;
        }
        public float getVY() {
            return this.vy;
        }
        
    }
    @Serializable
    public static class ScoreUpdate extends MyAbstractMessage {
        protected int pid;
        protected int newScore;
        
        public ScoreUpdate(){
            
        }
        
        public ScoreUpdate(int pid, int newScore) {
            this.pid = pid;
            this.newScore = newScore;
        }
        
        public int getPid() {
            return this.pid;
        }
        public int getNewScore() {
            return this.newScore;
        }
    }
    @Serializable
    public static class TimeSync extends MyAbstractMessage {
        protected float time;
        
        public TimeSync() {
            
        }
        
        public TimeSync(float time) {
            this.time = time;
        }
        
        public float getTime(){
            return this.time;
        }
        
    }
    @Serializable
    public static class TimeDiff extends MyAbstractMessage {
        protected float diff;
        
        public TimeDiff() {
            
        }
        
        public TimeDiff(float diff) {
            this.diff = diff;
        }
        
        public float getDiff() {
            return this.diff;
        }
        
    }
    @Serializable
    public static class ClientReady extends MyAbstractMessage {
        int diskID;
        
        public ClientReady() {
            
        }
        
        public ClientReady(int diskID) {
            this.diskID = diskID;
        }
        
        public int getDiskID() {
            return this.diskID;
        }
        
    }
    @Serializable
    public static class ChangeState extends MyAbstractMessage {
        protected byte state;
        
        public ChangeState() {
            
        }
        
        public ChangeState(byte state) {
            this.state = state;
        }
        
        public byte getState() {
            return this.state;
        }
        
    }
    @Serializable
    public static class InitClient extends MyAbstractMessage{
        int diskID;
        int startPos;
        public InitClient(){
            
        }
        
        public InitClient(int diskID, int startPos){
            this.diskID = diskID;
            this.startPos = startPos;
        }
        
        public int getDiskID() {
            return this.diskID;
        }
        
        public int getStartPos() {
            return this.startPos;
        }
    }
    @Serializable
    public static class JoiningClient extends MyAbstractMessage {
        int diskID;
        int startPos;
        
        public JoiningClient() {
            
        }
        
        public JoiningClient(int diskID, int startPos) {
            this.diskID = diskID;
            this.startPos = startPos;
        }
        
        public int getDiskID() {
            return this.diskID;
        }
        
        public int getStartPos() {
            return this.startPos;
        }
    }
    
    @Serializable
    public static class DisconnectClient extends MyAbstractMessage {
        int diskID;
        
        public DisconnectClient() {
            
        }
        
        public DisconnectClient(int diskID) {
            this.diskID = diskID;
        }
        
        public int getDiskID(){
            return this.diskID;
        }
        
    }
    
    @Serializable
    public static class InputPressed extends MyAbstractMessage {
        byte key;
        int diskID;
        
        public InputPressed() {
            
        }
        
        public InputPressed(int diskID, byte key) {
            this.key = key;
            this.diskID = diskID;
        }
        
        public byte getKey(){
            return this.key;
        }
        
        public int getDiskID() {
            return this.diskID;
        }
    }
    
    @Serializable
    public static class InputReleased extends MyAbstractMessage {
        //0 = up, 1 = left, 2 = down, 3 = right
        byte key;
        int diskID;
        
        public InputReleased() {
            
        }
        
        public InputReleased(int diskID, byte key) {
            this.key = key;
            this.diskID = diskID;
        }
        
        public byte getKey(){
            return this.key;
        }
        
        public int getDiskID() {
            return this.diskID;
        }
    }
    @Serializable
    public static class UpdatePosDisk extends MyAbstractMessage {
        int diskID;
        
        public UpdatePosDisk() {
            
        }
        
        public UpdatePosDisk(int diskID) {
            this.diskID = diskID;
        }
        
        public int getDiskID() {
            return this.diskID;
        }
    }
    @Serializable
    public static class RoundWinner extends MyAbstractMessage{
        int playerID;
        
        public RoundWinner(){    
            
        }
        
        public RoundWinner(int playerID){
            this.playerID = playerID;
        }
        
        public int getPlayerID(){
            return playerID;
        }
    }
}


  
