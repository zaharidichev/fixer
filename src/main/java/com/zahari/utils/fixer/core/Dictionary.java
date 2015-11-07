package com.zahari.utils.fixer.core;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by zdichev on 03/11/2015.
 */
public class Dictionary {



    public Map<Integer,Group> fieldsToGroups = new HashMap<Integer, Group>();
    public Map<Integer,FixField> numValueToFixField = new HashMap<Integer, FixField>();
    public Map<String,FixField> nameToFixField = new HashMap<String, FixField>();

    public Set<Integer> fieldsInGroups = new HashSet<Integer>();
    private InputStream in;




    private boolean built;
    public static int getFixFieldNumber(Node fieldNode) {
       return  Integer.valueOf(getAttribute(fieldNode, "number"));
    }
    public static String getFixFieldType(Node fieldNode) {
        return  getAttribute(fieldNode, "type");
    }

    public static String getFixFieldName(Node fieldNode) {
        return  getAttribute(fieldNode, "name");
    }

    public Dictionary(InputStream in) {
        this.in = in;

    }

    public void build() {
        if(!built && this.in != null)
        this.loadMetaData(in);

    }

    public void loadGroups(Element root) {

        NodeList components = root.getElementsByTagName("components");
        NodeList childNodes = components.item(0).getChildNodes();
        for (int var27 = 0; var27 < childNodes.getLength(); ++var27) {
            Node comp = childNodes.item(var27);

            if (comp.getNodeName().equals("component")) {
                NodeList componentFieldNodes = comp.getChildNodes();

                for (int i = 0; i < componentFieldNodes.getLength(); ++i) {
                    Node componentFieldNode = componentFieldNodes.item(i);

                    if (componentFieldNode.getNodeName().equals("group")) {
                        // in case it is a group
                        Group g = createGroup(componentFieldNode, this.nameToFixField);
                        this.fieldsToGroups.put(g.getGroupField(), g);
                        this.fieldsInGroups.addAll(g.getFieldsInGroup());
                    }

                }

            }

        }

    }


    public static Group createGroup(Node groupNode, Map<String, FixField> namesToFields) {

        String groupName = Dictionary.getAttribute(groupNode, "name");
        int groupField = namesToFields.get(groupName);
        Set<Integer> fieldsInGroup = new HashSet<Integer>();

        NodeList fieldNodeList = groupNode.getChildNodes();

        int delim = -1;
        for (int i = 0; i < fieldNodeList.getLength(); ++i) {
            Node fieldNode = fieldNodeList.item(i);
            if (fieldNode.getNodeName().equals("field")) {
                String nameOfField = Dictionary.getAttribute(fieldNode, "name");
                if (namesToFields.containsKey(nameOfField)) {
                    int fieldNum = namesToFields.get(nameOfField);
                            if(delim == -1) {
                                delim = fieldNum;
                            }
                    fieldsInGroup.add(namesToFields.get(nameOfField));
                }
            }
        }

        return new Group(groupField, delim, fieldsInGroup);
    }


    public void loadFields(Element root) {

        NodeList fieldsNode;
        fieldsNode = root.getElementsByTagName("fields");

        if (fieldsNode.getLength() != 0) {
            NodeList var22 = fieldsNode.item(0).getChildNodes();

            if (var22.getLength() != 0) {
                for (int var23 = 0; var23 < var22.getLength(); ++var23) {
                    Node var25 = var22.item(var23);
                    if (var25.getNodeName().equals("field")) {

                        String name = getFixFieldName(var25);
                        int fieldNumber = getFixFieldNumber(var25);
                        String type = getFixFieldType(var25);
                        this.numValueToFixField.put(fieldNumber, new FixField(fieldNumber, name, FieldType.fromName(type)));
                        this.nameToFixField.put(name, new FixField(fieldNumber, name, FieldType.fromName(type)));

                    }

                }

            }
        }
    }

    public void loadMetaData(InputStream inputStream) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        Document document;
        try {
            DocumentBuilder documentElement = factory.newDocumentBuilder();
            document = documentElement.parse(inputStream);
        } catch (Throwable var20) {
            throw new IllegalArgumentException("Could not parse data dictionary file", var20);
        }

        Element var21 = document.getDocumentElement();

        if (isValidXML(var21)) {
            this.loadFields(var21);
            this.loadGroups(var21);
        }

    }



    public static boolean isValidXML(Element elem) {

        if(!elem.getNodeName().equals("fix") ||
                !elem.hasAttribute("major")  ||
                !elem.hasAttribute("minor")) {
            return false;
        }
        return true;

    }


    public static String getAttribute(Node node, String name) {
        return getAttribute(node, name, (String)null);
    }

    private static String getAttribute(Node node, String name, String defaultValue) {
        NamedNodeMap attributes = node.getAttributes();
        if(attributes != null) {
            Node namedItem = attributes.getNamedItem(name);
            return namedItem != null?namedItem.getNodeValue():null;
        } else {
            return defaultValue;
        }
    }

}
