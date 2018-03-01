package com.catalina.akka;

import com.catalina.akka.models.msg;
import com.google.gson.Gson;

import akka.actor.AbstractActor;
import akka.dispatch.UnboundedMessageQueueSemantics;


public class StoreSessionHandlerActor extends AbstractActor implements UnboundedMessageQueueSemantics {

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(msg.class, p -> {
		    Gson gson = new Gson();
		    System.out.println("Actor--"+ this.getSelf().toString() + gson.toJson(p));
		}).build();
	}
}
