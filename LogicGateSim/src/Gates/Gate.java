package Gates;

import Pins.Pin;
import processing.core.PApplet;
import java.util.List;

public abstract class Gate {
    public float x;
    public float y;
    public List<Pin> inputs;
    public List<Pin> outputs;
    boolean isDragging = false;
    float offsetX, offsetY;
    String name;

    String gateId;

    public Gate(float x, float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.gateId = String.valueOf(this);
    }

    
//    Gate name getter
    public String getGateId() {
        return gateId;
    }

    public abstract void evaluate();
    public abstract void draw(PApplet p, boolean isSelected);
    public abstract float getPinX();
    public abstract float getPinY();

    public boolean isMouseOver(float mx, float my) {
        return mx >= x && mx <= x + 60 && my >= y && my <= y + 35;
    }
    public void startDragging(float mx, float my) {
        isDragging = true;
        offsetX = mx - x;
        offsetY = my - y;
    }


    public void dragTo(float mx, float my) {
        if (isDragging) {
            x = mx - offsetX;
            y = my - offsetY;
        }
    }

    public void stopDragging() {
        isDragging = false;
    }
}
