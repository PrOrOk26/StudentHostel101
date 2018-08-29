package com.applications.a306app.model;

import android.os.Handler;
import android.util.Log;

import com.applications.a306app.xml.XMLCreator;
import com.applications.a306app.xml.XMLMessagesCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SendMessageRequestRunnable implements Runnable{

    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;
    private List<Message> dataToSend;

    public SendMessageRequestRunnable(Socket mySocket,List<Message> dataToSend) {

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

        outputClient = XMLMessagesCreator.createSendChatMessagesXML(dataToSend);

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
