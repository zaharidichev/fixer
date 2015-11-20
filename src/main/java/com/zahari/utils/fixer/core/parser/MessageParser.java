package com.zahari.utils.fixer.core.parser;




import com.zahari.utils.fixer.core.converters.LocalMarketDateConverter;
import com.zahari.utils.fixer.core.converters.UtcDateOnlyConverter;
import com.zahari.utils.fixer.core.converters.UtcTimeOnlyConverter;
import com.zahari.utils.fixer.core.converters.UtcTimestampConverter;
import com.zahari.utils.fixer.core.parser.types.FieldType;
import com.zahari.utils.fixer.core.parser.types.FixFIeldEntry;
import com.zahari.utils.fixer.core.parser.types.FixRepeatingGroup;
import com.zahari.utils.fixer.core.parser.types.RawFixMessage;
import com.zahari.utils.fixer.core.utils.ParserConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Created by zdichev on 03/11/2015.
 */
public class MessageParser {


    public static JSONObject parseMessageIntoJson(RawFixMessage mess) {

        FieldExtractor extractor = new FieldExtractor(mess, ParserConstants.DEFAULT_DELIMETER);
        return parseMessage(mess.getDd(),extractor,mess.getSource());

    }



    private static void insertFieldIntoJsonObject(JSONObject root, FixFIeldEntry entr) {
        String name = entr.getDefinition().getName() == null ? String.valueOf (entr.getDefinition().getNumValue()) : entr.getDefinition().getName();
        insertFieldIntoJsonObject( root,  entr, name);
    }


        private static void insertFieldIntoJsonObject(JSONObject root, FixFIeldEntry entr,String key) {

        FieldType t = entr.getDefinition().getType();

        if (t != null) {

            Class<?> c = t.getJavaType();


            boolean hasValueDescription = entr.getDefinition().getDescriptionEnum(entr.getValueAsString()) != null;

            if (hasValueDescription) {
                root.put(key, entr.getDefinition().getDescriptionEnum(entr.getValueAsString()));
                return;

            } else {
                if (c == Double.class) {
                    root.put(key, Double.parseDouble(entr.getValueAsString()));
                    return;

                } else if (c == Integer.class) {
                    root.put(key, Integer.parseInt(entr.getValueAsString()));
                    return;
                } else if (c == Date.class) {

                    Date d = null;
                    if (t.getName().equals("UTCTIMESTAMP")) {
                        d = UtcTimestampConverter.convert((entr.getValueAsString()));
                    } else if (t.getName().equals("UTCDATEONLY") || t.getName().equals("UTCDATE")) {
                        d = UtcDateOnlyConverter.convert((entr.getValueAsString()));
                    } else if (t.getName().equals("UTCTIMEONLY")) {
                        d = UtcTimeOnlyConverter.convert((entr.getValueAsString()));
                    } else if (t.getName().equals("LOCALMKTDATE")) {
                        d = LocalMarketDateConverter.convert((entr.getValueAsString()));
                    }


                    if (d != null) {
                        JSONObject date = new JSONObject();
                        date.put("$date", d.getTime());
                        root.put(key, date);
                        return;
                    }


                    System.out.println("Not putting shit");


                } else {
                    root.put(key, entr.getValueAsString());
                }
            }


        }


    }


    private static JSONObject parseMessage(Dictionary dd, FieldExtractor extractor,String source) {
        JSONObject messageObj = new JSONObject();
        FixFIeldEntry field = extractor.extractField(dd);

        FixFIeldEntry timeStamp = null;


        while (field != null) {

            if (field.getDefinition().getName() != null && field.getDefinition().getName().equalsIgnoreCase("SendingTime")) {

                timeStamp = field;

            }

            //this.messageObj.put(field.getDefinition().getName(), field.getValueAsString());
            insertFieldIntoJsonObject(messageObj, field);
            FixRepeatingGroup gg = dd.getRepeatingGroup(field.getDefinition());
            if (gg != null) {

                JSONArray list = new JSONArray();
                messageObj.put(field.getDefinition().getName(), list);
                parseGroup(dd, gg, field, list, extractor);

            }

            field = extractor.extractField(dd);

        }

        if(timeStamp == null) {
            throw new RuntimeException("no SendingTime field in Fix message");
        }

        JSONObject mainRoot = new JSONObject();

        insertFieldIntoJsonObject(mainRoot,timeStamp,"timestamp");
        mainRoot.put("source", source);
        mainRoot.put("message",messageObj);

        return mainRoot;

    }

    private static  void parseGroup(Dictionary dd, FixRepeatingGroup gg, FixFIeldEntry field, JSONArray groupsContainer, FieldExtractor extractor) {


        JSONObject group = null;
        int firstField = gg.getGroupDelim();
        boolean inGroupParse = true;
        boolean firstFieldFound = false;


        while (inGroupParse) {
            field = extractor.extractField(dd);
            if (field == null) {
                break; // stop parsing
            }

            int tag = field.getDefinition().getNumValue();
            if (tag == firstField) {
                if (group != null) {
                    groupsContainer.add(group);
                }

                group = new JSONObject();
                insertFieldIntoJsonObject(group, field);

                //group.put(field.getDefinition().getName(), field.getValueAsString());
                firstFieldFound = true;

                if (dd.getRepeatingGroup(field.getDefinition()) != null) {

                    //this.parseGroup(dd, dd.getRepeatingGroup(field.getDefinition()), field, group);
                    //return;

                }
            } else if (gg.getFieldsInGroup().contains(field.getDefinition()) && dd.getRepeatingGroup(field.getDefinition()) != null) {

                JSONArray newCOntainer = new JSONArray();

                group.put(field.getDefinition().getName(), newCOntainer);
                parseGroup(dd, dd.getRepeatingGroup(field.getDefinition()), field, newCOntainer, extractor);

                //this.parseGroup(dd, dd.getRepeatingGroup(field.getDefinition()), field, group);
                //return;
            } else if (gg.getFieldsInGroup().contains(field.getDefinition())) {

                if (group != null) {
                    insertFieldIntoJsonObject(group, field);


                    //group.put(field.getDefinition().getName(), field.getValueAsString());
                }

            } else {


                extractor.pushBack(field);
                inGroupParse = false;

            }


        }


        if (group != null) {

            groupsContainer.add(group);
        }

    }


}
