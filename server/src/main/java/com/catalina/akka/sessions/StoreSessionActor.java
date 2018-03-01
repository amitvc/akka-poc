package com.catalina.akka.sessions;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.catalina.akka.StoreSessionHandlerActor;
import com.catalina.akka.models.cust;
import com.catalina.akka.models.msg;
import com.catalina.akka.models.tot;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class StoreSessionActor extends AbstractActor{

    private ConcurrentLinkedQueue<msg> pos = new ConcurrentLinkedQueue<msg>();

    
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(msg.class, m -> {
            System.out.println("Actor " + this.getSelf().toString() + "  pos size:" + pos.size());
            pos.offer(m);
            if(m instanceof cust || m instanceof tot) {
                System.out.println("Actor "+ getSelf().toString()+" requesting targeting -- pos size "+ pos.size());
                ActorRef nextActor = getContext().actorOf(Props.create(StoreSessionHandlerActor.class).withDispatcher("blocking-io-dispatcher"));
                nextActor.tell(pos, getSelf());
            }
        }).build();
    }
}
