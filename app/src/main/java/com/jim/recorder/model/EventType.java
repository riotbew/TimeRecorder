package com.jim.recorder.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EventType {

    @Id(autoincrement = true)
    private long _id;

    @Property
    private String name;

    @Property
    private int type = -1;

    @Property
    private int categoryType = -1;

    public EventType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    @Generated(hash = 1832760375)
    public EventType(long _id, String name, int type, int categoryType) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.categoryType = categoryType;
    }

    @Generated(hash = 1218244869)
    public EventType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long get_id() {
        return this._id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }
}
