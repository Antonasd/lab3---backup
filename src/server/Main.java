/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;

/**
 *
 * @author mrowlie
 */
public class Main extends SimpleApplication{
    //Server constants
    public static final String NAME = "MultiDisk";
    public static final String DEFAULT_SERVER = "localhost";
    public static final int PORT = 2000;
    public static final int VERSION = 1;
    
    //Game constants
    static final float START_TIME = 30f;
    
    //Frame constants
    static final float FRAME_THICKNESS = 24f;
    static final float FREE_AREA_WIDTH = 492f;
    static final float FRAME_SIZE = FREE_AREA_WIDTH + 2f * FRAME_THICKNESS;
    
    //Location constants
    static final float PLAYER_COORD = FREE_AREA_WIDTH / 6;
    static final float POSNEG_MAX_COORD = FREE_AREA_WIDTH / 3;
    static final float POSNEG_BETWEEN_COORD = PLAYER_COORD;
    
    //Disk radius constants
    static final float PLAYER_R = 20f;
    static final float POSDISK_R = 16f;
    static final float NEGDISK_R = 16f;
    
    public static Main app;
    
    public static GameServer server;

    private static Thread modelingThread;
    private static Modeling modeling;
    
    static float timeElapsed = 0;
    
    public Main() {
         
    }
    
    public static void main(String[] args){
        
        Main application = new Main();
        AppSettings newSettings = new AppSettings(true);
        newSettings.setFrameRate(100);
        application.setSettings(newSettings);
        Main.app = application;
        application.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        Main.server = new GameServer(NAME, VERSION, PORT);
        Main.server.startThreads();
        Main.modelingThread = new Thread(modeling = new Modeling());
        Main.modelingThread.start();
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        Main.timeElapsed += tpf;
    }
    
    @Override
    public void destroy() {
        server.server.close();
        super.destroy();
    }
    
}
