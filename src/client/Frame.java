/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author mrowlie
 */
public class Frame {
    
    public Node frameNode;
    
    //North, South, East, West
    private final Geometry nWall;
    private final Geometry sWall;
    private final Geometry eWall;
    private final Geometry wWall;
    
    
    public Frame(AssetManager assetManager) {
        //Create walls
        this.frameNode = new Node("Frame");
        this.frameNode.attachChild( this.nWall = new FrameWall(assetManager).getGeom()  );
        this.frameNode.attachChild( this.sWall = new FrameWall(assetManager).getGeom()  );
        this.frameNode.attachChild( this.eWall = new FrameWall(assetManager).getGeom()  );
        this.frameNode.attachChild( this.wWall = new FrameWall(assetManager).getGeom()  );
        
        //Translate walls
        this.nWall.setLocalTranslation(Main.FRAME_THICKNESS / 2 , (Main.FREE_AREA_WIDTH + Main.FRAME_THICKNESS) / 2, 0f);
        this.sWall.setLocalTranslation(-Main.FRAME_THICKNESS / 2, (-Main.FREE_AREA_WIDTH - Main.FRAME_THICKNESS) / 2, 0f);
        this.eWall.setLocalTranslation((Main.FREE_AREA_WIDTH + Main.FRAME_THICKNESS) / 2, -Main.FRAME_THICKNESS / 2, 0f);
        this.wWall.setLocalTranslation((-Main.FREE_AREA_WIDTH - Main.FRAME_THICKNESS) / 2, Main.FRAME_THICKNESS / 2, 0f);
        
        //Rotate east and west walls 
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(FastMath.PI / 2, new Vector3f(0,0,1));
        
        this.eWall.setLocalRotation(rotation);
        this.wWall.setLocalRotation(rotation);
        
        Main.refRootNode.attachChild(frameNode);
        
        
        
    }
    
    private class FrameWall {
        Box wall;
        Geometry wallGeom;
        Material wallMat;
        
        public FrameWall(AssetManager assetManager){
            wall = new Box((Main.FRAME_THICKNESS + Main.FREE_AREA_WIDTH) / 2, Main.FRAME_THICKNESS / 2, Main.FRAME_THICKNESS / 2);
            wallGeom = new Geometry("Wall", wall);
            wallMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            wallMat.setColor("Color", ColorRGBA.Orange);
            wallGeom.setMaterial(wallMat);
        }
        
        public Geometry getGeom() {
            return this.wallGeom;
        }
        
    }
}


