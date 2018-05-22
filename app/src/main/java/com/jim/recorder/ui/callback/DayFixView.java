package com.jim.recorder.ui.callback;

import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.model.SingleModel;

import java.util.List;

/**
 * Created by Tauren on 2018/5/19.
 */

public interface DayFixView extends MvpView {
    void updateTitle(String text);
    void updateEventList(List<EventType> eventTypeList);
    void updateContent(List<SingleModel> viewData);
    void updateLeftCell(View v, SingleModel singleModel,int position);
    void labelCoverWarning(EventType eventType);
    void updateAfterFix();
    void updateSelectedIndicator(int count);
}
