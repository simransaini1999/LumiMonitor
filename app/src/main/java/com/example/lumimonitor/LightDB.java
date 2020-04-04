package com.example.lumimonitor;

public class LightDB {
    int red;
    int green;
    int blue;
    int affectState;


    public LightDB() {
    }


    public LightDB(int red, int green, int blue, int affectState) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.affectState = affectState;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getAffectState() {
        return affectState;
    }

    public void setAffectState(int affectState) {
        this.affectState = affectState;
    }


}
