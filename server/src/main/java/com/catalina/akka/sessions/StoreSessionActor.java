package com.catalina.akka.sessions;

import com.catalina.akka.StoreSessionTargetingActor;
import com.catalina.akka.models.cust;
import com.catalina.akka.models.eord;
import com.catalina.akka.models.msg;
import com.catalina.akka.models.tot;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class StoreSessionActor extends AbstractActor{

    private StoreSession posSession = new StoreSession();
    private ActorRef targetingActor;
    
    @Override
    public Receive createReceive() {
    	
        return receiveBuilder().match(msg.class, m -> {
            posSession.pos.offer(m);
            if(!getSelf().toString().contains(m._hdr.seq)) {
                System.out.println("Cant be possible");
                System.exit(-666);
            }
            if(m instanceof cust || m instanceof tot) {
                System.out.println("Actor "+ this +" requesting targeting -- pos size "+ posSession.pos.size());
            	if(targetingActor == null) {
            	 targetingActor = getContext().actorOf(Props.create(StoreSessionTargetingActor.class).withDispatcher("blocking-io-dispatcher"));            		
            	}
                posSession.targetingCalls++;
                targetingActor.tell(posSession, getSelf());
            } else if(m instanceof eord) {
            	targetingActor.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
                getSelf().tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
            }
        }).build();
    }
    
    public static Props props() {
    	return Props.create(StoreSessionActor.class).withDispatcher("blocking-io-dispatcher");
    }
}
