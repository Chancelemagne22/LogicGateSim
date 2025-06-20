package Gates;

import Pins.Pin;
import processing.core.PApplet;

import java.util.Collections;
import java.util.List;

public class OutputGate extends Gate {
    private static final String gateName = "Output";

    public OutputGate(float x, float y) {
        super(x, y, gateName);
        inputs = List.of(new Pin(this, 0, 15));
        outputs = Collections.emptyList();
    }

    @Override
    public void evaluate() {
    }

    @Override
    public void draw(PApplet p, boolean isSelected) {
        if (inputs.get(0).value) {
            p.fill(255, 0, 0); // Bright red when true
        } else {
            p.fill(139, 0, 0); // Dark red when false
        }
        p.stroke(0);
        p.strokeWeight(2);
        p.rect(x, y, 60, 30);
        p.fill(255);
        p.text("OUT", x + 18, y + 20);

        inputs.get(0).draw(p);
    }

    @Override
    public float getPinX() {
        return x;
    }

    @Override
    public float getPinY() {
        return y + 15;
    }
}