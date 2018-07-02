package com.jim.recorder.ui.custom;

import com.jim.recorder.R;
import com.jim.common.adapter.recyclerview.base.ItemViewDelegate;
import com.jim.common.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Tauren on 2018/5/19.
 */

public class DayFixContentHeaderDelegate implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.layout_tv;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        return position % 5 == 0;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        holder.setText(R.id.time_indicator, position/5+":00");
    }
}
