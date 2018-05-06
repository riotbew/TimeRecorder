package com.jim.recorder.utils;

import java.util.Calendar;

import static com.jim.recorder.model.Constants.one_day;
import static com.jim.recorder.model.Constants.timezone;

/**
 * Created by Tauren on 2018/5/5.
 */

public class CalendarUtil {

    public static Calendar getCalendarDayStart(Calendar param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((param.getTimeInMillis() - param.getTimeInMillis()%one_day)-timezone);
        return calendar;
    }
}
