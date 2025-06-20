package Pins;

import Gates.Gate;
import processing.core.PApplet;

public class Pin {
    Gate parentGate;
    float relativeX, relativeY;
    public boolean value = false;
    int size = 10;

    public Pin(Gate parent, float rx, float ry){
        this.parentGate = parent;
        this.relativeX = rx;
        this.relativeY = ry;
    }

    public Gate getParentGate() {
        return parentGate;
    }

    public float getPinX() {
        return parentGate.x + relativeX;
    }

    public float getPinY() {
        return parentGate.y + relativeY;
    }

    public void setRelativeX(float relativeX) {
        this.relativeX = relativeX;
    }

    public void setRelativeY(float relativeY) {
        this.relativeY = relativeY;
    }

    public void draw(PApplet p){
        p.stroke(0);
        p.fill(value ? p.color(0,255,0): p.color(255,0,0));
        p.ellipse(getPinX(),getPinY(),size,size);
    }
    
    public boolean isMouseOver(int mx, int my) {
        return PApplet.dist(mx, my, getPinX(), getPinY()) < size;
    }
    
    public boolean isMouseOver(float mx, float my) {
        return PApplet.dist(mx, my, getPinX(), getPinY()) < size;
    }
}

