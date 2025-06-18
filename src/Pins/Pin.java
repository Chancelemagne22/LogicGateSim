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
        System.out.println(parent.getPinX()+rx);
    }


    public int getPinX() {
        return (int) (parentGate.getPinX());
    }

    public int getPinY() {
        return (int) (parentGate.getPinY());
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
        return PApplet.dist(mx, my, getPinX(), getPinY()) < size / 2f;
    }
}
