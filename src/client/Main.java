package client;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import networking.Packet.*;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    //constants
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
    
    static Client myClient;
    static Modeling model;
    static AssetManager refAssetManager;
    static InputManager refInputManager;
    static Node refRootNode;
    static Node refGuiNode;
    
    static GameState gameState;
    static InitState initState;
    static EndState endState;
    
    Thread netReadThread;
    Thread netWriteThread;
    Thread inputThread;
    
    //test
    float timeElapsed = 0;
    static final float TIME = 10f;
    //
    public static void main(String[] args) {
        Main application = new Main();
        AppSettings newSettings = new AppSettings(true);
        newSettings.setFrameRate(1000);
        application.setSettings(newSettings);
        Main app = application;
        app.start();
    }
    //test 2

    @Override
    public void simpleInitApp() {
        //Register packets
        Serializer.registerClass(DiskUpdate.class);
        Serializer.registerClass(ScoreUpdate.class);
        Serializer.registerClass(TimeSync.class);
        Serializer.registerClass(TimeDiff.class);
        Serializer.registerClass(ClientReady.class);
        Serializer.registerClass(ChangeState.class);
        Serializer.registerClass(InitClient.class);
        Serializer.registerClass(DisconnectClient.class);
        Serializer.registerClass(InputPressed.class);
        Serializer.registerClass(InputReleased.class);
        Serializer.registerClass(JoiningClient.class);
        Serializer.registerClass(UpdatePosDisk.class);
        //Set references
        refAssetManager = assetManager;
        refInputManager = inputManager;
        refRootNode = rootNode;
        refGuiNode = guiNode;
        //create and set states
        gameState = new GameState();
        initState =  new InitState();
        endState =  new EndState();
        
        stateManager.attach(gameState);
        stateManager.attach(initState);
        stateManager.attach(endState);
        
        gameState.setEnabled(false);
        initState.setEnabled(true);
        endState.setEnabled(false);
        //Cam setup
        cam.setLocation(new Vector3f(-84f, 0.0f, 720f));
        cam.setRotation(new Quaternion(0.0f, 1.0f, 0.0f, 0.0f));
        //Create frame
        new Frame(assetManager);
        //Start client
        try{
            
            model = new Modeling();
            NetRead netRead =  new NetRead();
            myClient = Network.connectToServer(NAME, VERSION, DEFAULT_SERVER, PORT, PORT);
            myClient.addMessageListener(netRead);
            myClient.start();       
            
            netWriteThread = new Thread(new NetWrite(myClient));
            netReadThread = new Thread(netRead);
            inputThread =  new Thread(new Input());
            
            netWriteThread.start();
            netReadThread.start();
            inputThread.start();
        }
        catch(Exception e){
            System.out.println("ERROR CONNECTING");
            System.out.println(e.getMessage());
        }
        System.out.println("hey");
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
