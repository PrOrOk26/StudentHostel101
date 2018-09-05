import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;


public class XMLParser {

	private boolean name = false;
    private boolean surname=false;
    private boolean date=false;
    
    private boolean garbage=false;
    private boolean bread=false;
    
    private boolean user=false;
    private boolean validation=false;
    private boolean login=false;
    private boolean password=false;
    private boolean group=false;
    
    private boolean registration=false;
    private boolean groupvalidation=false;
    private boolean groupcreation=false;
    private boolean groupinsertion=false;
    
    private boolean groupquit=false;
    
    private boolean insertgarbage=false;
    private boolean insertbread=false;
    
    private boolean retrievebread=false;
    private boolean retrievegarbage=false;
    
    
 
    
    private int xmlType;
    
    private boolean requestBreadGarbageType=false;
    
    private Logger log;
    

    public XMLParser()
    {
        log=Logger.getLogger(LoggingMXBean.class.getName());
        xmlType=-1;
    }

	private void setXmlType(int xmlType) {
		this.xmlType = xmlType;
	}

    public ArrayList<String> getParsingResultFromStream(String xmlDocument) {

        ArrayList<String> parsingResult=new ArrayList<>();

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
                    if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GARBAGE)) {
                        garbage = true;    
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_BREAD)) {
                        bread = true;                                
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_USER)) {
                        user = true;    
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_NAME)) {
                        name = true;                        
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_SURNAME)) {
                        surname = true;                     
                    }
                    else if (tagCatched.equalsIgnoreCase(XMLConstants.TAGS_DATE)) {
                        date = true;                       
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_LOGIN))
                    {
                    	login=true;
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_PASSWORD))
                    {
                    	password=true;
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP))
                    {
                    	group=true;
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_LOGIN_VALIDATION))
                    {
                    	validation=true;
                    	setXmlType(XMLConstants.SETVALIDATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_USER_REGISTRATION))
                    {
                    	registration=true;
                    	setXmlType(XMLConstants.SETREGISTRATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_VALIDATION))
                    {
                    	groupvalidation=true;
                    	setXmlType(XMLConstants.SETGROUPVALIDATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_CREATION))
                    {
                    	groupvalidation=true;
                    	setXmlType(XMLConstants.SETGROUPCREATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_ADD_USER))
                    {
                    	groupinsertion=true;
                    	setXmlType(XMLConstants.SETGROUPINVITATIONMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_GROUP_USER_QUIT))
                    {
                    	groupquit=true;
                    	setXmlType(XMLConstants.SETGROUPUSERQUITMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_INSERT_BREAD))
                    {
                    	insertbread=true;
                    	setXmlType(XMLConstants.SETINSERTBREADMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_INSERT_GARBAGE))
                    {
                    	insertgarbage=true;
                    	setXmlType(XMLConstants.SETINSERTGARBAGEMESSAGE);
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_REQUEST_BREAD_HISTORY))
                    {
                    	retrievebread=true;                      
                    }
                    else if(tagCatched.equalsIgnoreCase(XMLConstants.TAGS_REQUEST_GARBAGE_HISTORY))
                    {
                    	retrievegarbage=true;                        
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
                      
                    }
                    else if (surname) {
                    	parsingResult.add(new String(ch,start,length));
                        surname = false;
                        
                    }
                    else if (date) {
                    	parsingResult.add(new String(ch,start,length));
                        date = false;
                        
                    }                   
                    else if(login)
                    {
                    	parsingResult.add(new String(ch,start,length));
                        login = false;
                    }
                    else if(password)
                    {
                    	parsingResult.add(new String(ch,start,length));
                        password = false;
                    }
                    else if(group)
                    {
                    	parsingResult.add(new String(ch,start,length));
                    	group = false;
                    }
                    
                    //we process garbage/bread requests there.It depends on request type what we will receive
                    else if(requestBreadGarbageType)
                    {
                    	String buff=new String(ch,start,length);
                    	
                    	if(Integer.toString(XMLConstants.BREAD_GARBAGE_REQUEST_TYPE_ALL).equals(buff))
                    	{
                    		
                    	     if(retrievegarbage)
                    	     {
                    	    	 setXmlType(XMLConstants.SETGETGARBAGEHISTORYMESSAGEALL);
                    	     }
                    	     
                    	     else if(retrievebread)
                    	     {
                    	    	 setXmlType(XMLConstants.SETGETBREADHISTORYMESSAGEALL);
                    	     }
                    	     
                    	}
                    	else if(Integer.toString(XMLConstants.BREAD_GARBAGE_REQUEST_TYPE_RECENT).equals(buff))
                    	{
                    		
                    		if(retrievegarbage)
                    		{
                    			setXmlType(XMLConstants.SETGETGARBAGEHISTORYMESSAGERECENT);
                    		}
                    		
                    		else if(retrievebread)
                    		{
                    			setXmlType(XMLConstants.SETGETBREADHISTORYMESSAGERECENT);
                    		}
                    		
                    	}
                    	
                    	requestBreadGarbageType = false;
                    }
                    else if(registration)
                    {
                    	registration=false;
                    }
                    else if(garbage)
                    {                  	
                        garbage = false;
                    }
                    else if(bread)
                    {                 	
                        bread = false;
                    }
                    else if(validation)
                    {                 	
                    	validation = false;
                    }
                    else if(user)
                    {
                    	user=false;
                    }
                    else if(groupvalidation)
                    {
                    	groupvalidation=false;
                    }
                    else if(groupcreation)
                    {
                    	groupcreation=false;
                    }
                    else if(groupinsertion)
                    {
                    	groupinsertion=false;
                    }
                    else if(groupquit)
                    {
                    	groupquit=false;
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
        switch(xmlType)
        {
        case XMLConstants.SETVALIDATIONMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETVALIDATIONMESSAGE;
        }
        case XMLConstants.SETREGISTRATIONMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETREGISTRATIONMESSAGE;
        }
        case XMLConstants.SETGROUPVALIDATIONMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETGROUPVALIDATIONMESSAGE;
        }
        case XMLConstants.SETGROUPCREATIONMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETGROUPCREATIONMESSAGE;
        }
        case XMLConstants.SETGROUPINVITATIONMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETGROUPINVITATIONMESSAGE;
        }
        case XMLConstants.SETGROUPUSERQUITMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETGROUPUSERQUITMESSAGE;
        }
        case XMLConstants.SETGETBREADHISTORYMESSAGEALL:
        {
        	nullifyAllTags();
            return XMLConstants.SETGETBREADHISTORYMESSAGEALL;
        }
        case XMLConstants.SETGETBREADHISTORYMESSAGERECENT:
        {
        	nullifyAllTags();
            return XMLConstants.SETGETBREADHISTORYMESSAGERECENT;
        }
        case XMLConstants.SETGETGARBAGEHISTORYMESSAGEALL:
        {
        	nullifyAllTags();
            return XMLConstants.SETGETGARBAGEHISTORYMESSAGEALL;
        }
        case XMLConstants.SETGETGARBAGEHISTORYMESSAGERECENT:
        {
        	nullifyAllTags();
            return XMLConstants.SETGETGARBAGEHISTORYMESSAGERECENT;
        }
        case XMLConstants.SETINSERTBREADMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETINSERTBREADMESSAGE;
        }
        case XMLConstants.SETINSERTGARBAGEMESSAGE:
        {
        	nullifyAllTags();
            return XMLConstants.SETINSERTGARBAGEMESSAGE;
        }   
        }
        return XMLConstants.RETURN;
    }

    private void nullifyAllTags()
    {
        this.garbage=false;
        this.bread=false;
        this.date=false;
        this.name=false;
        this.surname=false;
        this.user=false;
        this.registration=false;
        this.groupvalidation=false;
        this.groupcreation=false;
        this.groupinsertion=false;
        this.groupquit=false;
        
        this.insertbread=false;
        this.insertgarbage=false;
        
        this.retrievebread=false;
        this.retrievegarbage=false;
        
        this.requestBreadGarbageType=false;
        
    }


}
