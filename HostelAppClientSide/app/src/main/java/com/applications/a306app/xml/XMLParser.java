package com.applications.a306app.xml;

import android.util.Log;

import com.applications.a306app.model.HandleServer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;


public class XMLParser {

    private boolean name = false;
    private boolean surname=false;
    private boolean garbage=false;
    private boolean date=false;
    private boolean bread=false;
    private boolean group=false;

    private boolean user=false;
    private boolean validation=false;
    private boolean login=false;
    private boolean password=false;
    private boolean registration=false;

    private boolean groupvalidation=false;
    private boolean groupcreation=false;
    private boolean groupinsert=false;
    private boolean qroupquit=false;


    private boolean insertgarbage=false;
    private boolean insertbread=false;

    private boolean retrievebread=false;
    private boolean retrievegarbage=false;

    private boolean requestBreadGarbageType=false;

    private int messageType;
    private ArrayList<String> parsingResult;

    private void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public XMLParser()
    {

        Log.d("Parse:","Parser CONSTRUCTOR");
    }

    public ArrayList<String> getParsingResultFromStream(String xmlDocument) {

        parsingResult=new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            Log.d("Parse:","The next line is handler instantiation");


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
                    if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GARBAGE)) {
                        garbage = true;
                        Log.d("XMLParser",XMLConstants.TAGS_GARBAGE);
                    }

                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_BREAD)) {
                        bread = true;
                        Log.d("XMLParser",XMLConstants.TAGS_BREAD);

                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_USER)) {
                        user = true;
                        Log.d("XMLParser",XMLConstants.TAGS_USER);

                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_NAME)) {
                        name = true;
                        Log.d("XMLParser",XMLConstants.TAGS_NAME);
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP)) {
                        group = true;
                        Log.d("XMLParser",XMLConstants.TAGS_GROUP);
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_SURNAME)) {
                        surname = true;
                        Log.d("XMLParser",XMLConstants.TAGS_SURNAME);
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_DATE)) {
                        date = true;
                        Log.d("XMLParser",XMLConstants.TAGS_DATE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_LOGIN))
                    {
                        login=true;
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_PASSWORD))
                    {
                        password=true;
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_LOGIN_VALIDATION))
                    {
                        validation=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETVALIDATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_USER_REGISTRATION))
                    {
                        registration=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETREGISTRATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_VALIDATION))
                    {
                        groupvalidation=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGROUPVALIDATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_CREATION))
                    {
                        groupcreation=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGROUPCREATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_ADD_USER))
                    {
                        groupinsert=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGROUPINVITATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_USER_QUIT))
                    {
                        qroupquit=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGROUPUSERQUITMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_INSERT_BREAD))
                    {
                        insertbread=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETINSERTBREADMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_INSERT_GARBAGE))
                    {
                        insertgarbage=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETINSERTGARBAGEMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_REQUEST_BREAD_HISTORY))
                    {
                        retrievebread=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_REQUEST_GARBAGE_HISTORY))
                    {
                        retrievegarbage=true;
                        setMessageType(HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_REQUEST_TYPE))
                    {
                        requestBreadGarbageType=true;
                    }

                }

                public void setResultTextForNode(char[] ch, int start, int length)
                {
                    if (name) {
                        parsingResult.add(new String(ch,start,length));
                        name = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (surname) {
                        parsingResult.add(new String(ch,start,length));
                        surname = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (date) {
                        parsingResult.add(new String(ch,start,length));
                        date = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if (group) {
                        parsingResult.add(new String(ch,start,length));
                        group = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(validation)
                    {
                        parsingResult.add(new String(ch,start,length));
                        validation = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(registration)
                    {
                        parsingResult.add(new String(ch,start,length));
                        registration= false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(groupvalidation)
                    {
                        parsingResult.add(new String(ch,start,length));
                        groupvalidation= false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(groupcreation)
                    {
                        parsingResult.add(new String(ch,start,length));
                        groupcreation= false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(groupinsert)
                    {
                        parsingResult.add(new String(ch,start,length));
                        groupinsert = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(qroupquit)
                    {
                        parsingResult.add(new String(ch,start,length));
                        qroupquit = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(bread)
                    {
                        parsingResult.add(new String(ch,start,length));
                        bread = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(garbage)
                    {
                        parsingResult.add(new String(ch,start,length));
                        garbage = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(requestBreadGarbageType)
                    {
                        requestBreadGarbageType = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(insertbread)
                    {
                        parsingResult.add(new String(ch,start,length));
                        insertbread = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(insertgarbage)
                    {
                        parsingResult.add(new String(ch,start,length));
                        insertgarbage = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(retrievebread)
                    {
                        retrievebread = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
                    }
                    else if(retrievegarbage)
                    {
                        retrievegarbage = false;
                        Log.d("XMLParser",parsingResult.get(parsingResult.size()-1));
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
            case HandleServer.HandleServerResponseConstants.SETBREADMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETBREADMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGARBAGEMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGARBAGEMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETVALIDATIONMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETVALIDATIONMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETREGISTRATIONMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETREGISTRATIONMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGROUPVALIDATIONMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGROUPVALIDATIONMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGROUPCREATIONMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGROUPCREATIONMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGROUPINVITATIONMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGROUPINVITATIONMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGROUPUSERQUITMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGROUPUSERQUITMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETINSERTBREADMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETINSERTBREADMESSAGE;
            }
            case HandleServer.HandleServerResponseConstants.SETINSERTGARBAGEMESSAGE:
            {
                nullifyAllTags();
                return HandleServer.HandleServerResponseConstants.SETINSERTGARBAGEMESSAGE;
            }

        }
        return -1;
    }

    public void nullifyAllTags()
    {
        this.garbage=false;
        this.bread=false;
        this.date=false;
        this.name=false;
        this.surname=false;
        this.user=false;
        this.registration=false;
        this.groupcreation=false;
        this.groupinsert=false;
        this.qroupquit=false;

        this.insertbread=false;
        this.insertgarbage=false;

        this.retrievebread=false;
        this.retrievegarbage=false;

        this.requestBreadGarbageType=false;
    }


}
