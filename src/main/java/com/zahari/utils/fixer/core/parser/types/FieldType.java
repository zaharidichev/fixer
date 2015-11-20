package com.zahari.utils.fixer.core.parser.types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by zdichev on 03/11/2015.
 */
public class FieldType {

    private int ordinal;
    private String name;
    private Class<?> javaType;
    private static HashMap<String, FieldType> values = new HashMap();
    private static ArrayList<FieldType> ordinalToValue = new ArrayList();
    public static final FieldType Unknown = new FieldType("UNKNOWN");
    public static final FieldType String = new FieldType("STRING");
    public static final FieldType Char = new FieldType("CHAR");
    public static final FieldType Price = new FieldType("PRICE", Double.class);
    public static final FieldType Int = new FieldType("INT", Integer.class);
    public static final FieldType Amt = new FieldType("AMT", Double.class);
    public static final FieldType Qty = new FieldType("QTY", Double.class);
    public static final FieldType Currency = new FieldType("CURRENCY");
    public static final FieldType MultipleValueString = new FieldType("MULTIPLEVALUESTRING");
    public static final FieldType Exchange = new FieldType("EXCHANGE");
    public static final FieldType UtcTimeStamp = new FieldType("UTCTIMESTAMP", Date.class);
    public static final FieldType Boolean = new FieldType("BOOLEAN", Boolean.class);
    public static final FieldType LocalMktDate = new FieldType("LOCALMKTDATE",Date.class);
    public static final FieldType Data = new FieldType("DATA");
    public static final FieldType Float = new FieldType("FLOAT", Double.class);
    public static final FieldType PriceOffset = new FieldType("PRICEOFFSET", Double.class);
    public static final FieldType MonthYear = new FieldType("MONTHYEAR");
    public static final FieldType DayOfMonth = new FieldType("DAYOFMONTH", Integer.class);
    public static final FieldType UtcDateOnly = new FieldType("UTCDATEONLY", Date.class);
    public static final FieldType UtcDate = new FieldType("UTCDATE", Date.class);
    public static final FieldType UtcTimeOnly = new FieldType("UTCTIMEONLY", Date.class);
    public static final FieldType Time = new FieldType("TIME");
    public static final FieldType NumInGroup = new FieldType("NUMINGROUP", Integer.class);
    public static final FieldType Percentage = new FieldType("PERCENTAGE", Double.class);
    public static final FieldType SeqNum = new FieldType("SEQNUM", Integer.class);
    public static final FieldType Length = new FieldType("LENGTH", Integer.class);
    public static final FieldType Country = new FieldType("COUNTRY");

    private FieldType(String name) {
        this(name, String.class);
    }

    private FieldType(String name, Class<?> javaType) {
        this.javaType = javaType;
        this.name = name;
        this.ordinal = ordinalToValue.size();
        ordinalToValue.add(this);
        values.put(name, this);
    }


    public String getName() {
        return this.name;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public Class<?> getJavaType() {
        return this.javaType;
    }

    public static FieldType fromOrdinal(int ordinal) {
        if(ordinal >= 0 && ordinal < ordinalToValue.size()) {
            return (FieldType)ordinalToValue.get(ordinal);
        } else {
            throw new RuntimeException("invalid field type ordinal: " + ordinal);
        }
    }

    public static FieldType fromName(String name) {
        FieldType type = values.get(name);
        return type != null?type:Unknown;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.getName() + "," + this.getJavaType() + "," + this.getOrdinal() + "]";
    }

}
