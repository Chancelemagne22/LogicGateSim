package Application;

import Display.Input;
import Gates.And;
import Gates.BufferGate;
import Gates.Gate;
import Gates.Not;
import Gates.InputGate;
import Gates.OutputGate;
import Pins.Pin;
import Wire.Wire;

import processing.core.PApplet;
import controlP5.*;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.List;

enum State{
    isIdle,
    isDrawing,
    isDragging,
    isStopping
}

public class LogicSimulatorApplication extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Application.LogicSimulatorApplication");
    }
    
    List<Gate> gates = new ArrayList<>();
    List<Wire> wires = new ArrayList<>();
    Wire wireIn, wireOut;
    State currentState = State.isIdle;



    Input input;

    ControlP5 cp5;
    String inputValue = "";

    int SCREEN_WIDTH = 1280;
    int SCREEN_HEIGHT = 720;

    // Mouse handling variables
    Wire activeWire = null;
    Pin startPin = null;
    Gate selectedGate = null;
    Gate draggingGate = null;
    boolean isConnecting = false;
    int gateCount = 0;

    public void settings(){
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    
    public void setup(){
        surface.setTitle("Logic Gate Simulator");
        GuiButton();
        textSize(14);



    }

    public void draw(){
        background(245);
        drawGrid();
        drawGui();

        // Propagate values through wires
        for (Wire wire : wires) {
            wire.propagateValue();
        }

        // Evaluate all gates
        for (Gate gate : gates) {
            gate.evaluate();
        }

        for(Gate gate: gates){
            gate.draw(this,gate == selectedGate);
        }
        for(Wire wire: wires){
            wire.draw(this);
        }
        if (activeWire != null) {
            activeWire.draw(this);
        }



    }

    //GUI
    void drawGui(){
        drawHeader();
        drawSetting();
        drawInputBar();
        drawOutputBar();
    }
    
    void drawHeader(){
        noStroke();
        fill(color(31,25,54));
        rect(0,0,SCREEN_WIDTH+10, 100);
    }
    
    void drawSetting(){
        noStroke();
        fill(color(58,57,57));
        rect(0,0, 100, 100);
    }
    
    void drawInputBar(){
        wireIn = new Wire(30,120,30,700);
        wireIn.customColor(61, 0, 4);
        wireIn.draw(this);
    }
    
    void drawOutputBar(){
        wireOut = new Wire(1200,120,1200,700);
        wireOut.customColor(36, 34, 34);
        wireOut.draw(this);
    }
    
    void drawGrid(){
        this.stroke(230);
        strokeWeight(2);
        for (int x = 0; x < width; x += 20) {
            line(x, 0, x, height);
        }
        for (int y = 0; y < height; y += 20) {
            line(0, y, width, y);
        }
    }

    public void createWire() {
        inputValue = cp5.get(Textfield.class, "inputValue").getText();
        println("Creating wire with value: " + inputValue);

        // You could parse the value and create a test wire or gate
        boolean val = inputValue.equals("1") || inputValue.equalsIgnoreCase("true");

        // Example: feed this value into a gate input

    }

    public void GuiButton(){
        //        GUI USING CONTROL P5
        cp5 = new ControlP5(this);
//        cp5.addButton("switchButton")
//                .setColorBackground(color(135,216,167))
//                .setPosition(30,30)
//                .setSize(100,40)
//                .setLabel("Switch");

        cp5.addButton("addNewBuffer")
                .setColorBackground(color(135,216,167))
                .setPosition(120,30)
                .setSize(100,40)
                .setLabel("Buffer");

        cp5.addButton("addNewNot")
                .setColorBackground(color(135,216,167))
                .setPosition(260,30)
                .setSize(100,40)
                .setLabel("NOT");
                
        cp5.addButton("addNewAnd")
                .setColorBackground(color(135,216,167))
                .setPosition(420,30)
                .setSize(100,40)
                .setLabel("AND");
        cp5.addButton("addNewInput")
                .setColorBackground(color(135,216,167))
                .setPosition(580,30)
                .setSize(100,40)
                .setLabel("Input");
        cp5.addButton("addNewOutput")
                .setColorBackground(color(135,216,167))
                .setPosition(740,30)
                .setSize(100,40)
                .setLabel("Output");     
    }
    
    public void switchButton(){
        gates.get(0).inputs.get(0).value = !gates.get(0).inputs.get(0).value;
        gates.get(0).outputs.get(0).value = gates.get(0).inputs.get(0).value;
    }
    
    public void addNewBuffer(){
        int layer = gateCount * 10;
        gates.add(new BufferGate(100, 100 + layer));
        gateCount = gates.size();
    }
    
    public void addNewNot(){
        int layer = gateCount * 10;
        gates.add(new Not(100, 100 + layer));
        gateCount = gates.size();
    }
    
    public void addNewAnd(){
        int layer = gateCount * 10;
        gates.add(new And(100, 100 + layer));
        gateCount = gates.size();

    }
    public void addNewInput() {
        int layer = gateCount * 10;
        gates.add(new InputGate(100, 100 + layer));
        gateCount = gates.size();
    }
    public void addNewOutput() {
        int layer = gateCount * 10;
        gates.add(new OutputGate(100, 100 + layer));
        gateCount = gates.size();
    }

    public void mouseMoved(){
        if (isConnecting && activeWire != null) {
            activeWire.setPoints(activeWire.getPointX1(), activeWire.getPointY1(), mouseX, mouseY);
        }
    }

    public void mousePressed() {
        // Reset connection if right click
        if (mouseButton == RIGHT) {
            if (activeWire != null) {
                activeWire = null;
                startPin = null;
                isConnecting = false;
                currentState = State.isIdle;
                return;
            }
            
            // Delete gates and connected wires on right click
            Gate foundGateToRemove = null;
            for (Gate gate : gates) {
                if (gate.isMouseOver(mouseX, mouseY)) {
                    foundGateToRemove = gate;
                    break;
                }
            }

            if (foundGateToRemove != null) {
                final Gate gateToRemove = foundGateToRemove;
                // Remove wires connected to the gate
                wires.removeIf(wire -> (wire.getStartPin() != null && wire.getStartPin().getParentGate() == gateToRemove) ||
                                        (wire.getEndPin() != null && wire.getEndPin().getParentGate() == gateToRemove));
                
                // Remove the gate
                gates.remove(gateToRemove);
                return;
            }
            return;
        }

        // Handle pin connections on left click
        if (mouseButton == LEFT) {
            // Check all gates for pin clicks
            for (Gate gate : gates) {
                // Check output pins first (start connections from outputs)
                for (Pin pin : gate.outputs) {
                    if (pin.isMouseOver(mouseX, mouseY)) {
                        if (!isConnecting) {
                            // Start new connection from output pin
                            startPin = pin;
                            activeWire = new Wire(pin.getPinX(), pin.getPinY(), mouseX, mouseY);
                            activeWire.customColor(0, 255, 0);
                            isConnecting = true;
                            currentState = State.isDrawing;
                            println("Started connection from output pin");
                            return;
                        }
                    }
                }
                
                // Check input pins (end connections at inputs)
                for (Pin pin : gate.inputs) {
                    if (pin.isMouseOver(mouseX, mouseY)) {
                        if (isConnecting && activeWire != null && startPin != null) {
                            // Complete connection to input pin
                            activeWire.connectPins(startPin, pin);
                            activeWire.customColor(255, 255, 0);
                            wires.add(activeWire);
                            
                            // Reset connection state
                            activeWire = null;
                            startPin = null;
                            isConnecting = false;
                            currentState = State.isIdle;
                            println("Completed connection to input pin");
                            return;
                        } else if (!isConnecting) {
                            // Toggle pin value if not connecting
                            pin.value = !pin.value;
                            gate.evaluate();
                            return;
                        }
                    }
                }
            }

            // If clicking on empty space while connecting, cancel connection
            if (isConnecting) {
                activeWire = null;
                startPin = null;
                isConnecting = false;
                currentState = State.isIdle;
                return;
            }

            // Handle gate dragging if not connecting
            if (!isConnecting) {
                for (Gate gate : gates) {
                    if (gate.isMouseOver(mouseX, mouseY)) {
                        draggingGate = gate;
                        selectedGate = gate;
                        gate.startDragging(mouseX, mouseY);
                        // Defer setting state to isDragging to distinguish a click from a drag.
                        // currentState = State.isDragging;
                        break;
                    }
                }
            }
        }
    }

    public void mouseDragged() {
        if (draggingGate != null) {
            currentState = State.isDragging;
            draggingGate.dragTo(mouseX, mouseY);
        }
    }

    public void mouseReleased() {
        if (draggingGate != null) {
            if (currentState != State.isDragging) { // This was a click, not a drag
                if (draggingGate instanceof InputGate) {
                    ((InputGate) draggingGate).toggleState();
                }
            }
            draggingGate.stopDragging();
            draggingGate = null;
        }
        if (currentState == State.isDragging) {
            currentState = State.isIdle;
        }
    }


    public void keyPressed() {
        currentState = State.isIdle;
        if (draggingGate != null) {
            draggingGate.stopDragging();
            draggingGate = null;
        }
    



        if (currentState == State.isDragging) {
            currentState = State.isIdle;
        }
    }



    




}
