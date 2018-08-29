package com.applications.a306app.model;

import android.util.Log;

import com.applications.a306app.xml.XMLMessagesCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GetAllConversationMessagesRequestRunnable implements Runnable{

    private Socket clientSocket;
    private String outputClient;
    private int conversationId;

    private PrintWriter myPrintWriter;

    public GetAllConversationMessagesRequestRunnable(Socket mySocket,int conversationId) {

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

        outputClient = XMLMessagesCreator.createGetAllConversationMessagesXML(conversationId);

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
