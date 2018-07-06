package com.jim.recorder.api;

import com.jim.recorder.MyApplication;
import com.jim.recorder.model.EventType;

import java.util.List;

public class EventTypeManager {

    private List<EventType> mEvents;

    private EventTypeManager() {
        super();
    }

    public static EventTypeManager getInstance() {
        return EventTypeManagerHolder.sInstance;
    }

    public synchronized List<EventType> getEventList() {
        if (mEvents == null || mEvents.size() == 0) {
            mEvents = DataStorage.getEventList();
        }
        return mEvents;
    }

    public void refreshEvent() {
        mEvents = DataStorage.getEventList();
    }

    public synchronized boolean addEvent(EventType event) {
        for (EventType item : mEvents) {
            if (item.getName().equals(event.getName())) {
                return false;
            }
        }
        mEvents.add(event);
        DataStorage.addEvent(event);
        return true;
    }

    public synchronized void updateEvent(EventType eventType) {
        MyApplication.getDaoInstant().getEventTypeDao().updateInTx(eventType);
    }

    public synchronized EventType getEventType(Long id) {
        for (EventType item : mEvents) {
            if (id.equals(item.get_id()))
                return item;
        }
        return null;
    }

    public synchronized void delEventType(EventType eventType) {
        int pos = -1;
        for (int i=0; i<mEvents.size(); i++) {
            if (mEvents.get(i).get_id().equals(eventType.get_id())) {
                pos = i;
            }
        }
        if (pos != -1) {
            mEvents.remove(pos);
        } else  {
            return;
        }
        DataStorage.delEvent(eventType.get_id());
    }

    private static class EventTypeManagerHolder {
        private static final EventTypeManager sInstance = new EventTypeManager();
    }
}
