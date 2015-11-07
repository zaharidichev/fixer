package com.zahari.utils.fixer.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zdichev on 03/11/2015.
 */
public class Group implements  IFixField {



    private final int groupDelim;
    private final Set<Integer> fieldsInGroup;
    private final int numValue;
    private final String name;


    public Group(int numValue, String name, FieldType type,boolean isGroup, int groupDelim, Set<Integer> fieldsInGroup) {

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
