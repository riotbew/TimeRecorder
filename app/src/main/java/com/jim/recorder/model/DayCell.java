package com.jim.recorder.model;

import android.util.SparseArray;

public class DayCell {

    private long time;
    private SparseArray<Cell> datas = new SparseArray<>();

    public DayCell(long time) {
        this.time = time;
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
}
