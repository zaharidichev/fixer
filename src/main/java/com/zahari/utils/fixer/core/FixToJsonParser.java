/*
package com.zahari.utils.fixer.core;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

*/
/**
 * Created by zdichev on 03/11/2015.
 *//*

public class FixToJsonParser {




    public static boolean IsBeginingOfAGroup(String s) {

        if(s.equals("3") || s.equals("4.1")) {
           return  true;
        }
        return false;
    }




    public static boolean isInGroup(String s) {

        if(s.equals("4") || s.equals("5") ||  s.equals("4.2") ||  s.equals("4.3")) {
            return  true;
        }
        return false;
    }

    public static boolean isEndOfGroup(String s) {

        if(s.equals("6") ||  s.equals("4.4")) {
            return  true;
        }
        return false;
    }


    public static JSONObject  loop(JSONObject current, String[] arr, Integer index) {


        for()

    }




        public static JSONObject  loop(JSONObject current, String[] arr, Integer index) {

        if (index == arr.length) {
            return current;
        }

        if (IsBeginingOfAGroup(arr[index])) {
            JSONObject newGroup = new JSONObject();
            newGroup.put("Key" + arr[index],arr[index]);
            index = index + 1;
            current.put("Group" + index, loop(group, arr, index));
            return  current;
        }

        else  if (isEndOfGroup(arr[index])) {
            current.put("Key" + arr[index], arr[index]);
            index = index + 1;
            return current;
        }

        else {
            current.put("Key" + arr[index], arr[index]);
            index = index + 1;
            loop(current, arr, index);
            return current;
        }


    }


    public static void parseFixIntoJson(JSONObject parent, JSONObject child, String[] values, int currentIndex, Dictionary dict) {






    }



    private static boolean isIngroup(String s) {
        return false;
    }




    public void buildJson(JSONObject root, int arrayIndex,  String[] stringArray,Dictionary dict) {

    }






    public static void main(String[] args) {
        String[] arr = {"1","2","3","4","4.1","4.2","4.3","4.4","5","6","7"};
        JSONObject obj = new JSONObject();
        loop(obj,arr,0);
        System.out.println(obj);

    }

    public static JSONObject parseIntoJson(Dictionary dict) {

        String fixMessage = "8=FIXT.1.1\u00019=835\u000135=AI\u000134=426\u000149=MA\u000152=20151103-10:26:22.525\u000156=MILLUKTRD\u00011=FIBI_ECME\u000115=EUR\u000122=4\u000138=1000000\u000148=XS1206540806\u000154=2\u000155=[N/A]\u000158=MILL (jfeerick01, Joe Feerick) passes \u000160=20151103-10:26:22\u000164=20151105\u0001106=VW INTNL FINANCE\u0001107=VW 2.500 3/20/2022-49 c (URegS)\u0001167=EUCORP\u0001223=0.02500000\u0001225=20150320\u0001297=11\u0001423=1\u0001460=3\u0001541=20490320\u0001581=1\u0001660=99\u0001873=20150320\u00011227=BOND\u00015215=Y\u00015487=6\u00015625=1\u00015627=Passed\u00015630=None\u00015961=EU Price\u00016360=Autos\u000120117=15634437\u000120120=C2D-RFQ-Open\u000121031=N\u000121032=N\u000129703=EUEU\u0001453=6\u0001448=skordova\u0001447=D\u0001452=11\u0001802=2\u0001523=kordova.s@fibi.co.il\u0001803=8\u0001523=Shmulik Kordova\u0001803=9\u0001448=The First International Bank of Israel\u0001447=D\u0001452=13\u0001448=jfeerick01\u0001447=D\u0001452=37\u0001802=1\u0001523=Joe Feerick\u0001803=9\u0001448=amcaleavey\u0001447=D\u0001452=29\u0001802=1\u0001523=Andrew McAleavey (Sales)\u0001803=9\u0001448=MIERGB21\u0001447=B\u0001452=17\u0001448=Millennium Europe Limited\u0001447=D\u0001452=17\u000110=057\u0001";
        String[] stringArray = fixMessage.split("\u0001");

        JSONObject obj = new JSONObject();

        for (int i = 0; i < stringArray.length; i++) {

            String token = stringArray[i];
            String tagString = token.split("=")[0];
            String valueString = token.split("=")[1];
            int tagNum = Integer.parseInt(tagString);
            IFixField f = dict.getField(tagNum);
            obj.put(f.getName(),valueString);

        }

    return obj;

    }

}
*/
