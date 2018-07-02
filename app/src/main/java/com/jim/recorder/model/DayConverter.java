package com.jim.recorder.model;

import android.util.SparseArray;

import com.jim.recorder.api.EventTypeManager;
import com.jim.recorder.ui.model.ViewCell;

import org.greenrobot.greendao.converter.PropertyConverter;

public class DayConverter implements PropertyConverter<SparseArray<ViewCell>, String> {


    @Override
    public SparseArray<ViewCell> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null || databaseValue.length() == 0) {
            return null;
        }
        SparseArray<ViewCell> result = new SparseArray<>();
        String[] cells = databaseValue.split(",");
        ViewCell tmp;
        for (String item : cells) {
            String[] cell_props = item.split("\\|");
            int pos = Integer.valueOf(cell_props[1]);
            Long eventId = Long.valueOf(cell_props[0]);
            tmp = new ViewCell(eventId, pos);
            if (EventTypeManager.getInstance().getEventType(eventId) == null) {
                continue;
            }
            tmp.setType(EventTypeManager.getInstance().getEventType(tmp.getEventId()).getType());
            result.put(pos, tmp);
        }
        return result;
    }

    @Override
    public String convertToDatabaseValue(SparseArray<ViewCell> entityProperty) {
        int key;
        ViewCell item;
        StringBuilder result = new StringBuilder();
        for (int i=0; i<entityProperty.size(); i++) {
            key = entityProperty.keyAt(i);
            item = entityProperty.get(key);
            if (item != null) {
                result.append(item.getEventId()).append("|").append(item.getPosition()).append(",");
            }
        }
        if ( result.length() > 0 && result.charAt(result.length()-1) == ',') {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
}
