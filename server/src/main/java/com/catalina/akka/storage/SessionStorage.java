package com.catalina.akka.storage;

import java.util.HashMap;
import java.util.Map;
import akka.actor.ActorRef;

public class SessionStorage {
    
    private Map<String, ActorRef> sessions = new HashMap<String, ActorRef>();
    
     

}
