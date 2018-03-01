package com.catalina.akka.models;

public class tot extends msg {
    private int _type;

	public tot(hdr h) {
        super(h);
        this._type = 4;
    }

    public int total;
}
