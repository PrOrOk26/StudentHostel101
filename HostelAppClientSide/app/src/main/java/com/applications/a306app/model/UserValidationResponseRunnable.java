package com.applications.a306app.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.applications.a306app.xml.XMLConstants;
import com.applications.a306app.xml.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UserValidationResponseRunnable implements Runnable {

    private Handler handler;
    private Socket clientSocket;
    private String inputClient;

    private BufferedReader fromServerReader;
    private XMLParser myParser;
    private List<String> resultOfXMLParsing;

    public UserValidationResponseRunnable(Socket inputSocket, Handler handler) {

        this.clientSocket=inputSocket;
        this.handler = handler;

        myParser = new XMLParser();
        resultOfXMLParsing = new ArrayList<>();
    }

    @Override
    public void run() {

        try {
            fromServerReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg;
        int messageType = 0;
        try {
            while ((inputClient = fromServerReader.readLine()) != null) {

                Log.d("Input:", inputClient);

                resultOfXMLParsing = myParser.getParsingResultFromStream(inputClient);

                messageType = myParser.getXMLMessageType();

                msg = handler.obtainMessage(messageType);
                Bundle bundle = new Bundle();

                if(messageType==com.applications.a306app.model.HandleServer.HandleServerResponseConstants.SETVALIDATIONMESSAGE) {
                        UserValidation validation = new UserValidation(resultOfXMLParsing);
                        bundle.putString(XMLConstants.TAGS_LOGIN_VALIDATION,validation.getValidationMessage());
                    }
                else throw new IOException("Wrong response type!");
                msg.setData(bundle);
                handler.sendMessage(msg);

                myParser.nullifyAllTags();

            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
