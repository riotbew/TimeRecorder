package com.jim.recorder.model;

import android.util.SparseArray;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DayCell {

    @Id
    private long time;

    @Convert(converter = DayConverter.class, columnType = String.class)
    private SparseArray<Cell> datas = new SparseArray<>();

    public DayCell(long time) {
        this.time = time;
    }

    @Generated(hash = 208389714)
    public DayCell(long time, SparseArray<Cell> datas) {
        this.time = time;
        this.datas = datas;
    }

    @Generated(hash = 2108147076)
    public DayCell() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int size() {
        return datas.size();
    }

    public void put(int pos, Cell cell) {
        datas.put(pos, cell);
    }

    public Cell get(int pos) {
        return datas.get(pos);
    }

    public SparseArray<Cell> getDatas() {
        return this.datas;
    }

    public void setDatas(SparseArray<Cell> datas) {
        this.datas = datas;
    }
}
