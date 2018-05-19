package com.jim.recorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.jim.recorder.api.DataStorage;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.view.AddEventActivity;
import com.jim.recorder.ui.view.EventManagerActivity;
import com.jim.recorder.ui.view.MainActivity;

/**
 * Created by Tauren on 2018/4/19.
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demolist);
        initView();
    }

    private void initView() {

        LinearLayout demo_content = findViewById(R.id.demo_content);
        int childCount = demo_content.getChildCount();
        View item;
        for (int i=0; i< childCount; i++) {
            item = demo_content.getChildAt(i);
            item.setOnClickListener(this);
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent it;
        switch (id) {
            case R.id.demo1:
                it = new Intent(this, MainActivity.class);
                startActivity(it);
                break;
            case R.id.demo2:
                it = new Intent(this, EventManagerActivity.class);
                startActivity(it);
                break;
        }
    }
}
