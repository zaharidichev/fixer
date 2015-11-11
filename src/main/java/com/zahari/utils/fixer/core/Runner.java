package com.zahari.utils.fixer.core;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zdichev on 03/11/2015.
 */
public class Runner {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {


/*        File fXmlFile = new File("C:\\Users\\zdichev\\personal\\fixer\\src\\main\\resources\\MADTFIX50SP2.xml");
        Dictionary d = new Dictionary();
        d.parse("C:\\Users\\zdichev\\personal\\fixer\\src\\main\\resources\\MADTFIX50SP2.xml");
        for(Integer fl: d.getFieldNames()) {
            System.out.println(fl + " " + d.getFieldName(fl) + " " + d.getFieldType(fl));
        }*/


        String fixMessage = "8=FIXT.1.1\u00019=835\u000135=AI\u000134=426\u000149=MA\u000152=20151103-10:26:22.525\u000156=MILLUKTRD\u00011=FIBI_ECME\u000115=EUR\u000122=4\u000138=1000000\u000148=XS1206540806\u000154=2\u000155=[N/A]\u000158=MILL (jfeerick01, Joe Feerick) passes \u000160=20151103-10:26:22\u000164=20151105\u0001106=VW INTNL FINANCE\u0001107=VW 2.500 3/20/2022-49 c (URegS)\u0001167=EUCORP\u0001223=0.02500000\u0001225=20150320\u0001297=11\u0001423=1\u0001460=3\u0001541=20490320\u0001581=1\u0001660=99\u0001873=20150320\u00011227=BOND\u00015215=Y\u00015487=6\u00015625=1\u00015627=Passed\u00015630=None\u00015961=EU Price\u00016360=Autos\u000120117=15634437\u000120120=C2D-RFQ-Open\u000121031=N\u000121032=N\u000129703=EUEU\u0001453=6\u0001448=skordova\u0001447=D\u0001452=11\u0001802=2\u0001523=kordova.s@fibi.co.il\u0001803=8\u0001523=Shmulik Kordova\u0001803=9\u0001448=The First International Bank of Israel\u0001447=D\u0001452=13\u0001448=jfeerick01\u0001447=D\u0001452=37\u0001802=1\u0001523=Joe Feerick\u0001803=9\u0001448=amcaleavey\u0001447=D\u0001452=29\u0001802=1\u0001523=Andrew McAleavey (Sales)\u0001803=9\u0001448=MIERGB21\u0001447=B\u0001452=17\u0001448=Millennium Europe Limited\u0001447=D\u0001452=17\u000110=057\u0001";
        InputStream file = new FileInputStream(new File("C:\\Users\\zdichev\\repos\\fixer\\src\\main\\resources\\MADTFIX50SP2.xml"));

        Dictionary d = new Dictionary(file);
        d.build();
        Message m = new Message(fixMessage);
        m.parseMessage( d);

        //
        // System.out.println(d);

/*    JSONObject obj = new JSONObject();
    obj.put("name", "mkyong.com");
    obj.put("age", new Integer(100));

    JSONArray list = new JSONArray();
    list.add("msg 1");
    list.add("msg 2");
    list.add("msg 3");

        JSONObject obj2 = new JSONObject();


        obj.put("messages", list);

        obj2.put("name", "efdwefwefwe.com");
        obj2.put("age", new Integer(200));
        obj.put("secondObj",obj2);
    System.out.print(obj.toJSONString());*/

}


}
