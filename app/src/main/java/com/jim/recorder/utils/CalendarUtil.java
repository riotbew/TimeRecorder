package com.jim.recorder.utils;

import com.jim.recorder.model.Constants;

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

    public static void setTimeZone() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        timezone = calendar.get(Calendar.HOUR_OF_DAY) * Constants.one_hour + calendar.get(Calendar.MINUTE) * Constants.one_min;
        if (calendar.get(Calendar.YEAR) != 1970) {
            timezone = Constants.one_day - timezone;
        }
    }
}
