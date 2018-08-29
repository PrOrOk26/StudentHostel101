package com.applications.a306app.model;

import android.os.Handler;
import android.util.Log;

import com.applications.a306app.xml.XMLCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RetrieveGarbageHistoryRequestRunnable implements Runnable{

    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;

    private final int type;

    public RetrieveGarbageHistoryRequestRunnable(Socket mySocket,int type) {

        this.clientSocket=mySocket;
        this.type=type;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputClient = XMLCreator.createRetrieveGarbageXML(type,UsersDB.getHostUser().getLogin());

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
