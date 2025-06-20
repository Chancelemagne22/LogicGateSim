package Gates;

import Pins.Pin;
import processing.core.PApplet;

import java.util.List;

public class And extends Gate{
    private static final String gateName = "And";

    public And(float x, float y) {
        super(x, y, gateName);
        inputs = List.of(
            new Pin(this, 0, 10),
            new Pin(this, 0, 30)
        );
        outputs = List.of(new Pin(this, 60, 20));
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

        // Draw pins
        inputs.get(0).draw(p);
        inputs.get(1).draw(p);
        outputs.get(0).draw(p);
    }

    @Override
    public float getPinX() {
        return x;
    }

    @Override
    public float getPinY() {
        return y + 20;
    }
}

