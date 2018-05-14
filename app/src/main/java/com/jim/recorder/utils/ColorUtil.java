package com.jim.recorder.utils;

import android.content.Context;

import com.jim.recorder.R;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {

    private static ArrayList<Integer> colors = new ArrayList<>();

    static {
        colors.add(R.color.time_recoder_template_01);
        colors.add(R.color.time_recoder_template_2);
        colors.add(R.color.time_recoder_template_3);
        colors.add(R.color.time_recoder_template_4);
        colors.add(R.color.time_recoder_template_5);
        colors.add(R.color.time_recoder_template_6);
        colors.add(R.color.time_recoder_template_7);
        colors.add(R.color.time_recoder_template_8);
        colors.add(R.color.time_recoder_template_9);
        colors.add(R.color.time_recoder_template_10);
        colors.add(R.color.time_recoder_template_11);
        colors.add(R.color.time_recoder_template_12);
        colors.add(R.color.time_recoder_template_13);
        colors.add(R.color.time_recoder_template_14);
        colors.add(R.color.time_recoder_template_15);
        colors.add(R.color.time_recoder_template_16);
        colors.add(R.color.time_recoder_template_17);
        colors.add(R.color.time_recoder_template_18);
        colors.add(R.color.time_recoder_template_19);
        colors.add(R.color.time_recoder_template_20);
        colors.add(R.color.time_recoder_template_21);
        colors.add(R.color.time_recoder_template_22);
        colors.add(R.color.time_recoder_template_23);
        colors.add(R.color.time_recoder_template_24);
        colors.add(R.color.time_recoder_template_25);
        colors.add(R.color.time_recoder_template_26);
        colors.add(R.color.time_recoder_template_27);
        colors.add(R.color.time_recoder_template_28);
        colors.add(R.color.time_recoder_template_29);
        colors.add(R.color.time_recoder_template_30);
        colors.add(R.color.time_recoder_template_31);
        colors.add(R.color.time_recoder_template_32);
        colors.add(R.color.time_recoder_template_33);
        colors.add(R.color.time_recoder_template_34);
        colors.add(R.color.time_recoder_template_35);
    }

    public static int getColor(int type) {
        return colors.get(type);
    }

    public static int getColor(Context context, int type) {
        return context.getResources().getColor(colors.get(type));
    }

    public static List<Integer> getTemplateColors() {
        return colors;
    }

    public static int getColorByRes(Context context, int color) {
        return context.getResources().getColor(color);
    }
}
