package com.applications.a306app.model;

import android.util.Log;

import com.applications.a306app.xml.XMLCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class InsertGarbageRequestRunnable implements Runnable{

    private Socket clientSocket;
    private String outputClient;
    private UserActivity activity;

    private PrintWriter myPrintWriter;


    public InsertGarbageRequestRunnable(Socket mySocket,UserActivity activity) {

        this.clientSocket=mySocket;
        this.activity=activity;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputClient = XMLCreator.createInsertGarbageXML(activity);

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
