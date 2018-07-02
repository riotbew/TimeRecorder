package com.jim.recorder.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Cell {

    @Id
    private Long mTime;

    @Property
    private int mHowLong;

    @Property
    private Long mUnit = Constants.one_min * 15;

    @Property
    private Long mTypeId = null;

    @Generated(hash = 347678275)
    public Cell(Long mTime, int mHowLong, Long mUnit, Long mTypeId) {
        this.mTime = mTime;
        this.mHowLong = mHowLong;
        this.mUnit = mUnit;
        this.mTypeId = mTypeId;
    }

    @Generated(hash = 739260143)
    public Cell() {
    }

    public Long getmTime() {
        return mTime;
    }

    public void setmTime(Long mTime) {
        this.mTime = mTime;
    }

    public int getmHowLong() {
        return mHowLong;
    }

    public void setmHowLong(int mHowLong) {
        this.mHowLong = mHowLong;
    }

    public Long getmUnit() {
        return mUnit;
    }

    public void setmUnit(Long mUnit) {
        this.mUnit = mUnit;
    }

    public Long getmTypeId() {
        return mTypeId;
    }

    public void setmTypeId(Long mTypeId) {
        this.mTypeId = mTypeId;
    }

    public Long getMTime() {
        return this.mTime;
    }

    public void setMTime(Long mTime) {
        this.mTime = mTime;
    }

    public int getMHowLong() {
        return this.mHowLong;
    }

    public void setMHowLong(int mHowLong) {
        this.mHowLong = mHowLong;
    }

    public Long getMUnit() {
        return this.mUnit;
    }

    public void setMUnit(Long mUnit) {
        this.mUnit = mUnit;
    }

    public Long getMTypeId() {
        return this.mTypeId;
    }

    public void setMTypeId(Long mTypeId) {
        this.mTypeId = mTypeId;
    }
}
