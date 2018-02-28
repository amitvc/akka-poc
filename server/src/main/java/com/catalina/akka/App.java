package com.catalina.akka;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.init();
    }
}
