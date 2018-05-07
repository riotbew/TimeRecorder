package com.jim.recorder.ui.callback;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.jim.recorder.model.Cell;
import com.jim.recorder.model.Data;

public interface MainView extends MvpView {

    void todayPosition(int position);
    void updateCellAfterClick(View v, Cell cell);
    void resetLeftDayCellView(ViewGroup content,final Data item);
    void updateLeftDayCellView(ViewGroup content, final Data item, SparseArray<Cell> cells);
    void refreshLeft();
}
