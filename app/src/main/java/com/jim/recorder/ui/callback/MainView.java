package com.jim.recorder.ui.callback;

import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.jim.recorder.ui.model.ViewCell;
import com.jim.recorder.ui.model.MainViewData;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;

public interface MainView extends MvpView {

    void todayPosition(int position);
    void updateCellAfterClick(View v, ViewCell viewCell);
    void resetLeftDayCellView(ViewGroup content,final MainViewData item);
    void updateLeftDayCellView(ViewGroup content, final MainViewData item, DayCell cells);
    void refreshLeft();
    void labelCoverWarning(EventType type);
    void updateSelectIndicator(int count);
    void labelWipeWarning();
}
