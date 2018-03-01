package com.catalina.akka.models;

public class sord extends msg {

    private int _type;

	public sord(hdr h) {
        super(h);
        this._type = 1;
    }

}
