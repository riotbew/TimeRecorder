package com.jim.recorder.ui.view;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.jim.recorder.R;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.common.BaseMvpActivity;
import com.jim.recorder.common.adapter.listview.CommonAdapter;
import com.jim.recorder.common.adapter.listview.ViewHolder;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.EventManagerView;
import com.jim.recorder.ui.pressenter.EventTypePressenter;
import com.jim.recorder.utils.ColorUtil;

/**
 * Created by Tauren on 2018/4/24.
 */

public class EventManagerActivity extends BaseMvpActivity<EventManagerView, EventTypePressenter> implements EventManagerView{

    final int ADD_EVENT = 0;

    CommonAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manager);
        init();
    }

    @NonNull
    @Override
    public EventTypePressenter createPresenter() {
        return new EventTypePressenter();
    }

    private void init() {
        ListView lv = findViewById(R.id.event_content_lv);
        mAdapter = new CommonAdapter<EventType>(this,R.layout.layout_manager_event_item, EventTypeManager.getEventList()) {

            @Override
            protected void convert(ViewHolder viewHolder, EventType item, int position) {
                View icon = viewHolder.getView(R.id.event_icon);
                GradientDrawable bg = (GradientDrawable) icon.getBackground();
                bg.setColor(ColorUtil.getColor(getContext(), item.getType()));
                viewHolder.setText(R.id.event_name, item.getName());
            }
        };
        lv.setAdapter(mAdapter);
    }

    @Override
    protected void setToolBar(Toolbar toolBar) {
        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
        toolBar.findViewById(R.id.tab_add_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddEvent();
            }
        });
        super.setToolBar(toolBar);
    }

    private void goToAddEvent() {
        startActivityForResult(new Intent(this, AddEventActivity.class), ADD_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void updateEventList() {
        mAdapter.notifyDataSetChanged();
    }
}
