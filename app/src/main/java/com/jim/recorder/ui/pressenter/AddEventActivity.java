package com.jim.recorder.ui.pressenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jim.recorder.R;
import com.jim.recorder.common.BaseActivity;
import com.jim.recorder.ui.custom.ColorListAdapter;
import com.jim.recorder.utils.ColorUtil;

/**
 * Created by Tauren on 2018/5/16.
 */

public class AddEventActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    @Override
    protected void setToolBar(Toolbar toolBar) {
        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
        findViewById(R.id.tab_back).setOnClickListener(this);
        findViewById(R.id.tab_add_right).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.color_content_rv);
        mAdapter = new ColorListAdapter(this, ColorUtil.getTemplateColors());
        recyclerView.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,7);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tab_back:
                finish();
                break;
            case R.id.tab_add_right:
                break;
            default:
                break;
        }

    }
}
