package com.catalina.akka.storage;

import java.util.concurrent.TimeUnit;

import com.catalina.akka.models.msg;
import com.catalina.akka.sessions.StoreSessionActor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SessionStorage {
    
    private Cache<String, ActorRef> sessions = this.sessions = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .initialCapacity(6000 * 10)    // 6,000 stores x 10 lanes each
            .concurrencyLevel(100) // high concurrency access
            .recordStats()
            .removalListener(new RemovalListener<String, ActorRef>() {

                @Override
                public void onRemoval(RemovalNotification<String, ActorRef> arg) {
                    System.out.println("Evicting " + arg.getKey() + " actor " + arg.getValue().isTerminated());
                }
            })
            .build();
    
    private static ActorSystem actorSystem = ActorSystem.create("store-session-actor-system", ConfigFactory.load().getConfig("akka.configuration"));
    
    
    public SessionStorage() {
        if(actorSystem != null) {
            System.out.println(actorSystem.settings());
        }
        
    }
    
    public void handleMessage(msg m) {
    	ActorRef actor = sessions.getIfPresent(createKey(m));
    	if(actor == null) {
    		try {
    		    actor = actorSystem.actorOf(Props.create(StoreSessionActor.class).withDispatcher("blocking-io-dispatcher"), "store-session-"+createKey(m));
                sessions.put(createKey(m), actor);
    		}catch(Exception ex) {
    		    System.out.println(ex);
    		    ex.printStackTrace();
    		}
    	}
    	actor.tell(m, null);
    }
    
    public static String createKey(msg m) {
    	return String.format("%d:%d:%d:%s", m._hdr.store, m._hdr.chain, m._hdr.lane, m._hdr.seq);
    } 
}
