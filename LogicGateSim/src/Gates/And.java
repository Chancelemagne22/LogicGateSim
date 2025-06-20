package Gates;

import Pins.Pin;
import processing.core.PApplet;

import java.util.List;

public class And extends Gate{
    private static final String gateName = "And";
    Pin pinX1,pinX2,pinY1;

    public And(float x, float y) {
        super(x, y, gateName);
        pinX1 = new Pin(this,x, y + 15);
        pinX2 = new Pin(this,x, y + 30);
        pinY1 = new Pin(this,x, 60 + 15);
        inputs = List.of(pinX1, pinX2);
        outputs = List.of(pinY1);
    }

    @Override
    public void evaluate() {
        outputs.get(0).value = inputs.get(0).value && inputs.get(1).value;
    }



    @Override
    public void draw(PApplet p, boolean isSelected) {
        p.fill(255);
        p.stroke(0);
        p.strokeWeight(2);
        p.rect(x, y, 60, 40);
        p.fill(0);
        p.text("AND", x + 10, y + 25);

        // Update pin positions
        inputs.get(0).getPinX();
        inputs.get(0).getPinY();
        inputs.get(1).getPinX();
        inputs.get(1).getPinY();
        outputs.get(0).getPinY();
        outputs.get(0).getPinY();

        // Draw pins
        inputs.get(0).draw(p);
        inputs.get(1).draw(p);
        outputs.get(0).draw(p);

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
