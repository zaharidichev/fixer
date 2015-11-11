package com.zahari.utils.fixer.core;

/**
 * Created by zdichev on 11/11/2015.
 */
public class FixFIeldEntry {

    private final IFixField definition;

    public IFixField getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return this.definition.getName() +  " ------> " + this.getValueAsString();
    }

    public FixFIeldEntry(IFixField definition, String valueAsString) {

        this.definition = definition;
        this.valueAsString = valueAsString;
    }

    public String getValueAsString() {
        return valueAsString;
    }

    private final String valueAsString;

}
