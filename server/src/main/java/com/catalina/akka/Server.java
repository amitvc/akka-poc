package com.catalina.akka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    
    private ServerSocket server_socket;
    
    public Server() {
    }
    
    public void init() throws IOException {
        server_socket = new ServerSocket(9001, 10);
        ExecutorService svc =  Executors.newCachedThreadPool();
        System.out.println("Creating socket on port 9000");
        while(true) {
            System.out.println("Waiting to accept incoming connections");
            Socket client_connection = server_socket.accept();
            System.out.println("got connection from "+ client_connection.getInetAddress().getHostAddress());
            svc.submit(new Thread(new ClientHandler(client_connection)));
        }
    }
    
    public static class ClientHandler implements Runnable {
        private BufferedReader reader;
        public ClientHandler(Socket client_connection) {
            try {                
                reader = new BufferedReader(new InputStreamReader(client_connection.getInputStream()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void run() {
            while(true) {
                try {
                    String msg = reader.readLine();
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
