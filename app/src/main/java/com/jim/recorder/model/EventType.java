package com.jim.recorder.model;

import android.util.LongSparseArray;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EventType {

    @Id(autoincrement = true)
    private Long _id;

    @Property
    private String name;

    /**
     * 配套默认显示颜色使用，取值范围：[0-35]; -1：未使用
     */
    @Property
    private int type = -1;

    /**
     * 这个是多余属性，建议删除
     */
    @Property
    private boolean show = true;

    @Property
    private int categoryType = -1;

    /**
     * 尚未使用的属性，用于标记当前显示的位置
     */
    @Property
    private int position = -1;

    public EventType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    @Generated(hash = 244459173)
    public EventType(Long _id, String name, int type, boolean show,
            int categoryType, int position) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.show = show;
        this.categoryType = categoryType;
        this.position = position;
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

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getShow() {
        return this.show;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
