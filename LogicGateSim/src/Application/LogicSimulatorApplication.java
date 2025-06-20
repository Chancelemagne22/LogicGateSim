package Application;

import Display.Input;
import Gates.And;
import Gates.BufferGate;
import Gates.Gate;
import Gates.Not;
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
    isStopping;
}

public class LogicSimulatorApplication extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Application.LogicSimulatorApplication");

    }
    List<Gate> gates = new ArrayList<>();
    List<Wire> wires = new ArrayList<>();
    Wire connectingWire = null;
    Wire wireIn, wireOut;
    State currentState = State.isIdle;



    Input input;

    ControlP5 cp5;
    String inputValue = "";

    int SCREEN_WIDTH = 1280;
    int SCREEN_HEIGHT = 720;

    public void settings(){
        size(SCREEN_WIDTH  ,SCREEN_HEIGHT);
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

        for(Gate gate: gates){
            gate.draw(this,gate == selectedGate);
        }
        for(Wire wire: wires){
            wire.draw(this);
        }
        if (connectingWire != null) {
            connectingWire.draw(this);
        }



    }


//    GUI
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
        PFont font  = createFont("Arial",20);
        //        GUI USING CONTROL P5
        cp5 = new ControlP5(this);
//        cp5.addButton("switchButton")
//                .setPosition(100 ,20)
//                .setSize(80,30)
//                .setLabel("On/Off");

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
    }
    public void switchButton(){

        gates.get(0).inputs.get(0).value = !gates.get(0).inputs.get(0).value;
        gates.get(0).outputs.get(0).value = gates.get(0).inputs.get(0).value;
    }
    int gateCount = 0;
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
    public void addWire(List<Integer> wirePoints){
        connectingWire = new Wire(wirePoints);
        activeWire = connectingWire; // save the active wire
        wires.add(connectingWire);
        System.out.println(wires);
    }
    //    DRAGGING MECHANICS

    Gate draggingGate = null;
    List <Integer> wirePoints = new ArrayList<>();
    boolean clickInPin = false;
    public void mouseMoved(){

        if(currentState == State.isDrawing && activeWire != null){
            for(Wire wire:wires){
                if(wire == activeWire){
                    wire.setPoints(wire.getPointX1(), wire.getPointY1(), mouseX, mouseY);
                    System.out.println("Moving: " + wire);
                }
            }
        }

    }
    Wire activeWire = null;
    Gate selectedGate = null;
    Gate activeGate = null;
    public void mousePressed() {

        currentState = State.isDrawing;
        for (Gate gate : gates) { // Loops to all gate

            System.out.println(gate.getGateId());
            for(Wire wire: wires){ // Loops to all existing wire
                if(wire.isMouseOver(mouseX,mouseY)){
                    System.out.println("In wire: " + wire);
                }
                System.out.println("Checking Wire:"+ wire);

                if(gate.isMouseOver(mouseX, mouseY)){ // Check if mouse is in a gate
                    selectedGate = gate;
                    activeGate = gate;
                    System.out.println("Selected Gate: "+ selectedGate.getGateId());
                }

                if(wireIn.isMouseOver(mouseX,mouseY)){ // Checking if the mouse is in the Wire In
                    System.out.println("Update the location ");
                    currentState = State.isStopping;
                    if(activeWire == wire){
                        wire.setPoints(wire.getPointX1(), wire.getPointY1(), mouseX, mouseY); // Will set the wire location.
                    }

                    System.out.println("Inside wore-In wire");
                }else{
                    System.out.println("Outside woreIn wire");
                }
            }

            System.out.println("Checking current state:" + currentState);

            // Will create the wire
            for (Pin pin : gate.inputs) {
                if (pin.isMouseOver(mouseX, mouseY)) {
                    currentState= State.isDrawing;
                    System.out.println("Mouse Pressed in Pin");
                    wirePoints = new ArrayList<>();
                    wirePoints.add(pin.getPinX());
                    wirePoints.add(pin.getPinY());
                    wirePoints.add(mouseX);
                    wirePoints.add(mouseY);
                    addWire(wirePoints);
                    wirePoints.clear();
//                  Adding wires

                    println("Clicked input pin: " + pin + " " + gate.getGateId());
                    pin.value = !pin.value;
                    gate.evaluate();
                    return;
                }
            }

//            for (Pin pin : gate.outputs) {
//                if (pin.isMouseOver(mouseX, mouseY) && draggingGate != null) {
//                    System.out.println("Changes: " + draggingGate.getPinX() +":" + draggingGate.getPinY());
//                    wirePoints.set(0, (int)draggingGate.getPinX());
//                    wirePoints.set(1, (int)draggingGate.getPinY());
//                }
//            }
        }




        // Right-click deletion
        if (mouseButton == RIGHT) {
            for (int i = gates.size() - 1; i >= 0; i--) {
                Gate gate = gates.get(i);
                if (gate.isMouseOver(mouseX, mouseY)) {
                    gates.remove(i);
                    return;
                }
            }
        }

        // Dragging
        if (mouseButton == LEFT) {
            currentState = State.isDragging;
            for (Gate gate : gates) {
                if (gate.isMouseOver(mouseX, mouseY)) {
                    draggingGate = gate;
                    gate.startDragging(mouseX, mouseY);
                    break;
                }
            }
        }
        if(currentState == State.isIdle){
            activeWire = null;
        }
    }
    public void mouseDragged() {

        if (draggingGate != null) {
            draggingGate.dragTo(mouseX, mouseY);
            if(currentState == State.isDragging && activeWire != null){
                for(Wire wire:wires){
                    if(wires.indexOf(wire) == gates.indexOf(activeGate)){
                        wire.setPoints(draggingGate.getPinX(),draggingGate.getPinY(),  wire.getPointX2(), wire.getPointY2());
                    }
//                    if(wire == activeWire){
//                        wire.setPoints(draggingGate.getPinX(),draggingGate.getPinY(),  wire.getPointX2(), wire.getPointY2());
//                    }
                }
            }
//
        }
    }

    public void mouseReleased() {
        currentState = State.isIdle;
        if (draggingGate != null) {
            draggingGate.stopDragging();
            draggingGate = null;
        }
    }


}
