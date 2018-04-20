package com.jim.recorder.bean;

/**
 * Created by Tauren on 2018/4/20.
 */

public class Cell {
    private boolean isChoosen = false;
    private int type;
    private long date;

    public Cell(boolean isChoosen, int type, long date) {
        this.isChoosen = isChoosen;
        this.type = type;
        this.date = date;
    }

    public boolean isChoosen() {
        return isChoosen;
    }

    public void setChoosen(boolean choosen) {
        isChoosen = choosen;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
