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
import java.util.Iterator;
import java.util.List;

public class GetAllUsersToMessageResponceRunnable implements Runnable{

    private Handler handler;
    private Socket clientSocket;
    private String inputClient;

    private BufferedReader fromServerReader;
    private XMLMessagesParser myParser;
    private List<String> resultOfXMLParsing;

    public GetAllUsersToMessageResponceRunnable(Socket inputSocket, Handler handler) {

        this.clientSocket=inputSocket;
        this.handler = handler;

        myParser = new XMLMessagesParser();
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

                Iterator<String> iterator=resultOfXMLParsing.iterator();

                List<User> toAdd=new ArrayList<>();

                User buff;

                while (iterator.hasNext())
                {
                    buff=new User();
                    List<Integer> conversationsList=new ArrayList<>();
                    conversationsList.add(Integer.parseInt(iterator.next()));
                    buff.setConversationsIds(conversationsList);
                    buff.setLogin(iterator.next());
                    toAdd.add(buff);
                }

                UsersDB.addUsers(toAdd);

                messageType = myParser.getXMLMessageType();

                msg = handler.obtainMessage(messageType);
                Bundle bundle = new Bundle();

                if(messageType== HandleServer.HandleServerResponseConstants.SETGETALLUSERSINFO) {

                    bundle.putString(XMLConstants.TAGS_GET_ALL_USERS_INFO,"success");

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
