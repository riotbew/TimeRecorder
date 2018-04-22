package com.jim.recorder.bean;

public class FlagForCell {

    private int position;

    private long date;

    public FlagForCell(int position, long date) {
        this.position = position;
        this.date = date;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
