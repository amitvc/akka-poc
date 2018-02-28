package com.catalina.akka.models;

public class upc extends msg {
    public upc(hdr h, String code, int qty, int amt) {
        super(h);
        this.upc_code = code;
        this.qty = qty;
        this.amt = amt;
    }
    public String upc_code;
    public int qty;
    public int amt;
}
