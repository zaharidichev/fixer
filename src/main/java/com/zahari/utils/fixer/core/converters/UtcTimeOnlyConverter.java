package com.zahari.utils.fixer.core.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by zdichev on 13/11/2015.
 */
public class UtcTimeOnlyConverter extends AbstractDateTimeConverter {
    private static ThreadLocal<UtcTimeOnlyConverter> utcTimeConverter = new ThreadLocal();
    private DateFormat utcTimeFormat = this.createDateFormat("HH:mm:ss");
    private DateFormat utcTimeFormatMillis = this.createDateFormat("HH:mm:ss.SSS");

    public UtcTimeOnlyConverter() {
    }

    public static String convert(Date d, boolean includeMilliseconds) {
        return getFormatter(includeMilliseconds).format(d);
    }

    private static DateFormat getFormatter(boolean includeMillis) {
        UtcTimeOnlyConverter converter = (UtcTimeOnlyConverter)utcTimeConverter.get();
        if(converter == null) {
            converter = new UtcTimeOnlyConverter();
            utcTimeConverter.set(converter);
        }

        return includeMillis?converter.utcTimeFormatMillis:converter.utcTimeFormat;
    }

    public static Date convert(String value) throws RuntimeException {
        Date d = null;

        try {
            d = getFormatter(value.length() == 12).parse(value);
        } catch (ParseException var3) {
            throwFieldConvertError(value, "time");
        }

        return d;
    }
}
