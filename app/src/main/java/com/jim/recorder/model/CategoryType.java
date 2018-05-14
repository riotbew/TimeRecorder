package com.jim.recorder.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CategoryType {

    @Id(autoincrement = true)
    private long _id;

    @Property
    private int type=-1;

    @Property
    private String name="";

    @Property
    private String nickName="";

    public CategoryType(int type, String name, String nickName) {
        this.type = type;
        this.name = name;
        this.nickName = nickName;
    }

    public CategoryType(int type) {
        this.type = type;
    }

    @Generated(hash = 690985589)
    public CategoryType(long _id, int type, String name, String nickName) {
        this._id = _id;
        this.type = type;
        this.name = name;
        this.nickName = nickName;
    }

    @Generated(hash = 1628124281)
    public CategoryType() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long get_id() {
        return this._id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
