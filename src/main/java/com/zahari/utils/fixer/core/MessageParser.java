package com.zahari.utils.fixer.core;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Map;
import java.util.Set;

/**
 * Created by zdichev on 03/11/2015.
 */
public class MessageParser {

    public static String parseToString(String message,Dictionary dd) {

        StringBuilder sb = new StringBuilder();
        message.split("\u0001");

        for(String s :  message.split("\u0001")) {

            Integer fieldNum = Integer.parseInt(s.split("=")[0]);
            String value = s.split("=")[1];
            String fieldName = dd.getFieldName(fieldNum);
            sb.append(fieldNum + "     " +  fieldName + "     " + value);
            sb.append("\n");
        }

        return sb.toString();
    }









}
