
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLMessagesCreator {

        public static String createSendChatMessagesXML(List<String> elements)
        {
            Document doc=null;
            Iterator<String> iterator=elements.iterator();
            try {

                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                Element sendchatmessage = doc.createElement("sendchatmessage");
                doc.appendChild(sendchatmessage);         

                Element text = doc.createElement("text");
                while (iterator.hasNext())
                	text.appendChild(doc.createTextNode(iterator.next()));
                sendchatmessage.appendChild(text);

                printXMLtoConsole(doc);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertXMLDocToString(doc);


        }
        
        public static String createReceiveAllChatMessagesXML(List<Message> elements)
        {
            Document doc=null;
            Iterator<Message> iterator=elements.iterator();
            try {

                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                Element sendchatmessage = doc.createElement("retrieveallchatmessages");
                doc.appendChild(sendchatmessage);   
                
                while (iterator.hasNext())
                {
                	Message buff=iterator.next();
                Element login = doc.createElement("login");
                
                	login.appendChild(doc.createTextNode(buff.getSender().getLogin()));
                sendchatmessage.appendChild(login);

                Element text = doc.createElement("text");
                
                	text.appendChild(doc.createTextNode(buff.getMessage()));
                sendchatmessage.appendChild(text);
                
                Element date = doc.createElement("date");
               
                    date.appendChild(doc.createTextNode(buff.getDate()));
                sendchatmessage.appendChild(date);
                }

                printXMLtoConsole(doc);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertXMLDocToString(doc);


        }
             
        
        public static String  createReceiveAllRecentChatMessagesXML(List<Message> elements)
        {
            Document doc=null;
            Iterator<Message> iterator=elements.iterator();
            try {

                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                Element sendchatmessage = doc.createElement("retrieveresentchatmessages");
                doc.appendChild(sendchatmessage);   
                
                while (iterator.hasNext())
                {
                	Message buff=iterator.next();
                Element login = doc.createElement("login");
                
                	login.appendChild(doc.createTextNode(buff.getSender().getLogin()));
                sendchatmessage.appendChild(login);

                Element text = doc.createElement("text");
                
                	text.appendChild(doc.createTextNode(buff.getMessage()));
                sendchatmessage.appendChild(text);
                
                Element date = doc.createElement("date");
               
                    date.appendChild(doc.createTextNode(buff.getDate()));
                sendchatmessage.appendChild(date);
                }

                printXMLtoConsole(doc);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertXMLDocToString(doc);


        }
        
        
        public static String createGetAllUsersConversationsResponce(List<String> allUsers)
 	   {
 		   
 		   Document doc=null;
            Iterator<String> iterator=allUsers.iterator();
            try {

                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                Element getalluser = doc.createElement(XMLConstants.TAGS_GET_ALL_USERS_INFO);
                doc.appendChild(getalluser); 
                                               
                while (iterator.hasNext())
                {           	   
             	   Element user = doc.createElement(XMLConstants.TAGS_USER);          	              	 
             	   
             	   Element conversation =doc.createElement(XMLConstants.TAGS_CONVERSATION);
             	   conversation.appendChild(doc.createTextNode(iterator.next()));
             	   user.appendChild(conversation);
             	   
             	   Element login = doc.createElement(XMLConstants.TAGS_LOGIN);
             	   login.appendChild(doc.createTextNode(iterator.next()));
             	   user.appendChild(login);
                       	   
             	   getalluser.appendChild(user);
                	
                }

                printXMLtoConsole(doc);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertXMLDocToString(doc);
            
 	   }
        
        
        public static String createReceiveAllConversationMessagesXML(List<PrivateMessage> elements)
        {
            Document doc=null;
            Iterator<PrivateMessage> iterator=elements.iterator();
            try {

                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                Element sendchatmessage = doc.createElement(XMLConstants.TAGS_GET_CONVERSATION_MESSAGES);
                doc.appendChild(sendchatmessage);   
                
                while (iterator.hasNext())
                {
                	PrivateMessage buff=iterator.next();
                	
                	Element conversation = doc.createElement(XMLConstants.TAGS_CONVERSATION);
                       
                	conversation.appendChild(doc.createTextNode(Integer.toString(buff.getConversationId())));
                    sendchatmessage.appendChild(conversation);
                    
                    Element text = doc.createElement(XMLConstants.TAGS_TEXT);
                    
                	text.appendChild(doc.createTextNode(buff.getMessage()));
                    sendchatmessage.appendChild(text);
                       
                    Element login = doc.createElement(XMLConstants.TAGS_LOGIN);
                
                	login.appendChild(doc.createTextNode(buff.getSender().getLogin()));
                    sendchatmessage.appendChild(login);
                 
                
                    Element date = doc.createElement(XMLConstants.TAGS_DATE);
               
                    date.appendChild(doc.createTextNode(buff.getDate()));
                    sendchatmessage.appendChild(date);
                }

                printXMLtoConsole(doc);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertXMLDocToString(doc);


        }
             
        
        public static String  createReceiveAllRecentConversationMessagesXML(List<PrivateMessage> elements)
        {
        	  Document doc=null;
              Iterator<PrivateMessage> iterator=elements.iterator();
              try {

                  DocumentBuilderFactory dbFactory =
                          DocumentBuilderFactory.newInstance();
                  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                  doc = dBuilder.newDocument();

                  Element sendchatmessage = doc.createElement(XMLConstants.TAGS_GET_RECENT_CONVERSATION_MESSAGES);
                  doc.appendChild(sendchatmessage);   
                  
                  while (iterator.hasNext())
                  {
                  	PrivateMessage buff=iterator.next();
                  	
                  	Element conversation = doc.createElement(XMLConstants.TAGS_CONVERSATION);
                         
                  	conversation.appendChild(doc.createTextNode(Integer.toString(buff.getConversationId())));
                      sendchatmessage.appendChild(conversation);
                      
                      Element text = doc.createElement(XMLConstants.TAGS_TEXT);
                      
                  	text.appendChild(doc.createTextNode(buff.getMessage()));
                      sendchatmessage.appendChild(text);
                         
                      Element login = doc.createElement(XMLConstants.TAGS_LOGIN);
                  
                  	login.appendChild(doc.createTextNode(buff.getSender().getLogin()));
                      sendchatmessage.appendChild(login);
                   
                  
                      Element date = doc.createElement(XMLConstants.TAGS_DATE);
                 
                      date.appendChild(doc.createTextNode(buff.getDate()));
                      sendchatmessage.appendChild(date);
                  }

                  printXMLtoConsole(doc);


              } catch (Exception e) {
                  e.printStackTrace();
              }
              return convertXMLDocToString(doc);

        }
        
        
        public static String createInsertConversationMessagesXML(List<String> elements)
        {
            Document doc=null;
            Iterator<String> iterator=elements.iterator();
            try {

                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                Element sendmessage = doc.createElement(XMLConstants.TAGS_INSERT_MESSAGE_TO_CONVERSATION);
                doc.appendChild(sendmessage);         

                Element text = doc.createElement(XMLConstants.TAGS_TEXT);
                while (iterator.hasNext())
                	text.appendChild(doc.createTextNode(iterator.next()));
                sendmessage.appendChild(text);

                printXMLtoConsole(doc);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertXMLDocToString(doc);


        }
        
        
        


        private static String convertXMLDocToString(Document doc)
        {
            try {
                StringWriter sw = new StringWriter();
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "no");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                transformer.transform(new DOMSource(doc), new StreamResult(sw));
                return sw.toString();
            } catch (Exception ex) {
                throw new RuntimeException("Error converting to String", ex);
            }
        }

        private static void printXMLtoConsole(Document doc)
        {
            try
            {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult consoleResult = new StreamResult(System.out);
                transformer.transform(source, consoleResult);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

}
