package com.applications.a306app.model;

import android.os.Handler;
import android.util.Log;

import com.applications.a306app.xml.XMLMessagesCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GetAllUsersToMessageRequestRunnable implements Runnable{

    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;

    public GetAllUsersToMessageRequestRunnable(Socket mySocket) {

        this.clientSocket=mySocket;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputClient = XMLMessagesCreator.createGetAllUsersConversationsXML();

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
