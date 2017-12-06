/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import java.util.Random;

/**
 *
 * @author mrowlie
 */
public class NegativeDisk extends Disk{
    
    public NegativeDisk(AssetManager assetManager, float x, float y, int id) {
        super(assetManager, ColorRGBA.Red, Main.NEGDISK_R, id);
        this.pos.x = x;
        this.pos.y = y;
        this.diskNode.setLocalTranslation(x, y, 0f);
       
    }
    
    @Override
    public void tick(float tpf) {
        super.tick(tpf);
    }
    
    public void randomizeVelocity() {
        Random rand = new Random();
        this.setVelocity(rand.nextInt(100) - 50, rand.nextInt(100) - 50);
    }
    
}
