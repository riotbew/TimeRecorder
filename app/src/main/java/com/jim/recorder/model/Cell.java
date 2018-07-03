package com.jim.recorder.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Cell {

    @Id
    private Long time;

    @Property
    private int howLong;

    @Property
    private Long unit = Constants.one_min * 15;

    @Property
    private Long typeId = null;

    public Cell(Long time, int howLong, Long typeId) {
        this.time = time;
        this.howLong = howLong;
        this.typeId = typeId;
    }

    @Generated(hash = 368920696)
    public Cell(Long time, int howLong, Long unit, Long typeId) {
        this.time = time;
        this.howLong = howLong;
        this.unit = unit;
        this.typeId = typeId;
    }

    @Generated(hash = 739260143)
    public Cell() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
