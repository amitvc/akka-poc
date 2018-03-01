package com.catalina.akka;

import java.util.concurrent.ConcurrentLinkedQueue;

import akka.actor.AbstractActor;


public class StoreSessionHandlerActor extends AbstractActor  {

    @Override
    public void preStart() throws Exception {
        System.out.println("Starting actor " + getSelf().toString());
    }
    
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(ConcurrentLinkedQueue.class, p -> {		    
		    System.out.println("Actor--"+ getSelf().toString() + p + " executing targeting");
		}).build();
	}
}
