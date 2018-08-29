package com.applications.a306app.xml;

import com.applications.a306app.model.Message;
import com.applications.a306app.model.PrivateMessage;
import com.applications.a306app.model.User;
import com.applications.a306app.model.UsersDB;
import com.applications.a306app.utils.DateTimeUtils;

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

        public static String createSendChatMessagesXML(List<Message> elements)
        {
            Document doc=null;
            Iterator<Message> iterator=elements.iterator();
            try {

                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();

                Element sendchatmessage = doc.createElement("sendchatmessage");
                doc.appendChild(sendchatmessage);

                if (iterator.hasNext()) {
                    Message buff = iterator.next();

                    Element sender = doc.createElement("login");
                    sender.appendChild(doc.createTextNode(buff.getSender().getLogin()));
                    sendchatmessage.appendChild(sender);

                    Element text = doc.createElement("text");

                    text.appendChild(doc.createTextNode(buff.getMessage()));
                    sendchatmessage.appendChild(text);

                    Element date = doc.createElement("date");

                    date.appendChild(doc.createTextNode(DateTimeUtils.convertDateTimeToString(buff.getDate())));
                    sendchatmessage.appendChild(date);
                }

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

    public static String createRetrieveAllChatMessagesXML() {

        Document doc=null;
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element sendchatmessage = doc.createElement("retrieveallchatmessages");
            doc.appendChild(sendchatmessage);


            printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String createRetrieveRecentChatMessagesXML() {

        Document doc=null;
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element retreiveresentchatmessage = doc.createElement("retrieveresentchatmessages");
            doc.appendChild(retreiveresentchatmessage);

            Element hostLogin = doc.createElement("login");
            hostLogin.appendChild(doc.createTextNode(UsersDB.getHostUser().getLogin()));
            retreiveresentchatmessage.appendChild(hostLogin);

            printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String createGetAllUsersConversationsXML()
    {

        Document doc=null;
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element getallusersinfo = doc.createElement(XMLConstants.TAGS_GET_ALL_USERS_INFO);
            doc.appendChild(getallusersinfo);

            Element hostlogin=doc.createElement(XMLConstants.TAGS_LOGIN);
            hostlogin.appendChild(doc.createTextNode(UsersDB.getHostUser().getLogin()));
            getallusersinfo.appendChild(hostlogin);

            printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);

    }

    public static String createGetAllConversationMessagesXML(int conversationId)
    {
        Document doc=null;
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element getallmessages = doc.createElement(XMLConstants.TAGS_GET_CONVERSATION_MESSAGES);
            doc.appendChild(getallmessages);

            Element conversation=doc.createElement(XMLConstants.TAGS_CONVERSATION);
            conversation.appendChild(doc.createTextNode(Integer.toString(conversationId)));
            getallmessages.appendChild(conversation);

            printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String createGetRecentConversationMessagesXML(int conversationId)
    {
        Document doc=null;
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element getrecentmessages = doc.createElement(XMLConstants.TAGS_GET_RECENT_CONVERSATION_MESSAGES);
            doc.appendChild(getrecentmessages);

            Element conversation=doc.createElement(XMLConstants.TAGS_CONVERSATION);
            conversation.appendChild(doc.createTextNode(Integer.toString(conversationId)));
            getrecentmessages.appendChild(conversation);

            Element login=doc.createElement(XMLConstants.TAGS_LOGIN);
            login.appendChild(doc.createTextNode(UsersDB.getHostUser().getLogin()));
            getrecentmessages.appendChild(login);

            printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String createInsertConversationMessageXML(PrivateMessage messageToInsert)
    {
        Document doc=null;
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element insertmessage = doc.createElement(XMLConstants.TAGS_INSERT_MESSAGE_TO_CONVERSATION);
            doc.appendChild(insertmessage);

            Element date=doc.createElement(XMLConstants.TAGS_DATE);
            date.appendChild(doc.createTextNode(DateTimeUtils.convertDateTimeToString(messageToInsert.getDate())));
            insertmessage.appendChild(date);

            Element text=doc.createElement(XMLConstants.TAGS_TEXT);
            text.appendChild(doc.createTextNode(messageToInsert.getMessage()));
            insertmessage.appendChild(text);

            Element login=doc.createElement(XMLConstants.TAGS_LOGIN);
            login.appendChild(doc.createTextNode(messageToInsert.getSender().getLogin()));
            insertmessage.appendChild(login);

            Element conversation=doc.createElement(XMLConstants.TAGS_CONVERSATION);
            conversation.appendChild(doc.createTextNode(Integer.toString(messageToInsert.getConversationId())));
            insertmessage.appendChild(conversation);

            printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }
}
