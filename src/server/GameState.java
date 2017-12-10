/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import networking.Packet.TimeSync;

/**
 *
 * @author mrowlie
 */
public class GameState extends BaseAppState {

    float roundTime = 0;
    final float updateTimeIntervall = 0.1f;
    float timeSinceUpdate = 0f;
    
    @Override
    public void update(float tpf) {
        for(Disk d : Disk.disks){
            d.tick(tpf);
        }
        timeSinceUpdate +=tpf;
        roundTime = roundTime + tpf > 30f ? 30f : roundTime + tpf;
        if(timeSinceUpdate >= updateTimeIntervall || roundTime == 30f)NetWrite.addMessage(new TimeSync(roundTime));
        if(roundTime == 30f){
            Modeling.stateManager.getState(EndState.class).setEnabled(true);
            Modeling.stateManager.getState(GameState.class).setEnabled(false);
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
        roundTime = 0;
        for(Disk d : Disk.disks){
            if(d.getClass() == PositiveDisk.class) {
                PositiveDisk pd = (PositiveDisk) d;
                pd.worth = 5;
                pd.randomizeVelocity();
            }
            else if(d.getClass() == NegativeDisk.class) {
                NegativeDisk nd = (NegativeDisk) d;
                nd.randomizeVelocity();
            } else if (d.getClass() == PlayerDisk.class) {
                PlayerDisk pd = (PlayerDisk) d;
                pd.score = 0;
                NetWrite.updateScore(pd.diskID, 0);
                pd.keyPressed[0] = false;
                pd.keyPressed[1] = false;
                pd.keyPressed[2] = false;
                pd.keyPressed[3] = false;
                pd.setVelocity(0, 0);
                pd.ready = false;
            }
            NetWrite.updateDisk(d.diskID, d.pos.x, d.pos.y, d.getVelocity().x, d.getVelocity().y);
        }
    }

    @Override
    protected void onDisable() {
        roundTime = 0;
        timeSinceUpdate = 0f;
        NetWrite.changeState((byte) 2);
    }
    
}
