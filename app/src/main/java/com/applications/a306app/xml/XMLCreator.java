package com.applications.a306app.xml;

import com.applications.a306app.model.User;
import com.applications.a306app.model.UserActivity;
import com.applications.a306app.utils.DateTimeUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLCreator {

    public static String createGarbageXML(List<String> elements) {
        Document doc = null;
        Iterator<String> iterator = elements.iterator();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("garbage");
            doc.appendChild(rootElement);

            Element user = doc.createElement("user");
            rootElement.appendChild(user);

            Element name = doc.createElement("name");
            if (iterator.hasNext())
                name.appendChild(doc.createTextNode(iterator.next()));
            user.appendChild(name);

            Element surname = doc.createElement("surname");
            if (iterator.hasNext())
                surname.appendChild(doc.createTextNode(iterator.next()));
            user.appendChild(surname);

            Element date = doc.createElement("date");

            //iterate through the list to set all text nodes
            if (iterator.hasNext())
                date.appendChild(doc.createTextNode(iterator.next()));
            user.appendChild(date);

            printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);


    }

    public static String createUserValidationXML(List<User> data) {
        Document doc = null;
        Iterator<User> iterator = data.iterator();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement("validation");
            doc.appendChild(validationElement);

            while (iterator.hasNext()) {
                User buff = iterator.next();

                Element rootElement = doc.createElement("user");
                validationElement.appendChild(rootElement);

                Element login = doc.createElement("login");
                login.appendChild(doc.createTextNode(buff.getLogin()));
                rootElement.appendChild(login);

                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode(buff.getPassword()));
                rootElement.appendChild(password);
            }

            //printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String createUserRegistrationXML(List<User> data) {
        Document doc = null;
        Iterator<User> iterator = data.iterator();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement("registration");
            doc.appendChild(validationElement);

            while (iterator.hasNext()) {
                User buff = iterator.next();

                Element rootElement = doc.createElement("user");
                validationElement.appendChild(rootElement);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(buff.getName()));
                rootElement.appendChild(name);

                Element surname = doc.createElement("surname");
                surname.appendChild(doc.createTextNode(buff.getSurname()));
                rootElement.appendChild(surname);

                Element login = doc.createElement("login");
                login.appendChild(doc.createTextNode(buff.getLogin()));
                rootElement.appendChild(login);

                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode(buff.getPassword()));
                rootElement.appendChild(password);
            }

            //printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String createUserGroupValidationXML(List<User> data) {
        Document doc = null;
        Iterator<User> iterator = data.iterator();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement("groupvalidation");
            doc.appendChild(validationElement);

            User buff = iterator.next();

            Element rootElement = doc.createElement("user");
            validationElement.appendChild(rootElement);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(buff.getLogin()));
            rootElement.appendChild(login);

            //printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    private static String convertXMLDocToString(Document doc) {
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

    private static void printXMLtoConsole(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createUserGroupCreationXML(List<User> data) {
        Document doc = null;
        Iterator<User> iterator = data.iterator();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement("groupcreation");
            doc.appendChild(validationElement);

            User buff = iterator.next();

            Element rootElement = doc.createElement("user");
            validationElement.appendChild(rootElement);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(buff.getLogin()));
            rootElement.appendChild(login);

            Element group = doc.createElement("group");
            group.appendChild(doc.createTextNode(buff.getGroup()));
            rootElement.appendChild(group);

            //printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String insertUserToGroupXML(List<User> data) {
        Document doc = null;
        Iterator<User> iterator = data.iterator();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement("groupinsert");
            doc.appendChild(validationElement);

            User buff = iterator.next();

            Element rootElement = doc.createElement("user");
            validationElement.appendChild(rootElement);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(buff.getLogin()));
            rootElement.appendChild(login);

            Element group = doc.createElement("group");
            group.appendChild(doc.createTextNode(buff.getGroup()));
            rootElement.appendChild(group);

            //printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }

    public static String quitUserFromGroup(List<User> data) {
        Document doc = null;
        Iterator<User> iterator = data.iterator();
        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement("groupquit");
            doc.appendChild(validationElement);

            User buff = iterator.next();

            Element rootElement = doc.createElement("user");
            validationElement.appendChild(rootElement);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(buff.getLogin()));
            rootElement.appendChild(login);

            Element group = doc.createElement("group");
            group.appendChild(doc.createTextNode(buff.getGroup()));
            rootElement.appendChild(group);

            //printXMLtoConsole(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);
    }


    public static String createRetrieveBreadXML(int requestType,String requesterLogin) {

        Document doc = null;

        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement(XMLConstants.TAGS_REQUEST_BREAD_HISTORY);
            doc.appendChild(validationElement);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(requesterLogin));
            validationElement.appendChild(login);

            Element type = doc.createElement("requesttype");
            type.appendChild(doc.createTextNode( Integer.toString(requestType) ) );
            validationElement.appendChild(type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);

    }


    public static String createRetrieveGarbageXML(int requestType,String requesterLogin) {

        Document doc = null;

        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement(XMLConstants.TAGS_REQUEST_GARBAGE_HISTORY);
            doc.appendChild(validationElement);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(requesterLogin));
            validationElement.appendChild(login);

            Element type = doc.createElement("requesttype");
            type.appendChild(doc.createTextNode( Integer.toString(requestType) ) );
            validationElement.appendChild(type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);

    }

    public static String createInsertGarbageXML(UserActivity activity) {

        Document doc = null;

        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement(XMLConstants.TAGS_INSERT_GARBAGE);
            doc.appendChild(validationElement);

            Element garbage = doc.createElement(XMLConstants.TAGS_GARBAGE);
            validationElement.appendChild(garbage);

            Element garbageLogin = doc.createElement(XMLConstants.TAGS_LOGIN);
            garbageLogin.appendChild(doc.createTextNode(activity.getPerformerLogin()));
            garbage.appendChild(garbageLogin);

            Element garbageDate = doc.createElement(XMLConstants.TAGS_DATE);
            garbageDate.appendChild(doc.createTextNode(DateTimeUtils.convertDateTimeToString(activity.getDate())));
            garbage.appendChild(garbageDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);

    }

    public static String createInsertBreadXML(UserActivity activity) {

        Document doc = null;

        try {

            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element validationElement = doc.createElement(XMLConstants.TAGS_INSERT_BREAD);
            doc.appendChild(validationElement);

            Element garbage = doc.createElement(XMLConstants.TAGS_BREAD);
            validationElement.appendChild(garbage);

            Element garbageLogin = doc.createElement(XMLConstants.TAGS_LOGIN);
            garbageLogin.appendChild(doc.createTextNode(activity.getPerformerLogin()));
            garbage.appendChild(garbageLogin);

            Element garbageDate = doc.createElement(XMLConstants.TAGS_DATE);
            garbageDate.appendChild(doc.createTextNode(DateTimeUtils.convertDateTimeToString(activity.getDate())));
            garbage.appendChild(garbageDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertXMLDocToString(doc);

    }
}
