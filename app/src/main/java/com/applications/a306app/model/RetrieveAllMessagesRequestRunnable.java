package com.applications.a306app.model;

import android.util.Log;

import com.applications.a306app.xml.XMLMessagesCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RetrieveAllMessagesRequestRunnable implements Runnable {

    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;

    public RetrieveAllMessagesRequestRunnable(Socket mySocket) {

        this.clientSocket=mySocket;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputClient = XMLMessagesCreator.createRetrieveAllChatMessagesXML();

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
