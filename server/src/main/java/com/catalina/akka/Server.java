package com.catalina.akka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.catalina.akka.models.cust;
import com.catalina.akka.models.eord;
import com.catalina.akka.models.msg;
import com.catalina.akka.models.sord;
import com.catalina.akka.models.tot;
import com.catalina.akka.models.upc;
import com.catalina.akka.storage.SessionStorage;
import com.google.gson.Gson;

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
            svc.submit(new Thread(new ClientHandler(client_connection, new SessionStorage())));
        }
    }
    
    public static class ClientHandler implements Runnable {
        private BufferedReader reader;
        private SessionStorage sessionStorage;
        public ClientHandler(Socket client_connection, SessionStorage s) {
            try {                
                reader = new BufferedReader(new InputStreamReader(client_connection.getInputStream()));
                this.sessionStorage = s;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void run() {
        	Gson gson = new Gson();
            while(true) {
                try {
                    String msg_str = reader.readLine();
                    msg m = null;
                    if(msg_str.contains("_type\":1")) {
                    	m= gson.fromJson(msg_str, sord.class);
                    } else if(msg_str.contains("_type\":2")) {
                    	m= gson.fromJson(msg_str, upc.class);
                    } else if(msg_str.contains("_type\":3")) {
                    	m= gson.fromJson(msg_str, cust.class);
                    } else if(msg_str.contains("_type\":4")) {
                    	m= gson.fromJson(msg_str, tot.class);
                    } else if(msg_str.contains("_type\":5")) {
                    	m= gson.fromJson(msg_str, eord.class);
                    }
                    sessionStorage.handleMessage(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
