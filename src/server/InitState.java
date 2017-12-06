/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector2f;
import java.util.ArrayList;

/**
 *
 * @author mrowlie
 */
public class InitState extends BaseAppState{


    @Override
    public void update(float tpf) {
        boolean playersReady = true;
        for(PlayerDisk player: PlayerDisk.playerDisks) {
            if(!player.ready) {
                playersReady = false;
                break;
            }
        }
        if(PlayerDisk.playerDisks.isEmpty()) playersReady = false;
        if(playersReady){
            startGame();
        }
    }
    
    private void startGame() {
        NetWrite.changeState((byte) 1);
        Modeling.stateManager.getState(GameState.class).setEnabled(true);
        Modeling.stateManager.getState(InitState.class).setEnabled(false);
    }
    
    @Override
    protected void initialize(Application game) {
        initDisks();
        
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
    
    
    public void initDisks() {
        PositiveDisk pd0 = new PositiveDisk( - Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD);
        PositiveDisk pd1 = new PositiveDisk( 0, Main.POSNEG_MAX_COORD);
        PositiveDisk pd2 = new PositiveDisk( Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD);
        PositiveDisk pd3 = new PositiveDisk( - Main.POSNEG_MAX_COORD, 0);
        PositiveDisk pd4 = new PositiveDisk( Main.POSNEG_MAX_COORD, 0);
        PositiveDisk pd5 = new PositiveDisk( - Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD);
        PositiveDisk pd6 = new PositiveDisk( 0, - Main.POSNEG_MAX_COORD);
        PositiveDisk pd7 = new PositiveDisk( Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD);
        
        NegativeDisk nd0 = new NegativeDisk( - Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd1 = new NegativeDisk( - Main.POSNEG_MAX_COORD, -Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd2 = new NegativeDisk( - Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD);
        NegativeDisk nd3 = new NegativeDisk( Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD);
        NegativeDisk nd4 = new NegativeDisk( Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd5 = new NegativeDisk( Main.POSNEG_MAX_COORD, - Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd6 = new NegativeDisk( Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD);
        NegativeDisk nd7 = new NegativeDisk( - Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD);
        
        
        
        ArrayList<Vector2f> playerPos = new ArrayList();
        
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(0, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, 0));
        playerPos.add(new Vector2f(0, 0));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, 0));
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, -Main.PLAYER_COORD));
        playerPos.add(new Vector2f(0, -Main.PLAYER_COORD));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, -Main.PLAYER_COORD));
        
        
    }
    
}
