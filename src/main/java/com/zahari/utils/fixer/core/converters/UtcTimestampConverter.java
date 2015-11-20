package com.zahari.utils.fixer.core.converters;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by zdichev on 13/11/2015.
 */
public class UtcTimestampConverter extends AbstractDateTimeConverter {
    private static ThreadLocal<UtcTimestampConverter> utcTimestampConverter = new ThreadLocal();
    private final DateFormat utcTimestampFormat = this.createDateFormat("yyyyMMdd-HH:mm:ss");
    private final DateFormat utcTimestampFormatMillis = this.createDateFormat("yyyyMMdd-HH:mm:ss.SSS");
    private static HashMap<String, Long> dateCache = new HashMap();

    public UtcTimestampConverter() {
    }

    public static String convert(Date d, boolean includeMilliseconds) {
        return getFormatter(includeMilliseconds).format(d);
    }

    private static DateFormat getFormatter(boolean includeMillis) {
        UtcTimestampConverter converter = (UtcTimestampConverter)utcTimestampConverter.get();
        if(converter == null) {
            converter = new UtcTimestampConverter();
            utcTimestampConverter.set(converter);
        }

        return includeMillis?converter.utcTimestampFormatMillis:converter.utcTimestampFormat;
    }

    public static Date convert(String value) throws RuntimeException {
        verifyFormat(value);
        long timeOffset = parseLong(value.substring(9, 11)) * 3600000L + parseLong(value.substring(12, 14)) * 60000L + parseLong(value.substring(15, 17)) * 1000L;
        if(value.length() == 21) {
            timeOffset += parseLong(value.substring(18, 21));
        }

        return new Date(getMillisForDay(value).longValue() + timeOffset);
    }

    private static Long getMillisForDay(String value) {
        String dateString = value.substring(0, 8);
        Long millis = (Long)dateCache.get(dateString);
        if(millis == null) {
            GregorianCalendar c = new GregorianCalendar(1970, 0, 1, 0, 0, 0);
            c.setTimeZone(SystemTime.UTC_TIMEZONE);
            int year = Integer.parseInt(value.substring(0, 4));
            int month = Integer.parseInt(value.substring(4, 6));
            int day = Integer.parseInt(value.substring(6, 8));
            c.set(year, month - 1, day);
            millis = Long.valueOf(c.getTimeInMillis());
            dateCache.put(dateString, Long.valueOf(c.getTimeInMillis()));
        }

        return millis;
    }

    private static void verifyFormat(String value) throws RuntimeException {
        String type = "timestamp";
        if(value.length() != 17 && value.length() != 21) {
            throwFieldConvertError(value, type);
        }

        assertDigitSequence(value, 0, 8, type);
        assertSeparator(value, 8, '-', type);
        assertDigitSequence(value, 9, 11, type);
        assertSeparator(value, 11, ':', type);
        assertDigitSequence(value, 12, 14, type);
        assertSeparator(value, 14, ':', type);
        assertDigitSequence(value, 15, 17, type);
        if(value.length() == 21) {
            assertSeparator(value, 17, '.', type);
            assertDigitSequence(value, 18, 21, type);
        } else if(value.length() != 17) {
            throwFieldConvertError(value, type);
        }

    }
}

