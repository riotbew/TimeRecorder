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
    private Long typeId = null;

    @Generated(hash = 406049515)
    public Cell(Long time, Long typeId) {
        this.time = time;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
