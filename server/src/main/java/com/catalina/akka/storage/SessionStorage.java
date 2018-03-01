package com.catalina.akka.storage;

import java.util.HashMap;
import java.util.Map;

import com.catalina.akka.StoreSessionHandlerActor;
import com.catalina.akka.models.cust;
import com.catalina.akka.models.msg;
import com.catalina.akka.models.tot;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SessionStorage {
    
    private Map<String, ActorRef> sessions = new HashMap<String, ActorRef>();
    
    private static ActorSystem actorSystem = ActorSystem.create("store-session-actor-system", ConfigFactory.load().getConfig("akka.configuration"));
    
    public SessionStorage() {
        if(actorSystem != null) {
            System.out.println(actorSystem.settings());
        }
    }
    
    public void handleMessage(msg m) {
        
    	ActorRef actor = sessions.get(createKey(m));
    	if(actor == null) {
    		try {
    		    actor = actorSystem.actorOf(Props.create(StoreSessionHandlerActor.class).withDispatcher("blocking-io-dispatcher"), "store-session-"+createKey(m));
                sessions.put(createKey(m), actor);    
    		}catch(Exception ex) {
    		    System.out.println(ex);
    		    ex.printStackTrace();
    		}
    	    
    	}
    	if(m instanceof cust || m instanceof tot ) {
    		actor.tell("Targeting", null);
    	} else {
    		actor.tell("Wait", null);
    	}
    }
    
    public static String createKey(msg m) {
    	return String.format("%d:%d:%d:%s", m._hdr.store, m._hdr.chain, m._hdr.lane, m._hdr.seq);
    } 
}
