package com.jim.recorder.api;

import com.jim.recorder.model.EventType;

import java.util.List;

public class EventTypeManager {

    private static List<EventType> mEvents;

    public synchronized static List<EventType> getEventList() {
        if (mEvents == null || mEvents.size() == 0) {
            mEvents = DataStorage.getEventList();
        }
        return mEvents;
    }

    public static void refreshEvent() {
        mEvents = DataStorage.getEventList();
    }

    public synchronized static boolean addEvent(EventType event) {
        for (EventType item : mEvents) {
            if (item.getName().equals(event.getName())) {
                return false;
            }
        }
        mEvents.add(event);
        DataStorage.addEvent(event);
        return true;
    }
}
