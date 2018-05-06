package com.jim.recorder.model;

public class FlagForCell {

    private int position;

    private long date;

    private boolean isSelected=false;

    public FlagForCell(int position, long date) {
        this.position = position;
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
