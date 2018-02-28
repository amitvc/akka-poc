package com.catalina.akka.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        List<Thread> threadList = new ArrayList<Thread>();
        for(int i=0; i < 1; i++) {            
            Socket client = new Socket("localhost", 9001);
            Thread.sleep(100);
            StoreSessionSimulator storeSessionSimulator = new StoreSessionSimulator(client);
            System.out.println("Got connected to server on 9001");
            Thread t = new Thread(storeSessionSimulator);
            threadList.add(t);
            t.start();
        }
        
        for(Thread t : threadList) {
            t.join();
        }
    }
}
