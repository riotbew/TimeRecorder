package com.jim.recorder.ui.pressenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.jim.recorder.api.DataStorage;
import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.EventManagerView;
import com.jim.recorder.ui.callback.MainView;

/**
 * Created by Tauren on 2018/5/16.
 */

public class EventTypePressenter extends MvpBasePresenter<EventManagerView> {

    public void delEvent(EventType type) {
        EventTypeManager.getInstance().delEventType(type);
        getView().updateEventList();
    }
}
