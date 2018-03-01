package com.catalina.akka.sessions;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.catalina.akka.models.msg;

public class StoreSession {
    
    public ConcurrentLinkedQueue<msg> pos = new ConcurrentLinkedQueue<msg>();
    public int targetingCalls = 0;

}
