package com.zahari.utils.fixer.core;

/**
 * Created by zdichev on 05/11/2015.
 */
public class FixField  implements  IFixField {

    private final int numValue;
    private final String name;
    private final FieldType type;




    public FixField(int numValue, String name, FieldType type){

        this.numValue = numValue;
        this.name = name;
        this.type = type;
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
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixField fixField = (FixField) o;

        if (numValue != fixField.numValue) return false;
        if (!name.equals(fixField.name)) return false;
        return type.equals(fixField.type);

    }

    @Override
    public int hashCode() {
        int result = numValue;
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FixField{" +
                "numValue=" + numValue +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
