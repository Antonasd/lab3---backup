/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.state.AppStateManager;

/**
 *
 * @author mrowlie
 */
public class Modeling implements Runnable {

    boolean exit = false;
    
    static AppStateManager stateManager;
    
    static InitState initState;
    static GameState gameState;
    static EndState endState;
    
    private void initialize() {
        Modeling.initState = new InitState();
        Modeling.gameState = new GameState();
        Modeling.endState = new EndState();
        
        stateManager = Main.app.getStateManager();
        
        stateManager.attach(initState);
        stateManager.attach(gameState);
        stateManager.attach(endState);
        
        initState.setEnabled(true);
        gameState.setEnabled(false);
        endState.setEnabled(false);
        
        
    }
    
    private void update() {
        while(!exit){
            
        }
    }
    
    private void destroy() {
        
    }
    
    @Override
    public void run() {
        initialize();
        update();
        destroy();
    }
    
}
