package com.catalina.akka.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.catalina.akka.models.cust;
import com.catalina.akka.models.eord;
import com.catalina.akka.models.hdr;
import com.catalina.akka.models.msg;
import com.catalina.akka.models.sord;
import com.catalina.akka.models.tot;
import com.catalina.akka.models.upc;
import com.google.gson.Gson;

public class StoreSessionSimulator implements Runnable {

    private Socket client;

    public StoreSessionSimulator(Socket client) {
        this.client = client;
    }

    public void run() {

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            Gson gson = new Gson();
            int i=0;
            while (i < 50000) {
                i++;
                List<msg> messages = getNewMessages();
                for (msg m : messages) {
                    Thread.sleep(20);
                    out.write(gson.toJson(m));
                    out.newLine();
                    out.flush();
                }
                Thread.sleep(100);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List<msg> getNewMessages() {
        hdr _hdr = new hdr(199, 321, UUID.randomUUID().toString(), 1, System.currentTimeMillis());
        List<msg> messages = new ArrayList<msg>();
        sord s = new sord(_hdr);
        messages.add(s);
        upc upc1 = new upc(_hdr, "111111111", 2, 300);
        messages.add(upc1);
        upc upc2 = new upc(_hdr, "222222222", 4, 300);
        messages.add(upc2);
        upc upc3 = new upc(_hdr, "333333333", 5, 800);
        messages.add(upc3);
        upc upc4 = new upc(_hdr, "444444444", 2, 800);
        messages.add(upc4);
        upc upc5 = new upc(_hdr, "555555555", 3, 800);
        messages.add(upc5);
        cust cid = new cust(_hdr);
        cid.cid = "999999";
        messages.add(cid);
        cid = new cust(_hdr);
        cid.cid = "9000000000";
        messages.add(cid);
        tot t = new tot(_hdr);
        messages.add(t);
        eord _eord = new eord(_hdr);
        messages.add(_eord);
        return messages;
    }
}
