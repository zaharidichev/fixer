package com.zahari.utils.fixer.core.parser;



import com.zahari.utils.fixer.core.parser.types.FixFIeldEntry;
import com.zahari.utils.fixer.core.parser.types.IFixField;
import com.zahari.utils.fixer.core.parser.types.RawFixMessage;


/**
 * Created by zdichev on 16/11/2015.
 */
public class FieldExtractor {

    private String[] arr;
    private int extractorPtr = 0;
    private FixFIeldEntry pushedBack;


    public FieldExtractor(RawFixMessage message, String delimeter) {
        this.arr = message.getMess().split(delimeter);

    }

    public void pushBack(FixFIeldEntry entry) {
        this.pushedBack = entry;
    }

    public FixFIeldEntry pop() {
        return this.pushedBack;
    }

    public FixFIeldEntry  extractField(Dictionary dd)
    {

        if(pushedBack != null) {

            FixFIeldEntry result = pushedBack;
            pushedBack = null;
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
