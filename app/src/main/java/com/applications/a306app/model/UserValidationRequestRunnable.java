package com.applications.a306app.model;

import android.os.Handler;
import android.util.Log;

import com.applications.a306app.xml.XMLCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserValidationRequestRunnable implements Runnable {

    private Handler handler;
    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;
    private List<String> dataToSend;

    public UserValidationRequestRunnable(Socket mySocket,Handler handler,List<String> dataToSend) {

        this.clientSocket=mySocket;
        this.handler=handler;
        this.dataToSend = dataToSend;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<String> iterator = dataToSend.iterator();
        while (iterator.hasNext()) {
            UsersDB.setLoginPasswordForHostUser(iterator.next(), iterator.next());
        }
        ArrayList<User> validateHost=new ArrayList<>();
        validateHost.add(UsersDB.getHostUser());
        outputClient = XMLCreator.createUserValidationXML(validateHost);

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
