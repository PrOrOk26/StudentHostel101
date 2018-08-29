package com.applications.a306app.model;

import android.os.Handler;
import android.util.Log;

import com.applications.a306app.xml.XMLCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

public class UserRegisterRequestRunnable implements Runnable {

    private Handler handler;
    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;
    private List<String> dataToSend;

    public UserRegisterRequestRunnable(Socket mySocket,Handler handler,List<String> dataToSend) {

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
            UsersDB.addUser(iterator.next(), iterator.next(),iterator.next(), iterator.next());
        }
        outputClient = XMLCreator.createUserRegistrationXML(UsersDB.getUsersData());

        UsersDB.deleteLastUser();

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
