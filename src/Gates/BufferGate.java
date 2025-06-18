package Gates;

import Pins.Pin;
import processing.core.PApplet;

import java.util.List;

public class BufferGate extends Gate{
    private static final String gateName = "Buffer";
    public float pinX;
    public float pinY;
    public BufferGate(float x, float y){
        super(x, y, gateName);
        pinX = x;
        pinY = x + 15;
        inputs = List.of(new Pin(this,pinX, pinY));
        outputs = List.of(new Pin(this,pinX, pinY+15));

    }

    public float getPinX() {
        return pinX;
    }

    public float getPinY() {
        return pinY;
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

        inputs.get(0).getPinX(); inputs.get(0).getPinY();
        outputs.get(0).getPinX() ; outputs.get(0).getPinY();
        pinX = x;
        pinY = y + 15;

        inputs.get(0).draw(p);
        outputs.get(0).draw(p);
    }
}
