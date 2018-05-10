package com.jim.recorder.model;

import android.util.Log;
import android.util.SparseArray;

import org.greenrobot.greendao.converter.PropertyConverter;

public class DayConverter implements PropertyConverter<SparseArray<Cell>, String> {


    @Override
    public SparseArray<Cell> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null || databaseValue.length() == 0) {
            return null;
        }
        SparseArray<Cell> result = new SparseArray<>();
        String[] cells = databaseValue.split(",");
        Cell tmp;
        for (String item : cells) {
            String[] cell_props = item.split("\\|");
            Log.i(DayConverter.class.getSimpleName(), item);
            int key = Integer.valueOf(cell_props[1]);
            tmp = new Cell(Integer.valueOf(cell_props[0]), key);
            result.put(key, tmp);
        }
        return result;
    }

    @Override
    public String convertToDatabaseValue(SparseArray<Cell> entityProperty) {
        int key;
        Cell item;
        StringBuilder result = new StringBuilder();
        for (int i=0; i<entityProperty.size(); i++) {
            key = entityProperty.keyAt(i);
            item = entityProperty.get(key);
            if (item != null) {
                result.append(item.getType()).append("|").append(item.getPosition()).append(",");
            }
        }
        if ( result.length() > 0 && result.charAt(result.length()-1) == ',') {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
}
