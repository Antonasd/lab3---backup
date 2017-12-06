/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Anton
 */
public abstract class InputContainer {
    public final float tpf;
    public final String name;
    public InputContainer(float tpf, String name){
        this.tpf = tpf;
        this.name = name;
    }
    
}
class ActionInputContainer extends InputContainer{
    boolean isPressed;
    public ActionInputContainer(float tpf, String name, Boolean isPressed){
        super(tpf, name);
        this.isPressed = isPressed;     
    }
}

class AnalogInputContainer extends InputContainer{
    public AnalogInputContainer(float tpf, String name){
        super(tpf, name);   
    }
}
