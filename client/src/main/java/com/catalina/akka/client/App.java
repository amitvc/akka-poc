package com.catalina.akka.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws UnknownHostException, IOException {
        ExecutorService svc = Executors.newCachedThreadPool();
        Socket client = new Socket("localhost", 9000);
        PrintWriter out = new PrintWriter(client.getOutputStream(),true);
        System.out.println("Got connected to server on 9000");
    }
}
