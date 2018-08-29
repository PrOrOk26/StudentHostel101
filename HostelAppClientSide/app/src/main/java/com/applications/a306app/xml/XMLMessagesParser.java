package com.applications.a306app.xml;

import android.util.Log;

import com.applications.a306app.model.HandleServer;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLMessagesParser {


    private int messageType;
    private ArrayList<String> parsingResult;
    private boolean date=false;
    private boolean login=false;
    private boolean user=false;
    private boolean group=false;
    private boolean text=false;
    private boolean sendchatmessage=false;
    private boolean receivechatmessage=false;

    private boolean retrieveallchatmessages=false;
    private boolean retrieveresentchatmessages=false;

    private boolean getusersinfo=false;
    private boolean conversation=false;

    private boolean insertmessage=false;
    private boolean getallmessages=false;
    private boolean getrecentmessages=false;

    private void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public XMLMessagesParser()
    {

        Log.d("Parse messages:","Parser messages CONSTRUCTOR");
    }

    public ArrayList<String> getParsingResultFromStream(String xmlDocument) {

        parsingResult=new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            Log.d("Parse messages:","The next line is handler instantiation");


            DefaultHandler handlerXML = new DefaultHandler() {


                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
                {
                    setAppropriateTag(qName);
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException
                {
                    setResultTextForNode(ch,start,length);
                }

                public void setAppropriateTag(String tagCatched)
                {

                    if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_USER)) {
                    user = true;
                    Log.d("XMLParser",XMLConstants.TAGS_USER);

                }
                    if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_TEXT)) {
                        text = true;
                        Log.d("XMLParser",XMLConstants.TAGS_TEXT);

                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP)) {
                        group = true;
                        Log.d("XMLParser",XMLConstants.TAGS_GROUP);
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_DATE)) {
                        date = true;
                        Log.d("XMLParser",XMLConstants.TAGS_DATE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_LOGIN))
                    {
                        login=true;
                        Log.d("XMLParser",XMLConstants.TAGS_LOGIN);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_SEND_MAIN_CHAT_MESSAGE))
                    {
                        sendchatmessage=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETSENDCHATMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_RETRIEVE_ALL_CHAT))
                    {
                        retrieveallchatmessages=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETRETRIEVEALLCHATMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_RETRIEVE_RESENT_CHAT_MESSAGES))
                    {
                        retrieveresentchatmessages=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETRETRIEVERESENTCHATMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GET_ALL_USERS_INFO))
                    {
                        getusersinfo=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGETALLUSERSINFO);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_CONVERSATION))
                    {
                        conversation=true;
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GET_CONVERSATION_MESSAGES))
                    {
                        getallmessages=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGETALLCONVERSATIONMESSAGES);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GET_RECENT_CONVERSATION_MESSAGES))
                    {
                        getrecentmessages=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGETRECENTCONVERSATIONMESSAGES);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_INSERT_MESSAGE_TO_CONVERSATION))
                    {
                        insertmessage=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETINSERTCONVERSATIONMESSAGE);
                    }

                }

                public void setResultTextForNode(char[] ch, int start, int length)
                {
                    if (date) {
                        parsingResult.add(new String(ch,start,length));
                        date = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (group) {
                        parsingResult.add(new String(ch,start,length));
                        group = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (login) {
                        parsingResult.add(new String(ch,start,length));
                        login = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (text) {
                        parsingResult.add(new String(ch,start,length));
                        text = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (receivechatmessage) {
                        receivechatmessage = false;
                    }
                    else if (sendchatmessage) {

                        sendchatmessage = false;
                    }
                    else if (user) {
                        parsingResult.add(new String(ch,start,length));
                        user = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (retrieveallchatmessages) {

                        retrieveallchatmessages = false;
                    }
                    else if (retrieveresentchatmessages) {

                        retrieveresentchatmessages = false;
                    }
                    else if(getusersinfo)
                    {
                        getusersinfo=false;
                    }
                    else if(conversation)
                    {
                        parsingResult.add(new String(ch,start,length));
                        conversation=false;
                    }
                    else if(getrecentmessages)
                    {
                        getrecentmessages=false;
                    }
                    else if(getallmessages)
                    {
                        getallmessages=false;
                    }
                    else if(insertmessage)
                    {
                        insertmessage=false;
                    }


                }
            };

            Log.d("Parse:","The next line is parser");

            saxParser.parse(new InputSource(new StringReader(xmlDocument)),handlerXML);

            Log.d("Parse:","The last line is parser");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsingResult;
    }

    public int getXMLMessageType()
    {
        switch(messageType) {
            case HandleServer.HandleServerResponseConstants.SETSENDCHATMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETSENDCHATMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETRETRIEVEALLCHATMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETRETRIEVEALLCHATMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETRETRIEVERESENTCHATMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETRETRIEVERESENTCHATMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGETALLUSERSINFO:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGETALLUSERSINFO;
            }
            case HandleServer.HandleServerResponseConstants.SETGETALLCONVERSATIONMESSAGES:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGETALLCONVERSATIONMESSAGES;
            }
            case HandleServer.HandleServerResponseConstants.SETGETRECENTCONVERSATIONMESSAGES:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGETRECENTCONVERSATIONMESSAGES;
            }
            case HandleServer.HandleServerResponseConstants.SETINSERTCONVERSATIONMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETINSERTCONVERSATIONMESSAGE;
            }

        }
        return -1;
    }

    public void nullifyAllTags()
    {

        this.date=false;
        this.user=false;
        this.group=false;
        this.text=false;
        this.sendchatmessage=false;
        this.receivechatmessage=false;
        this.login=false;
        this.retrieveallchatmessages=false;
        this.retrieveresentchatmessages=false;

        this.getusersinfo=false;
        this.conversation=false;

        this.insertmessage=false;
        this.getallmessages=false;
        this.getrecentmessages=false;

    }
}
