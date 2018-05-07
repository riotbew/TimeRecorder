package com.jim.recorder.model;

/**
 * Created by Tauren on 2018/4/20.
 */

public class EventType {

    private String name;
    int type;

    public EventType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
