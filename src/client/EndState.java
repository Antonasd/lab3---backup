/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.network.Message;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author mrowlie
 */
public class EndState extends BaseAppState {

    static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    
    @Override
    public void update(float tpf) {
        while(!messageQueue.isEmpty()){
            handleMessage(messageQueue.remove());
        }
    }
    @Override
    protected void initialize(Application app) {
        
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        resetGame();
    }
    
    private void handleMessage(Message message){
        if(true){
            
        }
        
        else{
            System.out.println("Error : Unhandled message");
        }
        
    }
    
    private void resetGame() {
        
        ArrayList<Vector2f> positivePos = new ArrayList();
        ArrayList<Vector2f> negativePos = new ArrayList();
        
        positivePos.add(new Vector2f(- Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD));
        positivePos.add(new Vector2f(0, Main.POSNEG_MAX_COORD));
        positivePos.add(new Vector2f(Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD));
        positivePos.add(new Vector2f( - Main.POSNEG_MAX_COORD, 0));
        positivePos.add(new Vector2f( Main.POSNEG_MAX_COORD, 0));
        positivePos.add(new Vector2f( - Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD));
        positivePos.add(new Vector2f(0, - Main.POSNEG_MAX_COORD));
        positivePos.add(new Vector2f(Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD));
                
        negativePos.add(new Vector2f(- Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD));
        negativePos.add(new Vector2f(- Main.POSNEG_MAX_COORD, -Main.POSNEG_BETWEEN_COORD));
        negativePos.add(new Vector2f(- Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD));
        negativePos.add(new Vector2f(Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD));
        negativePos.add(new Vector2f(Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD));
        negativePos.add(new Vector2f(Main.POSNEG_MAX_COORD, - Main.POSNEG_BETWEEN_COORD));
        negativePos.add(new Vector2f(Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD));
        negativePos.add(new Vector2f(- Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD));
        
        Random rand = new Random();
        
        for(Disk d : Disk.disks) {
            if(d.getClass() == PositiveDisk.class) {
                PositiveDisk pd = (PositiveDisk) d;
                Vector2f newPos = positivePos.remove(positivePos.size() - 1);
                pd.reset();
                pd.setPosition(newPos);
                pd.randomizeVelocity();
                
            }
            if(d.getClass() == NegativeDisk.class) {
                NegativeDisk nd = (NegativeDisk) d;
                Vector2f newPos = negativePos.remove(negativePos.size() - 1);
                nd.setPosition(newPos);
                nd.randomizeVelocity();
                
            }            
        }
    }
    
}
