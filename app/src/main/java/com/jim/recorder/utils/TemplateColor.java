package com.jim.recorder.utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class TemplateColor {

    private static ArrayList<Integer> colors = new ArrayList<>();

    static {
//        colors.add(R.color.time_recoder_template_01);
//        colors.add(R.color.time_recoder_template_2);
//        colors.add(R.color.time_recoder_template_3);
//        colors.add(R.color.time_recoder_template_4);
//        colors.add(R.color.time_recoder_template_5);
//        colors.add(R.color.time_recoder_template_6);
//        colors.add(R.color.time_recoder_template_7);
//        colors.add(R.color.time_recoder_template_8);
//        colors.add(R.color.time_recoder_template_9);
//        colors.add(R.color.time_recoder_template_10);
//        colors.add(R.color.time_recoder_template_11);
//        colors.add(R.color.time_recoder_template_12);
//        colors.add(R.color.time_recoder_template_13);
//        colors.add(R.color.time_recoder_template_14);
//        colors.add(R.color.time_recoder_template_15);
//        colors.add(R.color.time_recoder_template_16);
//        colors.add(R.color.time_recoder_template_17);
//        colors.add(R.color.time_recoder_template_18);
//        colors.add(R.color.time_recoder_template_19);
//        colors.add(R.color.time_recoder_template_20);
//        colors.add(R.color.time_recoder_template_21);
//        colors.add(R.color.time_recoder_template_22);
//        colors.add(R.color.time_recoder_template_23);
//        colors.add(R.color.time_recoder_template_24);
//        colors.add(R.color.time_recoder_template_25);
//        colors.add(R.color.time_recoder_template_26);
//        colors.add(R.color.time_recoder_template_27);
//        colors.add(R.color.time_recoder_template_28);
//        colors.add(R.color.time_recoder_template_29);
//        colors.add(R.color.time_recoder_template_30);
//        colors.add(R.color.time_recoder_template_31);
//        colors.add(R.color.time_recoder_template_32);
//        colors.add(R.color.time_recoder_template_33);
//        colors.add(R.color.time_recoder_template_34);
//        colors.add(R.color.time_recoder_template_35);
        colors.add(Color.parseColor("#d73543"));
        colors.add(Color.parseColor("#e9551e"));
        colors.add(Color.parseColor("#e67620"));
        colors.add(Color.parseColor("#f09626"));
        colors.add(Color.parseColor("#fabe60"));
        colors.add(Color.parseColor("#fed130"));
        colors.add(Color.parseColor("#c0d72c"));
        colors.add(Color.parseColor("#88dc8d"));
        colors.add(Color.parseColor("#2aba9d"));
        colors.add(Color.parseColor("#45b171"));
        colors.add(Color.parseColor("#345d41"));
        colors.add(Color.parseColor("#3d6f83"));
        colors.add(Color.parseColor("#24c3cf"));
        colors.add(Color.parseColor("#6bcae9"));
        colors.add(Color.parseColor("#23a9d9"));
        colors.add(Color.parseColor("#51659e"));
        colors.add(Color.parseColor("#3461c0"));
        colors.add(Color.parseColor("#361298"));
        colors.add(Color.parseColor("#4d2b4d"));
        colors.add(Color.parseColor("#674997"));
        colors.add(Color.parseColor("#874b96"));
        colors.add(Color.parseColor("#b45096"));
        colors.add(Color.parseColor("#e664a0"));
        colors.add(Color.parseColor("#f082c3"));
        colors.add(Color.parseColor("#f0717d"));
        colors.add(Color.parseColor("#9bacd4"));
        colors.add(Color.parseColor("#646f8c"));
        colors.add(Color.parseColor("#75302b"));
        colors.add(Color.parseColor("#5d4634"));
        colors.add(Color.parseColor("#a38571"));
        colors.add(Color.parseColor("#d7c396"));
        colors.add(Color.parseColor("#f0dcb4"));
        colors.add(Color.parseColor("#bec3c8"));
        colors.add(Color.parseColor("#707070"));
        colors.add(Color.parseColor("#010101"));
    }

    public static int getColor(int type) {
        return colors.get(type);
    }

    /**
     * 应用预设的颜色列表，与事件的type属性对应
     */
    public static List<Integer> getColors() {
        return colors;
    }

}
