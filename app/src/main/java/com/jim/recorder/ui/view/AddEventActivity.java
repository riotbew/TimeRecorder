package com.jim.recorder.ui.view;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jim.recorder.R;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.common.BaseActivity;
import com.jim.recorder.common.adapter.recyclerview.CommonAdapter;
import com.jim.recorder.common.adapter.recyclerview.MultiItemTypeAdapter;
import com.jim.recorder.common.adapter.recyclerview.base.ViewHolder;
import com.jim.recorder.ui.model.EventColor;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.custom.BamAnim;
import com.jim.recorder.utils.TemplateColor;

import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends BaseActivity implements View.OnClickListener{

    private CommonAdapter mAdapter;
    private List<EventColor> mData;
    private int mSelected = -1;
    private EditText event_name_input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preData();
        setContentView(R.layout.activity_add_event);
    }

    private void preData() {
        List<EventType> eventTypes = EventTypeManager.getInstance().getEventList();
        mData = new ArrayList<>();
        for (Integer item : TemplateColor.getColors()) {
            mData.add(new EventColor(item, 0));
        }
        EventType item;
        for (int i=0 ; i<eventTypes.size(); i++) {
            item = eventTypes.get(i);
            mData.get(item.getType()).add(1);
        }
    }

    protected void initView() {
        final RecyclerView recyclerView = findViewById(R.id.color_content_rv);
        event_name_input = findViewById(R.id.event_name_input);
        mAdapter = new CommonAdapter<EventColor>(this, R.layout.layout_add_event_color_item, mData) {
            @Override
            protected void convert(ViewHolder holder, EventColor eventColor, int position) {
                View contentView = holder.getConvertView();
                GradientDrawable bg;
                if ((bg = (GradientDrawable) contentView.getBackground()) == null) {
                    bg = new GradientDrawable();
                }
                bg.setColor(eventColor.getColor());
                contentView.setBackground(bg);

                TextView tv = holder.getView(R.id.color_content);
                tv.setText(String.valueOf(eventColor.getSize()));

                contentView.findViewById(R.id.select_icon).setVisibility(View.GONE);
            }
        };
        //点击
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, RecyclerView.ViewHolder holder, int position) {
                if (animing) {
                    return;
                }
                if (mSelected != -1 && position != mSelected) {
                    View child = recyclerView.getChildAt(mSelected);
                    child.findViewById(R.id.select_icon).setVisibility(View.GONE);
                }
                mSelected = position;
                startBtnAnim(view);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,7);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void startBtnAnim(final View view) {
        animing = true;
        BamAnim.setScale(0.5f);
        final int pivot = BamAnim.startAnimDown(view,false ,0,0);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                BamAnim.startAnimUp(view, pivot);
                view.findViewById(R.id.select_icon).setVisibility(View.VISIBLE);
                animing =false;
            }
        }, 150);
        BamAnim.setScale(0.95f);
    }

    private boolean animing = false;

    @Override
    protected void setToolBar(Toolbar toolBar) {
        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
        findViewById(R.id.btnRight).setOnClickListener(this);
        super.setToolBar(toolBar);
        hideToolBarTitle();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRight:
                saveEvent();
                break;
            default:
                break;
        }

    }

    private void saveEvent() {
        if (mSelected == -1) {
            getSnackbar("请选择事件类型的颜色", Snackbar.LENGTH_SHORT).show();
            return;
        }
        String name = event_name_input.getText().toString();
        if (name.length() == 0) {
            getSnackbar("请输入事件类型的名称", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!EventTypeManager.getInstance().addEvent(new EventType(name, mSelected))) {
            getSnackbar("存在相同事件类型", Snackbar.LENGTH_SHORT).show();
            return;
        }
        setResult(1);
        finish();
    }
}
