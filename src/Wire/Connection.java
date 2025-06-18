package Wire;

import Application.LogicSimulatorApplication;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Connection extends PApplet {
    List<Wire> connectingWire = new ArrayList<>();

    public Connection(Wire wire){
        this.connectingWire.add(wire);
    }
    int r,g, b;
    public void draw(LogicSimulatorApplication p) {

        p.stroke(color(r,g, b));
        p.strokeWeight(10);
    }


}
