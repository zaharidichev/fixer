package com.zahari.utils.fixer.core.converters;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zdichev on 13/11/2015.
 */
public class SystemTime {
    public static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone("UTC");
    private static SystemTimeSource DEFAULT_TIME_SOURCE = new SystemTimeSource() {
        public long getTime() {
            return System.currentTimeMillis();
        }
    };
    private static SystemTimeSource systemTimeSource;

    public SystemTime() {
    }

    public static synchronized long currentTimeMillis() {
        return systemTimeSource.getTime();
    }

    public static Date getDate() {
        return new Date(currentTimeMillis());
    }

    public static synchronized void setTimeSource(SystemTimeSource systemTimeSource) {
        systemTimeSource = systemTimeSource != null ? systemTimeSource : DEFAULT_TIME_SOURCE;
    }

    public static Calendar getUtcCalendar() {
        Calendar c = Calendar.getInstance(UTC_TIMEZONE);
        c.setTimeInMillis(currentTimeMillis());
        return c;
    }

    public static Calendar getUtcCalendar(Date date) {
        Calendar c = getUtcCalendar();
        c.setTime(date);
        return c;
    }

    static {
        systemTimeSource = DEFAULT_TIME_SOURCE;
    }
}