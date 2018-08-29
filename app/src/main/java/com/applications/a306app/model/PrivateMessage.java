package com.applications.a306app.model;

import com.applications.a306app.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

public class PrivateMessage{

    private String message;
    private User sender;
    private Date date;
    private int conversationId;

    public PrivateMessage(String message,User sender,int conversationId) {
        this.message = message;
        this.sender = sender;
        this.date= Calendar.getInstance().getTime();
        this.conversationId=conversationId;
    }

    public PrivateMessage(String message,User sender,String date,int conversationId) {
        this.message = message;
        this.sender = sender;
        this.date= DateTimeUtils.convertStringToDate(date);
        this.conversationId=conversationId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
}
