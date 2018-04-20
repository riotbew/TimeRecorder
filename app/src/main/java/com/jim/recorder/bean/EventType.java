package com.jim.recorder.bean;

import android.support.annotation.ColorInt;

/**
 * Created by Tauren on 2018/4/20.
 */

public class EventType {

    private String name;
    int type;
    @ColorInt
    int color;

    public EventType(String name, int type, int color) {
        this.name = name;
        this.type = type;
        this.color = color;
    }
}
