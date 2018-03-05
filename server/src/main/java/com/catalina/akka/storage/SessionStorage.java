package com.catalina.akka.storage;

import java.util.concurrent.TimeUnit;

import com.catalina.akka.models.msg;
import com.catalina.akka.sessions.StoreSessionActor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class SessionStorage extends AbstractActor  {
    private int count = 0;
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
    
//    private static ActorSystem actorSystem = ActorSystem.create("store-session-actor-system", ConfigFactory.load().getConfig("akka.configuration"));
    
    
    public SessionStorage() {
    }
    
    public void handleMessage(msg m) {
    	count++;
    	if(count == 500) {
    		count++;
        	throw new NullPointerException();
        }
    	ActorRef actor = sessions.getIfPresent(createKey(m));
    	if(actor == null) {
    		try {
    		    actor = getContext().actorOf(StoreSessionActor.props(),"store-session-"+createKey(m));
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

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(msg.class, this::handleMessage).build();
	} 
	
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return new OneForOneStrategy(10, Duration.create(1, "minute"),
				new Function<Throwable, Directive> () {

					@Override
					public Directive apply(Throwable arg0) throws Exception {
						return SupervisorStrategy.resume();
					}
			
		});
	}
	
	public static Props props() {
		return Props.create(SessionStorage.class).withDispatcher("my-pinned-dispatcher");
	}
	
	public void message(msg m) {
		getSelf().tell(m, ActorRef.noSender());
	}
}
