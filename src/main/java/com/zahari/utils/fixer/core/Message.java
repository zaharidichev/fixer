package com.zahari.utils.fixer.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import java.util.Random;

/**
 * Created by zdichev on 11/11/2015.
 */
public class Message {

    private Random rnd = new Random();
    private String[] arr;
    private int extractorPtr = 0;
    private JSONObject obj = new JSONObject();
    private FixFIeldEntry pushedBack;
    public Message(String str) {

        arr = str.split("\u0001");

    }


    public void parseGroup(Dictionary dd, FixRepeatingGroup gg, FixFIeldEntry field, JSONArray groupsContainer) {


        JSONObject group = null;
        int firstField = gg.getGroupDelim();
        boolean inGroupParse = true;
        boolean firstFieldFound = false;


        while (inGroupParse) {
            field = this.extractField(dd);
            if (field == null) {
                break; // stop parsing
            }

            int tag = field.getDefinition().getNumValue();
            if (tag == firstField) {
                if (group != null) {
                    groupsContainer.add(group);
                }

                group = new JSONObject();
                group.put(field.getDefinition().getName(), field.getValueAsString());
                firstFieldFound = true;

                if (dd.getRepeatingGroup(field.getDefinition()) != null) {

                   //this.parseGroup(dd, dd.getRepeatingGroup(field.getDefinition()), field, group);
                    //return;

                }
            } else if (dd.getRepeatingGroup(field.getDefinition()) != null) {

                JSONArray newCOntainer = new JSONArray();

                group.put(field.getDefinition().getName(), newCOntainer);
                this.parseGroup(dd, dd.getRepeatingGroup(field.getDefinition()), field, newCOntainer);

                //this.parseGroup(dd, dd.getRepeatingGroup(field.getDefinition()), field, group);
                //return;
            } else if (gg.getFieldsInGroup().contains(field.getDefinition())) {

                if (group != null) {
                    group.put(field.getDefinition().getName(), field.getValueAsString());
                }

            } else {


                pushedBack = field;
                inGroupParse = false;

            }


        }


        if (group != null) {

            groupsContainer.add(group);
        }

    }


    public void parseMessage(Dictionary dd) {

        FixFIeldEntry field = this.extractField(dd);

        while (field != null) {


            this.obj.put(field.getDefinition().getName(), field.getValueAsString());

            FixRepeatingGroup gg = dd.getRepeatingGroup(field.getDefinition());
            if (gg != null) {

                JSONArray list = new JSONArray();

                this.obj.put(field.getDefinition().getName(), list);
                this.parseGroup(dd, gg, field, list);

            }

            field = this.extractField(dd);
        }

        System.out.println(obj.toJSONString());

    }

    private FixFIeldEntry  extractField(Dictionary dd) {

        if(this.pushedBack != null) {

            FixFIeldEntry result = pushedBack;
            this.pushedBack = null;
            return  result;
        }

        if(extractorPtr >= arr.length){
            return null;
        }
        String token = arr[extractorPtr];
        extractorPtr++;
        String tagString = token.split("=")[0];
        String valueString = token.split("=")[1];
        int tagNum = Integer.parseInt(tagString);
        IFixField f = dd.getField(tagNum);

        String val = f.getDescriptionEnum(valueString);
        if(val != null) {
            valueString = val;
        }

        return new FixFIeldEntry(f,valueString);
    }

}
