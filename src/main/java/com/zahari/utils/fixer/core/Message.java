package com.zahari.utils.fixer.core;

import org.json.simple.JSONObject;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import java.util.Random;

/**
 * Created by zdichev on 11/11/2015.
 */
public class Message {


    private String[] arr;
    private int extractorPtr = 0;
    private JSONObject obj = new JSONObject();
    private FixFIeldEntry pushedBack;
    public Message(String str) {

        arr = str.split("\u0001");

    }




    public void parseGroup(Dictionary dd,FixRepeatingGroup gg,FixFIeldEntry ffe,JSONObject parent) {


        JSONObject group = null;
        int firstField = gg.getGroupDelim();
        boolean inGroupParse = true;
        boolean firstFieldFound = false;


        while (inGroupParse) {
            ffe = this.extractField(dd);
            if (ffe.getDefinition().getNumValue() == firstField) {

                if (group != null) {
                    parent.put(gg.getName(), group);
                }

                group = new JSONObject();
                group.put(ffe.getDefinition().getName() + System.currentTimeMillis(), ffe.getValueAsString());
                firstFieldFound = true;
            }


            else if (dd.getRepeatingGroup(ffe.getDefinition()) != null) {
                this.parseGroup(dd, dd.getRepeatingGroup(ffe.getDefinition()), ffe,group);
            }




            else if (gg.getFieldsInGroup().contains(ffe.getDefinition())) {

                if (group != null) {
                    group.put(ffe.getDefinition().getName() + System.currentTimeMillis(), ffe.getValueAsString());
                }

            }

            else {

                if (group != null) {

                    parent.put(ffe.getValueAsString()  + System.currentTimeMillis(), group);
                }

                pushedBack = ffe;
                inGroupParse = false;

            }


        }

    }


        public void parseMessage(Dictionary dd) {

        for (FixFIeldEntry field = this.extractField(dd); field != null; field = this.extractField(dd)) {


            this.obj.put(field.getDefinition().getName(), field.getValueAsString());

            FixRepeatingGroup gg = dd.getRepeatingGroup(field.getDefinition());
            if(gg != null) {
                this.obj.put(field.getDefinition().getName(),field.getValueAsString());
                this.parseGroup(dd,gg,field,this.obj);

            }

        }

        System.out.println(obj.toJSONString());

    }

    private FixFIeldEntry extractField(Dictionary dd) {

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
        return new FixFIeldEntry(f,valueString);
    }

}
