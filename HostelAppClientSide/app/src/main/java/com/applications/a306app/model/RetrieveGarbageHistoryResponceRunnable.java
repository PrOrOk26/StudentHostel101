package com.applications.a306app.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.applications.a306app.utils.DateTimeUtils;
import com.applications.a306app.xml.XMLConstants;
import com.applications.a306app.xml.XMLMessagesParser;
import com.applications.a306app.xml.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RetrieveGarbageHistoryResponceRunnable implements Runnable{

    private Handler handler;
    private Socket clientSocket;
    private String inputClient;

    private BufferedReader fromServerReader;
    private XMLParser myParser;
    private List<String> resultOfXMLParsing;

    public RetrieveGarbageHistoryResponceRunnable(Socket inputSocket, Handler handler) {

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




        Iterator<String> iterator;
        try {
            while ((inputClient = fromServerReader.readLine()) != null) {

                Log.d("Input:", inputClient);

                resultOfXMLParsing = myParser.getParsingResultFromStream(inputClient);

                iterator=resultOfXMLParsing.iterator();

                messageType = myParser.getXMLMessageType();

                msg = handler.obtainMessage(messageType);
                Bundle bundle = new Bundle();

                UserActivity buffAct;

                while(iterator.hasNext())
                {
                    buffAct=new UserActivity(HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE);
                    buffAct.setPerformerLogin(iterator.next());
                    buffAct.setPerformerName(iterator.next());
                    buffAct.setPerformerSurname(iterator.next());
                    buffAct.setDate(DateTimeUtils.convertStringToDate(iterator.next()));
                    UserActivityDB.addActivity(buffAct);
                }

                if(messageType== HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE) {

                    bundle.putString(XMLConstants.TAGS_REQUEST_GARBAGE_HISTORY,XMLConstants.TAGS_REQUEST_GARBAGE_HISTORY);

                }
                else throw new IOException("Wrong response type! "+Integer.toString(messageType));
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
