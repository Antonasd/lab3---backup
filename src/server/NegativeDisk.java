/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Random;

/**
 *
 * @author mrowlie
 */
public class NegativeDisk extends Disk{
    
    
    public NegativeDisk(float x, float y) {
        super(Main.NEGDISK_R);
        this.pos.x = x;
        this.pos.y = y;
       
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
