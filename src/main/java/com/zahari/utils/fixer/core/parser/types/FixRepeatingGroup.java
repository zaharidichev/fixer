package com.zahari.utils.fixer.core.parser.types;

import java.util.Set;

/**
 * Created by zdichev on 03/11/2015.
 */
public class FixRepeatingGroup  {



    private final int groupDelim;
    private  final IFixField mainGroupField;
    private final Set<IFixField> fieldsInGroup;

    public FixRepeatingGroup(int groupDelim, IFixField mainGroupField, Set<IFixField> fieldsInGroup) {
        this.groupDelim = groupDelim;
        this.mainGroupField = mainGroupField;
        this.fieldsInGroup = fieldsInGroup;
    }

    public int getGroupDelim() {
        return groupDelim;
    }

    public IFixField getMainGroupField() {
        return mainGroupField;
    }

    public Set<IFixField> getFieldsInGroup() {
        return fieldsInGroup;
    }
    public String getName() {
        return this.mainGroupField.getName();
    }
}
