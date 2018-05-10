package com.jim.recorder.model;

import android.util.SparseArray;

import org.greenrobot.greendao.converter.PropertyConverter;

public class DayConverter implements PropertyConverter<SparseArray<Cell>, String> {


    @Override
    public SparseArray<Cell> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null || databaseValue.length() == 0) {
            return null;
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(SparseArray<Cell> entityProperty) {
        int key;
        Cell item;
        StringBuilder result = new StringBuilder();
        for (int i=0; i<entityProperty.size(); i++) {
            key = entityProperty.keyAt(i);
            item = entityProperty.get(key);
            result.append(item.getType()).append("|").append(item.getPosition()).append(",");
        }
        if ( result.length() > 0 && result.charAt(result.length()) == ',') {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
}
