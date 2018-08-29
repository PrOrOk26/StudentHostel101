package com.applications.a306app.model;

import android.util.Log;

import com.applications.a306app.xml.XMLMessagesCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ListenForNewMessagesConversationRequestRunnable implements Runnable{

    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;
    private int conversationId;

    public ListenForNewMessagesConversationRequestRunnable(Socket mySocket,int conversationId) {

        this.clientSocket=mySocket;
        this.conversationId=conversationId;
    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputClient = XMLMessagesCreator.createGetRecentConversationMessagesXML(conversationId);

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();


    }
}
