package com.jim.recorder.ui.custom;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jim.recorder.R;
import com.jim.recorder.utils.ColorUtil;

import java.util.List;

/**
 * Created by Tauren on 2018/5/17.
 */

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.ColorHolder> {

    private Context context;
    private List<Integer> datas;

    public ColorListAdapter(Context context, List<Integer> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColorHolder(View.inflate(this.context, R.layout.layout_add_event_color_item, null));
    }

    @Override
    public void onBindViewHolder(ColorHolder holder, int position) {
        GradientDrawable bg = (GradientDrawable) holder.num.getBackground();
        bg.setColor(ColorUtil.getColorByRes(context, datas.get(position)));
        bg.setStroke(1, ColorUtil.getColorByRes(context, datas.get(position)));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ColorHolder extends RecyclerView.ViewHolder {
        TextView num;
        View contentView;
        ColorHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            num = itemView.findViewById(R.id.color_content);
        }
    }
}
