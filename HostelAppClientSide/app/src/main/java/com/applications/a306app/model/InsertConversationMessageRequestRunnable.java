package com.applications.a306app.model;

import android.util.Log;

import com.applications.a306app.xml.XMLMessagesCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class InsertConversationMessageRequestRunnable implements Runnable {

    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;
    private List<PrivateMessage> dataToSend;

    public InsertConversationMessageRequestRunnable(Socket mySocket,List<PrivateMessage> dataToSend) {

        this.clientSocket=mySocket;
        this.dataToSend=dataToSend;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputClient = XMLMessagesCreator.createInsertConversationMessageXML(dataToSend.get(0));

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }

}
