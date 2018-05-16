package com.jim.recorder.ui.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jim.recorder.R;
import com.jim.recorder.common.BaseActivity;
import com.jim.recorder.ui.callback.EventManagerView;
import com.jim.recorder.ui.pressenter.EventTypePressenter;

/**
 * Created by Tauren on 2018/4/24.
 */

public class EventManagerActivity extends BaseActivity<EventManagerView, EventTypePressenter> implements EventManagerView{

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
        findViewById(R.id.snackbar_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(android.R.id.content), "haha",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setToolBar() {
        super.setToolBar();
        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
    }

    @Override
    public void updateEventList() {

    }
}
