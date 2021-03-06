package com.jim.recorder.ui.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jim.common.adapter.recyclerview.base.ViewHolder;
import com.jim.recorder.R;
import com.jim.recorder.api.EventTypeManager;
import com.jim.common.BaseMvpActivity;
import com.jim.common.adapter.recyclerview.CommonAdapter;
import com.jim.recorder.model.Constants;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.EventManagerView;
import com.jim.recorder.ui.custom.MyDecoration;
import com.jim.recorder.ui.custom.SwipeItemLayout;
import com.jim.recorder.ui.pressenter.EventTypePressenter;
import com.jim.recorder.utils.TemplateColor;

/**
 * Created by Tauren on 2018/4/24.
 */

public class EventManagerActivity extends BaseMvpActivity<EventManagerView, EventTypePressenter> implements EventManagerView{

    final int ADD_EVENT = 0;


    CommonAdapter mAdapter;
    RecyclerView mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manager);
    }

    @NonNull
    @Override
    public EventTypePressenter createPresenter() {
        return new EventTypePressenter();
    }

    protected void initView() {
        mContent = findViewById(R.id.event_content_rv);
        mAdapter = new CommonAdapter<EventType>(this, R.layout.layout_manager_event_item,
                EventTypeManager.getInstance().getEventList()) {
            @Override
            protected void convert(ViewHolder holder, final EventType eventType, final int position) {
                View icon = holder.getView(R.id.event_icon);
                GradientDrawable bg = (GradientDrawable) icon.getBackground();
                bg.setColor(TemplateColor.getColor(eventType.getType()));
                holder.setText(R.id.event_name, eventType.getName());
                holder.getView(R.id.swipe_del_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleDelAction(eventType);
                    }
                });
                holder.getView(R.id.event_setting).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToAddEvent(eventType.get_id());
                    }
                });
            }
        };
        mContent.setAdapter(mAdapter);
        mContent.setLayoutManager(new LinearLayoutManager(this));
        final SwipeItemLayout.OnSwipeItemTouchListener listener = new SwipeItemLayout.OnSwipeItemTouchListener(this);
        mContent.addOnItemTouchListener(listener);
        mContent.addItemDecoration(new MyDecoration(this, LinearLayoutManager.VERTICAL));
        SharedPreferences sp = this.getSharedPreferences(Constants.SETTING_NAME, Context.MODE_PRIVATE);
        if (sp!=null && !sp.contains(Constants.FIRST_OPEN_EVENT_MG)) {
            mContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    View view = mContent.getChildAt(0);
                    if (view != null && view instanceof SwipeItemLayout) {
                        SwipeItemLayout layout = (SwipeItemLayout)view;
                        layout.open();
                        listener.setCaptureItem(layout);
                    }
                }
            },100);
            sp.edit().putBoolean(Constants.FIRST_OPEN_EVENT_MG, true).apply();
        }

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

    private void goToAddEvent(long id) {
        startActivityForResult(new Intent(this, AddEventActivity.class).putExtra(Constants.MODIFY_EVENT_ID, id), ADD_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.TIME_RECORDER_EVENT_UPDATE));
            mAdapter.notifyDataSetChanged();
            mContent.smoothScrollToPosition(EventTypeManager.getInstance().getEventList().size());
        }
    }

    @Override
    public void updateEventList() {
        mAdapter.notifyDataSetChanged();
    }

    private void handleDelAction(final EventType eventType) {
        showCustomDialog(null, "该事件类型将被删除，是否继续？", "继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().delEvent(eventType);
                LocalBroadcastManager.getInstance(EventManagerActivity.this).sendBroadcast(new Intent(Constants.TIME_RECORDER_EVENT_UPDATE));
                dialog.dismiss();
            }
        }, false);
    }
}
