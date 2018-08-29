package com.applications.a306app.model;

import android.os.Handler;
import android.util.Log;

import com.applications.a306app.xml.XMLCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestRunnable implements Runnable{

    private Handler handler;
    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;
    private List<String> dataToSend;

    public CreateGroupRequestRunnable(Socket mySocket,Handler handler,List<String> dataToSend) {

        this.clientSocket=mySocket;
        this.handler=handler;
        this.dataToSend=dataToSend;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        User hostUser=UsersDB.getHostUser();
        ArrayList<User> users=new ArrayList<>();

        users.add(hostUser);

        users.get(0).setLogin(hostUser.getLogin());
        users.get(0).setGroup(dataToSend.get(0));

        outputClient = XMLCreator.createUserGroupCreationXML(users);

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
