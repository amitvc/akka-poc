package com.catalina.akka.storage;

import com.catalina.akka.models.cust;
import com.catalina.akka.models.tot;
import com.typesafe.config.Config;

import akka.actor.ActorSystem;
import akka.dispatch.PriorityGenerator;
import akka.dispatch.UnboundedStablePriorityMailbox;

public class PosMessagePriorityMailbox extends UnboundedStablePriorityMailbox {
	  // needed for reflective instantiation
	  public PosMessagePriorityMailbox(ActorSystem.Settings settings, Config config) {
	    // Create a new PriorityGenerator, lower prio means more important
	    super(new PriorityGenerator() {
	      @Override
	      public int gen(Object message) {
	        if (message instanceof cust || message instanceof tot)
	          return 0; // 'highpriority messages should be treated first if possible
	        else 
	        	return 2;
	      }
	    });
	  }
	}