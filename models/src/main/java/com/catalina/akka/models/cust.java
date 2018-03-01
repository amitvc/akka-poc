package com.catalina.akka.models;

public class cust extends msg {
    private int _type;

	public cust(hdr h) {
        super(h);
        this._type = 3;
    }

    public String cid;

}
