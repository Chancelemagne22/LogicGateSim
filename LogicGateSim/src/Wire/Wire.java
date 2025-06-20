package Wire;

import Application.LogicSimulatorApplication;
import processing.core.PApplet;

import java.util.List;


public class Wire extends PApplet {
    float x1, y1, x2, y2;


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

    public boolean isMouseOver(float mx, float my){
        System.out.println(mx+ "mx:" + x1 + "x1:" + x2 + "x2:" + my +"my:" +y1 + "y1:" + y2 +"y2");
        return mx >= x1-5 && mx <= x1+5 && my >= y1 && my <=y2;
    }
    int r,g, b;
    public void draw(LogicSimulatorApplication p) {

        p.stroke(color(r,g, b));
        p.strokeWeight(10);
        p.line(x1, y1,x2,y2);
    }
    public void customColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

}