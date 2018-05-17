package com.jim.recorder.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * Created by Tauren on 2018/5/17.
 */

public interface UserAction {
    void setStatusBarColor(@ColorInt int color);
    Dialog showCustomDialog(String title, String message, String positiveBtnText,
                            DialogInterface.OnClickListener positiveCallback, boolean cancelable);
    Dialog getBottomDialog(View contentView);
    int getStatusBarHeight();
}
