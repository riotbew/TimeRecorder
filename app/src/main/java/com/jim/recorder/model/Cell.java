package com.jim.recorder.model;

/**
 * Created by Tauren on 2018/4/20.
 */

public class Cell {
    private boolean isSelected = false;
    private int type = -1;
    private int position;

    public Cell(boolean isSelected, int position) {
        this.isSelected = isSelected;
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
