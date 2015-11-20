package com.zahari.utils.fixer.core.converters;


import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zdichev on 13/11/2015.
 */
abstract class AbstractDateTimeConverter {


    AbstractDateTimeConverter() {
    }

    protected static void assertLength(String value, int i, String type) throws RuntimeException {
        if(value.length() != i) {
            throwFieldConvertError(value, type);
        }

    }

    protected static void assertDigitSequence(String value, int i, int j, String type) throws RuntimeException {
        for(int offset = i; offset < j; ++offset) {
            if(!Character.isDigit(value.charAt(offset))) {
                throwFieldConvertError(value, type);
            }
        }

    }

    protected static void assertSeparator(String value, int offset, char ch, String type) throws RuntimeException {
        if(value.charAt(offset) != ch) {
            throwFieldConvertError(value, type);
        }

    }

    protected static void throwFieldConvertError(String value, String type) throws RuntimeException {
        throw new RuntimeException("invalid UTC " + type + " value: " + value);
    }

    protected static long parseLong(String s) {
        long n = 0L;

        for(int i = 0; i < s.length(); ++i) {
            n = n * 10L + (long)(s.charAt(i) - 48);
        }

        return n;
    }

    protected DateFormat createDateFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setDateFormatSymbols(new DateFormatSymbols(Locale.US));
        return sdf;
    }


}
