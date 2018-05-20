package com.jim.recorder.ui.model;

/**
 * Created by Tauren on 2018/5/19.
 */

public class SingleModel {
    private int color = -1;
    private Long eventId = -1l;
    private boolean header = false;
    private String name = "";

    public SingleModel(boolean header) {
        this.header = header;
    }

    public SingleModel(int color, boolean header) {
        this.header = header;
        this.color = color;
    }

    public SingleModel(int color, long eventId) {
        this.color = color;
        this.eventId = eventId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
