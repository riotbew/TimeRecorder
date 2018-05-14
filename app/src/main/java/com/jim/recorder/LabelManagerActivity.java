package com.jim.recorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Tauren on 2018/4/24.
 */

public class LabelManagerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lalel_manager);
        init();
    }

    private void init() {
        findViewById(R.id.snackbar_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(android.R.id.content), "haha",Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}
