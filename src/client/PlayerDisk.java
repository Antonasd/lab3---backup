/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mrowlie
 */
public class PlayerDisk extends Disk{
    static ArrayList<Vector2f> playerPos = new ArrayList();
    
    
    int score;
    
    BitmapFont font;
    BitmapText text;
    
    public static int playerAmount;
    public static ConcurrentHashMap<Integer, PlayerDisk> playerMap = new ConcurrentHashMap();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public PlayerDisk(AssetManager assetManager, int id, int posIndex) {
        super(assetManager, ColorRGBA.Blue, Main.PLAYER_R, id);
        if(playerPos.isEmpty()){
            PlayerDisk.playerPos.add(new Vector2f(-Main.PLAYER_COORD, Main.PLAYER_COORD));
            PlayerDisk.playerPos.add(new Vector2f(0, Main.PLAYER_COORD));
            PlayerDisk.playerPos.add(new Vector2f(Main.PLAYER_COORD, Main.PLAYER_COORD));
            PlayerDisk.playerPos.add(new Vector2f(-Main.PLAYER_COORD, 0));
            PlayerDisk.playerPos.add(new Vector2f(0, 0));
            PlayerDisk.playerPos.add(new Vector2f(Main.PLAYER_COORD, 0));
            PlayerDisk.playerPos.add(new Vector2f(-Main.PLAYER_COORD, -Main.PLAYER_COORD));
            PlayerDisk.playerPos.add(new Vector2f(0, -Main.PLAYER_COORD));
            PlayerDisk.playerPos.add(new Vector2f(Main.PLAYER_COORD, -Main.PLAYER_COORD));
        }
        
        score = 0;
        font = assetManager.loadFont("Interface/Fonts/Console.fnt");
        text = new BitmapText(font, false);
        text.setSize(26);
        text.setColor(ColorRGBA.White);
        if(id > 15) {
            text.setText("p" + (id - 16));
            
        }else{
            text.setText("p" + (id));
        }
        text.setQueueBucket(RenderQueue.Bucket.Transparent);
        this.diskNode.attachChild(text);
        text.setLocalTranslation(- text.getHeight() / 2, text.getHeight() / 2, Main.FRAME_THICKNESS + 1f);
        setPosition(playerPos.get(posIndex));
        PlayerDisk.playerMap.put(id, this);
        
        
        
    }
    
    public void addScore(int amount) {
        this.score += amount;
        
    }
    
    public void removeScore(int amount) {
        this.score -= amount;
    }
    
    public synchronized void moveNorth(float tpf){
        this.setVelocity(this.getVelocity().getX(), this.getVelocity().getY() + 500 * tpf);
    }
    
    public synchronized void moveSouth(float tpf){
        this.setVelocity(this.getVelocity().getX(), this.getVelocity().getY() - 500 * tpf);
    }
    
    public synchronized void moveEast(float tpf){
        this.setVelocity(this.getVelocity().getX() + 500 * tpf, this.getVelocity().getY());
    }
    
    public synchronized void moveWest(float tpf){
        this.setVelocity(this.getVelocity().getX() - 500 * tpf, this.getVelocity().getY());
    }
    
}
