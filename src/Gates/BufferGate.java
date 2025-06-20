package Gates;

import Pins.Pin;
import processing.core.PApplet;

import java.util.List;

public class BufferGate extends Gate{
    private static final String gateName = "Buffer";

    public BufferGate(float x, float y){
        super(x, y, gateName);
        inputs = List.of(new Pin(this, 0, 15));
        outputs = List.of(new Pin(this, 60, 15));
    }

    @Override
    public float getPinX() {
        return x;
    }

    @Override
    public float getPinY() {
        return y + 15;
    }

    @Override
    public void evaluate() {
        outputs.get(0).value = inputs.get(0).value;
    }

    @Override
    public void draw(PApplet p, boolean isSelected) {
        p.fill(255);
        p.stroke(0);
        p.strokeWeight(2);
        p.rect(x, y, 60, 30);
        p.fill(0);
        p.text("BUFFER", x + 5, y + 20);

        inputs.get(0).draw(p);
        outputs.get(0).draw(p);
    }
}

