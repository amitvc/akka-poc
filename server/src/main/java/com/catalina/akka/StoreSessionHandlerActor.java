package com.catalina.akka;

import akka.actor.AbstractActor;
import akka.dispatch.UnboundedMessageQueueSemantics;


public class StoreSessionHandlerActor extends AbstractActor implements UnboundedMessageQueueSemantics {

	@Override
	public Receive createReceive() {
		return receiveBuilder().matchEquals("Targeting", p -> {
			System.out.println("Actor "+ this +" will make a targeting call");
			
		}).matchEquals("Wait", p -> {
			System.out.println("Actor "+ this + " will wait for next message");
		}).build();
	}
}
