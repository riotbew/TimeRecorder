package com.jim.recorder.model;

import android.util.SparseArray;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

@Entity
public class DayCell {

    @Id
    private long id;
    private long time;

    @Convert(converter = DayConverter.class, columnType = String.class)
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
