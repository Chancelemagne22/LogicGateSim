package Gates;

import Pins.Pin;
import processing.core.PApplet;

import java.util.Collections;
import java.util.List;

public class InputGate extends Gate {
    private static final String gateName = "Input";

    public InputGate(float x, float y) {
        super(x, y, gateName);
        inputs = Collections.emptyList();
        outputs = List.of(new Pin(this, 60, 15));
        outputs.get(0).value = false; // Default to off
    }

    /**
     * Toggles the state of the output pin. Call this on mouse click.
     */
    public void toggleState() {
        outputs.get(0).value = !outputs.get(0).value;
    }

    @Override
    public void evaluate() {
        // State is controlled manually via toggleState(), no evaluation needed.
    }

    @Override
    public void draw(PApplet p, boolean isSelected) {
        if (outputs.get(0).value) {
            p.fill(0, 255, 0); // Green when true
        } else {
            p.fill(255); // White when false
        }
        p.stroke(0);
        p.strokeWeight(2);
        p.rect(x, y, 60, 30);
        p.fill(0);
        p.text("IN", x + 22, y + 20);

        outputs.get(0).draw(p);
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