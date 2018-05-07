package com.jim.recorder.utils;

import com.jim.recorder.R;

import java.util.ArrayList;

public class ColorUtil {

    private static ArrayList<Integer> colors = new ArrayList<>();

    static {
        colors.add(0, R.color.azure);
        colors.add(1, R.color.blue);
        colors.add(2, R.color.orange_color);
        colors.add(3, R.color.colorPrimaryDark);
        colors.add(4, R.color.colorPrimary);
        colors.add(5, R.color.colorAccent);
    }

    public static int getColor(int type) {
        return colors.get(type);
    }
}
