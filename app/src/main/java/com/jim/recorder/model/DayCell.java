package com.jim.recorder.model;

import android.util.SparseArray;

import com.jim.recorder.ui.model.ViewCell;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DayCell {

    @Id
    private long time;

    @Convert(converter = DayConverter.class, columnType = String.class)
    private SparseArray<ViewCell> datas = new SparseArray<>();

    public DayCell(long time) {
        this.time = time;
    }

    @Generated(hash = 2073067504)
    public DayCell(long time, SparseArray<ViewCell> datas) {
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

    public void put(int pos, ViewCell viewCell) {
        datas.put(pos, viewCell);
    }

    public ViewCell get(int pos) {
        return datas.get(pos);
    }

    public SparseArray<ViewCell> getDatas() {
        return this.datas;
    }

    public void setDatas(SparseArray<ViewCell> datas) {
        this.datas = datas;
    }
}
