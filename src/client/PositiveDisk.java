/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import java.util.Random;

/**
 *
 * @author mrowlie
 */
public class PositiveDisk extends Disk{
    
    BitmapFont font;
    BitmapText text;
    
    int worth = 5;
    
    
    public PositiveDisk(AssetManager assetManager, float x, float y, int id) {
        super(assetManager, ColorRGBA.Green, Main.POSDISK_R, id);
        this.pos.x = x;
        this.pos.y = y;
        
        
        font = assetManager.loadFont("Interface/Fonts/Console.fnt");
        text = new BitmapText(font, false);
        text.setSize(26);
        text.setColor(ColorRGBA.Black);
        text.setText("" + worth);
        text.setQueueBucket(RenderQueue.Bucket.Transparent);
        this.diskNode.attachChild(text);
        text.setLocalTranslation(- text.getHeight() / 3, text.getHeight() / 2, Main.FRAME_THICKNESS + 1f);
        
        this.diskNode.setLocalTranslation(x, y, 0f);
    }
    
    @Override
    public void tick(float tpf) {
        super.tick(tpf);
    }
    
    public void reset() {
        worth = 5;
        text.setText("" + worth);
    }
    
    public void subWorth(){
        if(worth > 0){
            worth--;
            text.setText("" + worth);
        }
        
    }
    
    public void randomizeVelocity() {
        Random rand = new Random();
        this.setVelocity(rand.nextInt(100) - 50, rand.nextInt(100) - 50);
    }
}
