package com.zahari.utils.fixer.core.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by zdichev on 13/11/2015.
 */
public class UtcDateOnlyConverter extends AbstractDateTimeConverter {
    private static ThreadLocal<UtcDateOnlyConverter> utcDateConverter = new ThreadLocal();
    private DateFormat dateFormat = this.createDateFormat("yyyyMMdd");

    public UtcDateOnlyConverter() {
    }

    public static String convert(Date d) {
        return getFormatter().format(d);
    }

    private static DateFormat getFormatter() {
        UtcDateOnlyConverter converter = (UtcDateOnlyConverter)utcDateConverter.get();
        if(converter == null) {
            converter = new UtcDateOnlyConverter();
            utcDateConverter.set(converter);
        }

        return converter.dateFormat;
    }

    public static Date convert(String value) throws RuntimeException {
        Date d = null;
        String type = "date";
        assertLength(value, 8, type);
        assertDigitSequence(value, 0, 8, type);

        try {
            d = getFormatter().parse(value);
        } catch (ParseException var4) {
            throwFieldConvertError(value, type);
        }

        return d;
    }
}
