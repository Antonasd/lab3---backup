/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author mrowlie
 */
public class GameState extends BaseAppState {
    
    @Override
    public void update(float tpf) {
        Main.model.update(tpf);
    }
    @Override
    protected void initialize(Application app) {
        Main.model.initialize();
    }

    @Override
    protected void cleanup(Application app) {
        
    }

    @Override
    protected void onEnable(){
        Main.model.startTimer();
        for(Disk d : Disk.disks){
            if(d.getClass() == PositiveDisk.class){
                PositiveDisk pd = (PositiveDisk) d;
                pd.setWorth(5);
            } else if (d.getClass() == PlayerDisk.class) {
                PlayerDisk pd = (PlayerDisk) d;
                pd.score = 0;
                pd.setVelocity(0, 0);
            }
        }
        Main.refInputManager.addMapping("p1Up", new KeyTrigger(KeyInput.KEY_T));
        Main.refInputManager.addMapping("p1Down", new KeyTrigger(KeyInput.KEY_G));
        Main.refInputManager.addMapping("p1Left", new KeyTrigger(KeyInput.KEY_F));
        Main.refInputManager.addMapping("p1Right", new KeyTrigger(KeyInput.KEY_H));
        
        Main.refInputManager.addMapping("p2Up", new KeyTrigger(KeyInput.KEY_I));
        Main.refInputManager.addMapping("p2Down", new KeyTrigger(KeyInput.KEY_K));
        Main.refInputManager.addMapping("p2Left", new KeyTrigger(KeyInput.KEY_J));
        Main.refInputManager.addMapping("p2Right", new KeyTrigger(KeyInput.KEY_L));
        
        Main.refInputManager.addMapping("Debug", new KeyTrigger(KeyInput.KEY_M));
        
        Main.refInputManager.addListener(actionListener, "p1Up", "p1Down", "p1Left", "p1Right",
                                                         "p2UP", "p2Down", "p2Left", "p2Right", "Debug");
        Main.refInputManager.addListener(analogListener, "p1Up", "p1Down", "p1Left", "p1Right",
                                                         "p2UP", "p2Down", "p2Left", "p2Right");   
    }

    @Override
    protected void onDisable() {
        Main.refInputManager.clearMappings();
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf){
            Input.addMessage(new ActionInputContainer(tpf, name, isPressed));
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float howmuch, float tpf){
            Input.addMessage(new AnalogInputContainer(tpf, name));
        }
    };   
}
