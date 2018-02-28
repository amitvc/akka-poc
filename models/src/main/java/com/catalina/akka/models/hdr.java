package com.catalina.akka.models;

public class hdr {
    public int store;
    public int chain;
    public String seq;
    public int lane;
    public long ts;
    
    public hdr(int str, int ch, String seq, int ln, long ts) {
        this.store = str;
        this.chain = ch;
        this.seq = seq;
        this.lane = ln;
        this.ts = ts;
    }
}