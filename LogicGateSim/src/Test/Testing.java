package Test;

import Gates.Gate;
import processing.core.PApplet;

public class Testing extends Gate {
    public Testing(float x, float y) {
        super(x, y, "Testing");
    }

    @Override
    public void evaluate() {

    }

    @Override
    public void draw(PApplet p, boolean isSelected) {
        p.fill(200);
        p.stroke(0);
        p.rect(x, y, 60, 40);
        p.fill(0);
        p.text("DUMMY", x + 10, y + 25);
    }

    @Override
    public float getPinX() {
        return 0;
    }

    @Override
    public float getPinY() {
        return 0;
    }

}