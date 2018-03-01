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
public class AppClient {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        List<Thread> threadList = new ArrayList<Thread>();
        Socket client = new Socket("localhost", 9001);
        
        for(int i=0; i < 500; i++) {
            Thread.sleep(10);
            StoreSessionSimulator storeSessionSimulator = new StoreSessionSimulator(client);
            Thread t = new Thread(storeSessionSimulator);
            threadList.add(t);
            t.start();
        }
        
        for(Thread t : threadList) {
            t.join();
        }
    }
}
