package com.jim.recorder.model;

/**
 * Created by Tauren on 2018/5/18.
 */

public class EventColor {

    int color;
    int size;

    public EventColor(int color, int size) {
        this.color = color;
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void add(int i) {
        this.size += i;
    }
}
