/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.network.Message;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.RoundWinner;

/**
 *
 * @author mrowlie
 */
public class EndState extends BaseAppState {

    private static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    BitmapFont font;
    BitmapText readyText;
    BitmapText winnerText;
    @Override
    public void update(float tpf){
        if(!messageQueue.isEmpty())handleMessage(messageQueue.remove());
    }
    
    @Override
    protected void initialize(Application game) {
        resetGame();
        font = Main.refAssetManager.loadFont("Interface/Fonts/Console.fnt");
        readyText = new BitmapText(font);
        winnerText = new BitmapText(font);
        readyText.setText("Press 'R' to ready-up");
        //set pos
        readyText.setLocalTranslation(5f, 300f, 0);
        winnerText.setLocalTranslation(5f, 240f, 0);
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        Main.refInputManager.removeListener(actionListener);
        Main.refInputManager.deleteMapping("Ready");
        Main.refGuiNode.detachChild(readyText);
        Main.refGuiNode.detachChild(winnerText);
    }
    public static void addMessage(Message message){
        messageQueue.add(message);
    }
    private void handleMessage(Message message){
        if(message instanceof RoundWinner){
            RoundWinner packet = (RoundWinner)message;
            winnerText.setText("Player "+(packet.getPlayerID()-16)+" has won!");
            Main.refInputManager.addMapping("Ready", new KeyTrigger(KeyInput.KEY_R));
            Main.refInputManager.addListener(actionListener, "Ready");
            Main.refGuiNode.attachChild(readyText);
            Main.refGuiNode.attachChild(winnerText);
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
                
            }
            if(d.getClass() == NegativeDisk.class) {
                NegativeDisk nd = (NegativeDisk) d;
                Vector2f newPos = negativePos.remove(negativePos.size() - 1);
                nd.setPosition(newPos);
                
            }            
        }
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf){
            Input.addMessage(new ActionInputContainer(tpf, name, isPressed));
        }
    }; 
    
}
