package com.jim.recorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.jim.common.BaseActivity;
import com.jim.recorder.demo.RxJavaActivity;
import com.jim.recorder.ui.view.DayFixNewActivity;
import com.jim.recorder.ui.view.MainActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Tauren on 2018/4/19.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    final Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demolist);
    }

    protected void initView() {

        final LinearLayout demo_content = findViewById(R.id.demo_content);
        if (demo_content == null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getContext(), DayFixNewActivity.class));
                    finish();
                }
            }, 1000);
            return;
        }
        final int childCount = demo_content.getChildCount();
        View item;
        for (int i=0; i< childCount; i++) {
            item = demo_content.getChildAt(i);
            item.setOnClickListener(this);
        }
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
                it = new Intent(this, DayFixNewActivity.class);
                startActivity(it);
                break;
            case R.id.demo3:
                startActivity(new Intent(this, RxJavaActivity.class));

                break;
        }
    }
}
