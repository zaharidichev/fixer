package com.zahari.utils.fixer.core.parser.types;


import com.zahari.utils.fixer.core.parser.Dictionary;

/**
 * Created by zdichev on 11/11/2015.
 */
public class RawFixMessage {


    public String getSource() {
        return source;
    }

    public String getMess() {
        return mess;
    }

    public Dictionary getDd() {
        return dd;
    }

    private final String source;
    private final String mess;
    private final Dictionary dd;

    public RawFixMessage(String message, Dictionary dd, String source) {
        this.mess = message;
        this.dd = dd;
        this.source = source;
    }















}
