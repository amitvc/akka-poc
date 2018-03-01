package com.catalina.akka;

import com.catalina.akka.models.msg;
import com.catalina.akka.sessions.StoreSession;

import akka.actor.AbstractActor;


public class StoreSessionTargetingActor extends AbstractActor  {

    @Override
    public void preStart() throws Exception {
    }
    
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(StoreSession.class, p -> {		    
		    StoreSession session = (StoreSession)p;
		    msg m = (msg) session.pos.peek();
		    System.out.println("Actor --"+ m._hdr.seq + " executing targeting call " + session.targetingCalls);
		}).build();
	}
}
