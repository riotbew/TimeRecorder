package com.jim.recorder.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Cell {

    private boolean isSelected = false;
    @Property
    private int type = -1;
    private Long eventId = -1L;
    private int position;

    public Cell(boolean isSelected, int position) {
        this.isSelected = isSelected;
        this.position = position;
    }

    @Generated(hash = 557474950)
    public Cell(boolean isSelected, int type, Long eventId, int position) {
        this.isSelected = isSelected;
        this.type = type;
        this.eventId = eventId;
        this.position = position;
    }

    public Cell(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public Cell(Long eventId, int position) {
        this.eventId = eventId;
        this.position = position;
    }

    @Generated(hash = 739260143)
    public Cell() {
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
