package com.zahari.utils.fixer.core.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zdichev on 13/11/2015.
 */
public class LocalMarketDateConverter {

    public static Date convert(String value) throws RuntimeException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return df.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    }
