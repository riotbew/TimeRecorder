package com.jim.recorder.common;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
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

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V,P> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            //实现透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //实现透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setToolBar();
    }

    protected void setToolBar() {
//        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
//        如果contentView根布局添加了android:fitsSystemWindows="true"属性，请注释下面代码
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            lp.setMargins(0, getStatusBarHeight(), 0,0);
            toolbar.setLayoutParams(lp);
        }
    }

    @TargetApi(21)
    protected void setStatusBarColor(int statusColor) {
        Window window = getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    protected int getStatusBarHeight() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("TAG", "statusbar的高度： "+statusBarHeight+"");
        return statusBarHeight;
    }

    protected AlertDialog showDialog(String title, String message, View contentView,
                                     String positiveBtnText, String negativeBtnText,
                                     DialogInterface.OnClickListener positiveCallback,
                                     DialogInterface.OnClickListener negativeCallback,
                                     boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title == null ? "提示" : title);
        if (message != null) {
            builder.setMessage(message);
        }
        if (contentView != null) {
            builder.setView(contentView);
        }
        if (positiveBtnText != null) {
            builder.setPositiveButton(positiveBtnText, positiveCallback);
        }
        if (negativeBtnText != null) {
            builder.setNegativeButton(negativeBtnText, negativeCallback);
        }
        builder.setCancelable(cancelable);
        return builder.show();
    }
    //普通对话框
    protected AlertDialog showSimpleDialog(Context context, String title, String message,
                                               String positiveBtnText, String negativeBtnText,
                                               DialogInterface.OnClickListener positiveCallback,
                                               DialogInterface.OnClickListener negativeCallback,
                                               boolean cancelable) {
        return showDialog(title, message, null, positiveBtnText, negativeBtnText, positiveCallback, negativeCallback, cancelable);
    }

    protected AlertDialog showCustomDialog(String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveCallback, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title == null ? "提示" : title);
        if (message != null) {
            builder.setMessage(message);
        }
        if (positiveCallback != null) {
            builder.setPositiveButton(positiveBtnText == null?"确定":positiveBtnText, positiveCallback);
        }

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(cancelable);
        return builder.show();
    }

    protected Dialog getBottomDialog(View contentView) {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
//        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        contentView.setLayoutParams(layoutParams);
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        return bottomDialog;
    }

    protected Snackbar getSnackbar(String text) {
        return Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE);
    }

}
