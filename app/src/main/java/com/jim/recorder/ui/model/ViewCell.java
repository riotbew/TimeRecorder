package com.jim.recorder.ui.model;

public class ViewCell {

    private boolean isSelected = false;
    private int type = -1;
    private Long eventId = -1L;
    private int position;

    public ViewCell(boolean isSelected, int position) {
        this.isSelected = isSelected;
        this.position = position;
    }

    public ViewCell(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public ViewCell(Long eventId, int position) {
        this.eventId = eventId;
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

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
