package com.jim.recorder.ui.model;

/**
 * Created by Tauren on 2018/5/20.
 */

public class MenuModel {
    private String name;
    private int drawable;

    public MenuModel(String name, int drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
