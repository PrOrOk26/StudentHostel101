package com.applications.a306app.model;

import com.applications.a306app.utils.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Message {

    private String message;
    private User sender;
    private Date date;


    public Message(String message, User sender) {
        this.message = message;
        this.sender = sender;
        this.date=Calendar.getInstance().getTime();
    }

    public Message(String message, User sender,String date) {
        this.message = message;
        this.sender = sender;
        this.date= DateTimeUtils.convertStringToDate(date);
    }

    public String getMessage() {
        return message;
    }

    public User getSender() {
        return sender;
    }

    public Date getDate() {
        return date;
    }
}
