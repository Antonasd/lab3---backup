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
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import networking.Packet.RoundWinner;

/**
 *
 * @author mrowlie
 */
public class EndState extends BaseAppState {

    
    
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
        Modeling.stateManager.getState(EndState.class).setEnabled(false);
    }
    
    @Override
    protected void initialize(Application app) {
        
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        Collections.sort(PlayerDisk.playerDisks, scoreComparator);
        PlayerDisk winner = PlayerDisk.playerDisks.get(PlayerDisk.playerDisks.size()-1);
        NetWrite.addMessage(new RoundWinner(winner.diskID));
    }

    @Override
    protected void onDisable() {
        resetGame();
    }
    
    private void resetGame() {
        
        ArrayList<Vector2f> positivePos = new ArrayList();
        ArrayList<Vector2f> negativePos = new ArrayList();
        ArrayList<Vector2f> playerPos = new ArrayList();
        
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
        
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(0, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, 0));
        playerPos.add(new Vector2f(0, 0));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, 0));
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, -Main.PLAYER_COORD));
        playerPos.add(new Vector2f(0, -Main.PLAYER_COORD));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, -Main.PLAYER_COORD));
        
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
            if(d.getClass() == PlayerDisk.class) {
                
                PlayerDisk pd = (PlayerDisk) d;
                Vector2f newPos = playerPos.remove(rand.nextInt(playerPos.size()));
                pd.setPosition(newPos);
                pd.setVelocity(0, 0);
            }
            
            NetWrite.updateDisk(d.diskID, d.pos.x, d.pos.y, d.getVelocity().x, d.getVelocity().y);
            
        }
    }
    
    private final Comparator scoreComparator = new Comparator<PlayerDisk>(){
      @Override
      public int compare(PlayerDisk player1, PlayerDisk player2){
          return player1.score-player2.score;
        }  
    };
}
