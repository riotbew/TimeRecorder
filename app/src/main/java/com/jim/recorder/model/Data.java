package com.jim.recorder.model;

import java.util.LinkedList;

/**
 * Created by Tauren on 2018/4/20.
 */

public class Data {

    LinkedList<Cell> list;
    long time;

    public Data(long time) {
        this.time = time;
    }

    public LinkedList<Cell> getList() {
        return list;
    }

    public void setList(LinkedList<Cell> list) {
        this.list = list;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
