package com.jim.recorder.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.jim.recorder.R;

public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V,P> {

    protected final String TAG = this.getClass().getSimpleName();

    UserAction mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAction = new UserActionImpl(this);
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
//            //实现透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //实现透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
    }

    protected void initView() {

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolBar(toolbar);
        initView();
    }

    protected void setToolBar(Toolbar toolBar) {
//        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
//        如果contentView根布局添加了android:fitsSystemWindows="true"属性，请注释下面代码
        if (toolBar!= null)
            setSupportActionBar(toolBar);
    }

    @TargetApi(21)
    protected void setStatusBarColor(int statusColor) {
        mAction.setStatusBarColor(statusColor);
    }

    protected Dialog showCustomDialog(String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveCallback, boolean cancelable) {
        return mAction.showCustomDialog(title,message,positiveBtnText,positiveCallback,cancelable);
    }

    protected Dialog getBottomDialog(View contentView) {
        return mAction.getBottomDialog(contentView);
    }

    protected Snackbar getSnackbar(String text) {
        return Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE);
    }

    protected Activity getContext() {
        return this;
    }

    protected void hideToolBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

}
