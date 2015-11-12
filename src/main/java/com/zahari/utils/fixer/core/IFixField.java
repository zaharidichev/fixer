package com.zahari.utils.fixer.core;

/**
 * Created by zdichev on 05/11/2015.
 */
public interface IFixField {

    public int getNumValue();

    public String getName();

    public FieldType getType();

    public boolean isGroup();

    public void putDescriptionEnum(String enumIdx, String value);


    public String getDescriptionEnum(String enumIdx);

}
