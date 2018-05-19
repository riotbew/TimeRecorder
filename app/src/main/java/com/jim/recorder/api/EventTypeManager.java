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

    public synchronized static EventType getEventType(Long id) {
        for (EventType item : mEvents) {
            if (id.equals(item.get_id()))
                return item;
        }
        return null;
    }

    public synchronized static void delEventType(EventType eventType) {
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
}
