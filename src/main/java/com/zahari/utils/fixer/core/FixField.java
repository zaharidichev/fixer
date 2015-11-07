package com.zahari.utils.fixer.core;

/**
 * Created by zdichev on 05/11/2015.
 */
public class FixField  implements  IFixField {

    private final int numValue;
    private final String name;
    private final FieldType type;


    private final boolean isGroup;


    public FixField(int numValue, String name, FieldType type,boolean isGroup){

        this.numValue = numValue;
        this.name = name;
        this.type = type;
        this.isGroup = isGroup;
    }

    public int getNumValue() {
        return numValue;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public boolean isGroup() {
        return isGroup;
    }



}
