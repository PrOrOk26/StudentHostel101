
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLMessagesParser{


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
    
    private Logger log;

    private void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public XMLMessagesParser()
    {
    	log=Logger.getLogger(LoggingMXBean.class.getName());
    }

    public ArrayList<String> getParsingResultFromStream(String xmlDocument) {

        parsingResult=new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();         


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
                    

                }
                    if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_TEXT)) {
                        text = true;
                        

                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP)) {
                        group = true;
                        
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_DATE)) {
                        date = true;
                        
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_LOGIN))
                    {
                        login=true;
                        
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_SEND_MAIN_CHAT_MESSAGE))
                    {
                        sendchatmessage=true;
                        setMessageType(XMLConstants.SETSENDCHATMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_RECEIVE_MAIN_CHAT_MESSAGE))
                    {
                        receivechatmessage=true;                      
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_RETRIEVE_ALL_CHAT))
                    {
                    	retrieveallchatmessages=true;  
                    	setMessageType(XMLConstants.SETRETRIEVEALLCHATMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_RETRIEVE_RESENT_CHAT_MESSAGES))
                    {
                    	retrieveresentchatmessages=true;  
                    	setMessageType(XMLConstants.SETRETRIEVERESENTCHATMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GET_ALL_USERS_INFO))
                    {
                    	getusersinfo=true;    
                    	setMessageType(XMLConstants.SETGETALLUSERSINFO);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_CONVERSATION))
                    {
                    	conversation=true;                      
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GET_CONVERSATION_MESSAGES))
                    {
                        getallmessages=true;
                        setMessageType(XMLConstants.SETGETALLCONVERSATIONMESSAGES);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GET_RECENT_CONVERSATION_MESSAGES))
                    {
                        getrecentmessages=true;
                        setMessageType(XMLConstants.SETGETRECENTCONVERSATIONMESSAGES);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_INSERT_MESSAGE_TO_CONVERSATION))
                    {
                        insertmessage=true;
                        setMessageType(XMLConstants.SETINSERTCONVERSATIONMESSAGE);
                    }
                    

                }

                public void setResultTextForNode(char[] ch, int start, int length)
                {
                    if (date) {
                        parsingResult.add(new String(ch,start,length));
                        date = false;
                        
                    }
                    else if (group) {
                        parsingResult.add(new String(ch,start,length));
                        group = false;
                       
                    }
                    else if (login) {
                        parsingResult.add(new String(ch,start,length));
                        login = false;
                       
                    }
                    else if (text) {
                        parsingResult.add(new String(ch,start,length));
                        text = false;
                    
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
            
            saxParser.parse(new InputSource(new StringReader(xmlDocument)),handlerXML);
          

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsingResult;
    }

    public int getXMLMessageType()
    {
        switch(messageType) {
            case XMLConstants.SETSENDCHATMESSAGE:
            {
                nullifyAllTags();
                return XMLConstants.SETSENDCHATMESSAGE;
            }
            case XMLConstants.SETRETRIEVEALLCHATMESSAGE:
            {
                nullifyAllTags();
                return XMLConstants.SETRETRIEVEALLCHATMESSAGE;
            }
            case XMLConstants.SETRETRIEVERESENTCHATMESSAGE:
            {
                nullifyAllTags();
                return XMLConstants.SETRETRIEVERESENTCHATMESSAGE;
            }
            case XMLConstants.SETGETALLUSERSINFO:
            {
            	nullifyAllTags();
                return XMLConstants.SETGETALLUSERSINFO;
            }
            case XMLConstants.SETGETALLCONVERSATIONMESSAGES:
            {
                nullifyAllTags();
                return XMLConstants.SETGETALLCONVERSATIONMESSAGES;
            }
            case XMLConstants.SETGETRECENTCONVERSATIONMESSAGES:
            {
                nullifyAllTags();
                return XMLConstants.SETGETRECENTCONVERSATIONMESSAGES;
            }
            case XMLConstants.SETINSERTCONVERSATIONMESSAGE:
            {
                nullifyAllTags();
                return XMLConstants.SETINSERTCONVERSATIONMESSAGE;
            }

        }
        return XMLConstants.RETURN;
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
