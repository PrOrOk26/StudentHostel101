package com.applications.a306app.model;

import com.applications.a306app.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageDB {

    //used to find out if our DB is initialized,if not then initialize it

    private static boolean isChatInitialized =false;

    private static boolean isPrivateMessagesInitialized =false;

    private static List<Message> myMessageList=new ArrayList<>();

    private static List<PrivateMessage> myPrivateMessageList=new ArrayList<>();

    private static List<Integer> initializedConversations=new ArrayList<>();

    public static List<Message> getMyChatMessageList() {
        return myMessageList;
    }

    public static Message getLastChatMessage()
    {
        return myMessageList.get(myMessageList.size()-1);
    }

    public static void addChatMessages(List<Message> parsedMessages)
    {
        Iterator<Message> iterator=parsedMessages.iterator();

        while(iterator.hasNext())
        {
            myMessageList.add(iterator.next());
        }
    }

    public static void addInitializedConversation(int toAdd)
    {
        initializedConversations.add(toAdd);
    }

    public static boolean isConversationInitialized(int toCheck)
    {
        if(initializedConversations.contains(toCheck))
            return true;
        return false;
    }

    public static void addChatMessage(Message message)
    {
        myMessageList.add(message);
    }

    public static void addChatMessagesFromStrings(List<String> parsedMessages)
    {
        Iterator<String> iterator=parsedMessages.iterator();
        Message buff;
        String text=new String();
        User buffUser=new User();

        while(iterator.hasNext())
        {
            buffUser.setLogin(iterator.next());
            text=iterator.next();
            buff=new Message(text,buffUser,iterator.next());
            myMessageList.add(buff);
        }
    }

    public static void initializeChatMessageDB(List<String> resultOfXMLParsing) {

        Iterator<String> iterator=resultOfXMLParsing.iterator();
        Message buff;
        String text=new String();
        User buffUser;

        while(iterator.hasNext())
        {
            buffUser=new User();
            buffUser.setLogin(iterator.next());
            text=iterator.next();
            buff=new Message(text,buffUser,iterator.next());
            myMessageList.add(buff);
        }

        isChatInitialized=true;
    }

    public static List<Message> getMyConversationMessageList(User receiver) {
        return myMessageList;
    }

    public static void addPrivateMessages(List<PrivateMessage> parsedMessages)
    {
        Iterator<PrivateMessage> iterator=parsedMessages.iterator();

        while(iterator.hasNext())
        {
            myPrivateMessageList.add(iterator.next());
        }
    }

    public static void addPrivateMessage(PrivateMessage message)
    {
        myPrivateMessageList.add(message);
    }

    public static void addPrivateMessagesFromStrings(List<String> parsedMessages)
    {
        Iterator<String> iterator=parsedMessages.iterator();
        PrivateMessage buff;
        String text;
        String login;
        String date;
        String conversation;
        User buffUser=new User();

        while(iterator.hasNext())
        {
            conversation=iterator.next();
            text=iterator.next();
            login=iterator.next();
            date=iterator.next();

            buffUser.setLogin(login);

            buff=new PrivateMessage(text,buffUser,date,Integer.parseInt(conversation));
            myPrivateMessageList.add(buff);
        }
    }

    public static void initializePrivateMessageDB(List<String> resultOfXMLParsing) {

        Iterator<String> iterator=resultOfXMLParsing.iterator();
        PrivateMessage buff;
        String text=new String();
        User buffUser;

        while(iterator.hasNext())
        {
            buffUser=new User();
            buffUser.setLogin(iterator.next());
            text=iterator.next();
            buff=new PrivateMessage(text,buffUser,iterator.next(),Integer.parseInt(iterator.next()));
            myPrivateMessageList.add(buff);
        }

        isPrivateMessagesInitialized=true;
    }

    public static boolean isChatInitialized() {
        return isChatInitialized;
    }

    public static boolean isPrivateInitialized() {
        return isPrivateMessagesInitialized;
    }

    public static List<PrivateMessage> getAllConversationMessages(int conversation)
    {
        Iterator<PrivateMessage> iterator=myPrivateMessageList.iterator();

        List<PrivateMessage> conversationMessages=new ArrayList<>();

        while(iterator.hasNext())
        {
            PrivateMessage buff=iterator.next();

            if(buff.getConversationId()==conversation)
            {
                conversationMessages.add(buff);
            }
        }

        return conversationMessages;
    }

    public static void setPrivateMessagesInitialization(boolean privateMessagesInitialization) {

        isPrivateMessagesInitialized = privateMessagesInitialization;
    }

    public static void setChatInitialization(boolean chatInitialization) {

        isChatInitialized = chatInitialization;
    }
}
