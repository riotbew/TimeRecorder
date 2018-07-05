package com.jim.recorder.ui.custom;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.jim.recorder.R;

public class CalendarAdapter implements CaledarAdapter {
    @Override
    public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
        TextView view;
        if (convertView == null) {
            convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.layout_item_calendar, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(48), px(48));
            convertView.setLayoutParams(params);
        }
        view = convertView.findViewById(R.id.text);
        view.setText("" + bean.day);
        if (bean.mothFlag != 0) {
            view.setTextColor(0xff9299a1);
        } else {
            view.setTextColor(0xffffffff);
        }
        return convertView;
    }

    public int px(float dipValue) {
        Resources r=Resources.getSystem();
        final float scale =r.getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
