package com.catalina.akka.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.catalina.akka.models.hdr;
import com.catalina.akka.models.msg;
import com.catalina.akka.models.sord;
import com.catalina.akka.models.upc;

public class StoreSessionSimulator implements Runnable {

    public void run() {
        hdr _hdr = new hdr(199,321,UUID.randomUUID().toString(), 1, System.currentTimeMillis());
        
        List<msg> messages = new ArrayList<msg>();
        sord s = new sord(_hdr);
        upc upc1 = new upc(_hdr, "111111111", 2,300);
        upc upc2 = new upc(_hdr, "222222222", 4,300);
        upc upc3 = new upc(_hdr, "333333333", 5,800);
        
    }

}
