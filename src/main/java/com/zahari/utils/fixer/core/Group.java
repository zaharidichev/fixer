package com.zahari.utils.fixer.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zdichev on 03/11/2015.
 */
public class Group {



    private final int groupField;
    private final int groupDelim;
    private final Set<Integer> fieldsInGroup;


    public Group(int groupField, int groupDelim, Set<Integer> fieldsInGroup) {
        this.groupField = groupField;
        this.groupDelim = groupDelim;
        this.fieldsInGroup = fieldsInGroup;
    }


    public int getGroupField() {
        return groupField;
    }

    public int getGroupDelim() {
        return groupDelim;
    }

    public Set<Integer> getFieldsInGroup() {
        return fieldsInGroup;
    }






}
