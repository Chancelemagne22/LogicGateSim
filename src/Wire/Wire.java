package Wire;

import Application.LogicSimulatorApplication;
import Pins.Pin;
import processing.core.PApplet;

import java.util.List;

public class Wire extends PApplet {
    float x1, y1, x2, y2;
    Pin startPin, endPin;
    int r, g, b;

    public Wire(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Wire(List<Integer> wirePoints) {
        this.x1 = wirePoints.get(0);
        this.y1 = wirePoints.get(1);
        this.x2 = wirePoints.get(2);
        this.y2 = wirePoints.get(3);
    }

    public void setPoints(float x1, float y1, float x2, float y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getPointX1() {
        return x1;
    }

    public float getPointX2() {
        return x2;
    }

    public float getPointY1() {
        return y1;
    }

    public float getPointY2() {
        return y2;
    }

    public Pin getStartPin() {
        return startPin;
    }

    public Pin getEndPin() {
        return endPin;
    }

    public boolean isMouseOver(float mx, float my){
        float distance = PApplet.dist(mx, my, (x1 + x2) / 2, (y1 + y2) / 2);
        return distance < 10;
    }

    public void draw(LogicSimulatorApplication p) {
        if (isConnected()) {
            updatePositions();
        }
        
        p.stroke(color(r, g, b));
        p.strokeWeight(3);
        p.line(x1, y1, x2, y2);
        
        // Draw connection dots at endpoints
        if (startPin != null) {
            p.fill(0, 255, 0);
            p.noStroke();
            p.ellipse(x1, y1, 6, 6);
        }
        if (endPin != null) {
            p.fill(255, 255, 0);
            p.noStroke();
            p.ellipse(x2, y2, 6, 6);
        }
    }

    public void customColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void connectPins(Pin start, Pin end) {
        this.startPin = start;
        this.endPin = end;
        updatePositions();
        propagateValue();
    }

    public void updatePositions() {
        if (startPin != null) {
            x1 = startPin.getPinX();
            y1 = startPin.getPinY();
        }
        if (endPin != null) {
            x2 = endPin.getPinX();
            y2 = endPin.getPinY();
        }
    }

    public boolean isConnected() {
        return startPin != null && endPin != null;
    }

    public void propagateValue() {
        if (isConnected()) {
            endPin.value = startPin.value;
        }
    }

}