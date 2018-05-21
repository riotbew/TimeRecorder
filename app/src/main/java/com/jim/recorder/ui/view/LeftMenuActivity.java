package com.jim.recorder.ui.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jim.recorder.R;
import com.jim.recorder.common.BaseActivity;
import com.jim.recorder.common.adapter.recyclerview.CommonAdapter;
import com.jim.recorder.common.adapter.recyclerview.MultiItemTypeAdapter;
import com.jim.recorder.common.adapter.recyclerview.base.ViewHolder;
import com.jim.recorder.ui.model.MenuModel;

import java.util.ArrayList;

/**
 * Created by Tauren on 2018/5/20.
 */

public class LeftMenuActivity extends BaseActivity {

    String from;
    RecyclerView rv;
    CommonAdapter adapter;
    ArrayList<MenuModel> mData = new ArrayList<>();
    Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_menu);
        mHandler = new Handler();
        Intent it = getIntent();
        if (it != null)
            from = it.getStringExtra("from");
        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        mData.add(new MenuModel("统计",-1));
        mData.add(new MenuModel("事件管理",-1));
        mData.add(new MenuModel("切换主页",-1));
        rv = findViewById(R.id.content_rv);
        adapter = new CommonAdapter<MenuModel>(this, R.layout.left_menu_item, mData) {
            @Override
            protected void convert(ViewHolder holder, MenuModel menuModel, int position) {
                holder.setText(R.id.menu_name, menuModel.getName());
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private void startActivity(int position) {
        Intent it;
        switch (position) {
            case 0:
                break;
            case 1:
                it = new Intent(this, EventManagerActivity.class);
                startActivity(it);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
                break;
            case 2:
                if (!TextUtils.isEmpty(from)) {
                    if (from.equals("MAIN_1")) {
                        it = new Intent(this, DayFixActivity.class);
                    } else {
                        it = new Intent(this, MainActivity.class);
                    }
                    it.putExtra("clear_other", true);
                    startActivity(it);
                }

                break;
        }
    }

}
