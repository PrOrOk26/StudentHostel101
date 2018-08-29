package com.applications.a306app.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.applications.a306app.xml.XMLConstants;
import com.applications.a306app.xml.XMLMessagesParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CheckNewMessagesResponceRunnable implements Runnable {

    private Handler handler;
    private Socket clientSocket;
    private String inputClient;

    private BufferedReader fromServerReader;
    private XMLMessagesParser myParser;
    private List<String> resultOfXMLParsing;


    public CheckNewMessagesResponceRunnable(Socket inputSocket, Handler handler) {

        this.clientSocket=inputSocket;
        this.handler = handler;

        myParser = new XMLMessagesParser();
        resultOfXMLParsing = new ArrayList<>();
    }


    @Override
    public void run() {
            try {
                fromServerReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Message msg;
                int messageType = 0;
                while ((inputClient = fromServerReader.readLine()) != null) {

                        Log.d("Input:", inputClient);

                        resultOfXMLParsing = myParser.getParsingResultFromStream(inputClient);

                        MessageDB.addChatMessagesFromStrings(resultOfXMLParsing);

                        messageType = myParser.getXMLMessageType();

                        msg = handler.obtainMessage(messageType);
                        Bundle bundle = new Bundle();

                        if (messageType == HandleServer.HandleServerResponseConstants.SETRETRIEVERESENTCHATMESSAGE) {

                            bundle.putString(XMLConstants.TAGS_RETRIEVE_RESENT_CHAT_MESSAGES, "success");

                        } else
                            throw new IOException("Wrong response type! " + Integer.toString(messageType));
                        msg.setData(bundle);
                        handler.sendMessage(msg);

                        myParser.nullifyAllTags();

                }
                clientSocket.close();
            }
             catch (IOException e) {
                e.printStackTrace();
            }
        }



}
