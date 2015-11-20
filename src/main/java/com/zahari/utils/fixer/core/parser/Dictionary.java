package com.zahari.utils.fixer.core.parser;



import com.zahari.utils.fixer.core.parser.types.FieldType;
import com.zahari.utils.fixer.core.parser.types.FixField;
import com.zahari.utils.fixer.core.parser.types.FixRepeatingGroup;
import com.zahari.utils.fixer.core.parser.types.IFixField;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;

/**
 * Created by zdichev on 03/11/2015.
 */
public class Dictionary {



    public Map<IFixField,FixRepeatingGroup> mainFieldToGroup = new HashMap<IFixField, FixRepeatingGroup>();
    public Map<IFixField,FixRepeatingGroup> fieldsToGroups = new HashMap<IFixField, FixRepeatingGroup>();
    public Map<Integer,IFixField> numValueToFixField = new HashMap<Integer, IFixField>();
    public Map<String,IFixField> nameToFixField = new HashMap<String, IFixField>();

    public Set<IFixField> fieldsInGroups = new HashSet<IFixField>();
    private InputStream in;


    public FixRepeatingGroup getGroupForField(IFixField f) {

        return this.fieldsToGroups.get(f);

    }
    public FixRepeatingGroup getRepeatingGroup (IFixField filed) {
        return this.mainFieldToGroup.get(filed);
    }


    public IFixField getField (int field) {
        return this.numValueToFixField.get(field);
    }

    public IFixField getField (String fieldNamme) {
        return this.nameToFixField.get(fieldNamme);
    }


    public boolean isFieldInGroup(IFixField field) {
        return this.fieldsInGroups.contains(field);
    }




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
                        FixRepeatingGroup g = createGroup(componentFieldNode, this.nameToFixField);
                        this.mainFieldToGroup.put(g.getMainGroupField(), g);
                        this.fieldsInGroups.addAll(g.getFieldsInGroup());
                        for (IFixField fieldInGroup:g.getFieldsInGroup()) {
                            this.fieldsToGroups.put(fieldInGroup,g);
                        }
                    }

                }

            }

        }

    }


    public static FixRepeatingGroup createGroup(Node groupNode, Map<String, IFixField> namesToFields) {

        String groupName = Dictionary.getAttribute(groupNode, "name");
        IFixField groupField = namesToFields.get(groupName);
        Set<IFixField> fieldsInGroup = new HashSet<IFixField>();

        NodeList fieldNodeList = groupNode.getChildNodes();

        int delim = -1;
        for (int i = 0; i < fieldNodeList.getLength(); ++i) {
            Node fieldNode = fieldNodeList.item(i);
            if (fieldNode.getNodeName().equals("field")) {
                String nameOfField = Dictionary.getAttribute(fieldNode, "name");
                if (namesToFields.containsKey(nameOfField)) {
                    IFixField fieldInGroup = namesToFields.get(nameOfField);
                            if(delim == -1) {
                                delim = fieldInGroup.getNumValue();
                            }
                    fieldsInGroup.add(fieldInGroup);
                }
            }
        }

        return new FixRepeatingGroup(delim, groupField,fieldsInGroup);
    }

    private final Map<String, Node> components = new HashMap();


    public void loadComponents(Element root) {


        NodeList components =  root.getElementsByTagName("components");
        if (components.getLength() != 0) {

            NodeList childNodes = components.item(0).getChildNodes();

            if (childNodes.getLength() != 0) {

                for (int var23 = 0; var23 < childNodes.getLength(); ++var23) {
                    Node childNode = childNodes.item(var23);
                    if (childNode.getNodeName().equals("component")) {
                        String name = this.getAttribute(childNode, "name");
                        if(name != null) {
                            this.components.put(name, childNode);
                        }
                    }

                    }


            }

        }


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

                        FixField field = new FixField(fieldNumber, name, FieldType.fromName(type));

                        this.numValueToFixField.put(fieldNumber, field);
                        this.nameToFixField.put(name, field);

                        if(var25.getChildNodes().getLength() > 0) {
                            for(int i = 0; i < var25.getChildNodes().getLength(); i++ ) {
                                Node descriptionNode = var25.getChildNodes().item(i);

                                if(descriptionNode.getNodeName().equals("value")){
                                    String enumIdx = getAttribute(descriptionNode,"enum");
                                    String value =  getAttribute(descriptionNode,"description");
                                    field.putDescriptionEnum(enumIdx,value);
                                }

                            }
                        }


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
            this.loadComponents(var21);
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
