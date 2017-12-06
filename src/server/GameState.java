/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

/**
 *
 * @author mrowlie
 */
public class GameState extends BaseAppState {

    float roundTime = 0;
    
    @Override
    public void update(float tpf) {
        for(Disk d : Disk.disks){
            d.tick(tpf);
        }
        
        roundTime += tpf;
        if(roundTime > 30){
            NetWrite.changeState((byte) 2);
            Modeling.stateManager.getState(EndState.class).setEnabled(true);
            Modeling.stateManager.getState(GameState.class).setEnabled(true);
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
        System.out.println("Enabled");
        for(Disk d : Disk.disks){
            if(d.getClass() == PositiveDisk.class) {
                PositiveDisk pd = (PositiveDisk) d;
                pd.randomizeVelocity();
            }
            else if(d.getClass() == NegativeDisk.class) {
                NegativeDisk nd = (NegativeDisk) d;
                nd.randomizeVelocity();
            } //else {
                //continue;
            //}
            NetWrite.updateDisk(d.diskID, d.pos.x, d.pos.y, d.getVelocity().x, d.getVelocity().y);
        }
    }

    @Override
    protected void onDisable() {
        
    }
    
}
