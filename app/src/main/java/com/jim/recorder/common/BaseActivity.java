package com.jim.recorder.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jim.recorder.R;

/**
 * Created by Tauren on 2018/5/17.
 */

public class BaseActivity extends AppCompatActivity {
    UserAction mAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAction = new UserActionImpl(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolBar(toolbar);
    }

    protected void setToolBar(Toolbar toolBar) {
//        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
//        如果contentView根布局添加了android:fitsSystemWindows="true"属性，请注释下面代码
        if (toolBar != null)
            setSupportActionBar(toolBar);
    }

    protected Dialog showCustomDialog(String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveCallback, boolean cancelable) {
        return mAction.showCustomDialog(title,message,positiveBtnText,positiveCallback,cancelable);
    }

    protected Dialog getBottomDialog(View contentView) {
        return mAction.getBottomDialog(contentView);
    }

    protected void setStatusBarColor(int statusColor) {
        mAction.setStatusBarColor(statusColor);
    }

    protected Snackbar getSnackbar(String text) {
        return Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE);
    }

    protected Activity getContext() {
        return this;
    }
}
