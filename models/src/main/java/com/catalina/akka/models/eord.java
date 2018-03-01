package com.catalina.akka.models;

public class eord extends msg {

    private int _type;

	public eord(hdr h) {
        super(h);
        this._type = 5;
    }

}
