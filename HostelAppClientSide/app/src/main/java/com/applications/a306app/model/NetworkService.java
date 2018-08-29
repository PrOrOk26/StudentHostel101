package com.applications.a306app.model;

import java.io.IOException;
import java.net.Socket;

public class NetworkService {

    private Socket mySocket;


    public NetworkService(int port,String ip) {
        Thread do1=new Thread(new InitSocket(port,ip));
        do1.start();

        try {
            do1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Socket getMySocket() {
        return mySocket;
    }


    private class InitSocket implements Runnable {

        private int port;
        private String ip;

        public InitSocket(int port, String ip) {
            this.port = port;
            this.ip = ip;
        }


        @Override
        public void run() {
            try {
                mySocket=new Socket(ip,port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
