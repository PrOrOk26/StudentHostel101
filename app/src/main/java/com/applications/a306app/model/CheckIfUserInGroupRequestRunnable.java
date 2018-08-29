package com.applications.a306app.model;

import android.os.Handler;
import android.util.Log;

import com.applications.a306app.xml.XMLCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class CheckIfUserInGroupRequestRunnable implements Runnable{

    private Handler handler;
    private Socket clientSocket;
    private String outputClient;

    private PrintWriter myPrintWriter;

    public CheckIfUserInGroupRequestRunnable(Socket mySocket,Handler handler) {

        this.clientSocket=mySocket;
        this.handler=handler;

    }

    @Override
    public void run() {

        try {
            myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<User> validateHost=new ArrayList<>();
        validateHost.add(UsersDB.getHostUser());
        outputClient = XMLCreator.createUserGroupValidationXML(validateHost);

        Log.d("Result XML validation:", outputClient);

        myPrintWriter.println(outputClient);
        myPrintWriter.flush();

    }
}
