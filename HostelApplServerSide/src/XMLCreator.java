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
	
	enum COMMON_RESPONCE{XML_LOGIN_VALIDATION,XML_USER_REGISTRATION,XML_GARBAGE,XML_BREAD,XML_GROUP_VALIDATION,XML_GROUP_CREATION,XML_INSERTION_TO_GROUP,XML_QUIT_FROM_GROUP,XML_SEND_MESSAGE};

	public class XMLCreator {
	   
	   public static String createDataXML(List<EntityDao> elements,COMMON_RESPONCE type,Iterator<EntityDao> queryListIterator)
	   {
			  Document doc=null;       
		   try {
			   
			   DocumentBuilderFactory dbFactory =
				         DocumentBuilderFactory.newInstance();
				         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				         doc = dBuilder.newDocument();
				        
			   
			   switch(type)
			   {
			   case XML_GARBAGE:
			   {
				   buildGarbageXML(doc,elements,queryListIterator);
				   break;
			   }
			   case XML_BREAD:
			   {
				   buildBreadXML(doc,elements,queryListIterator);
				   break;
			   }			
			   }
		         		             
		       printXMLtoConsole(doc);
		         
		               
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   return convertXMLDocToString(doc);	
		   
		  }
	   
	   public static String createResponceXML(List<String> data,COMMON_RESPONCE type)
	   {
		   Document doc=null;       
		   try {
			   
			   DocumentBuilderFactory dbFactory =
				         DocumentBuilderFactory.newInstance();
				         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				         doc = dBuilder.newDocument();
				        
			   
			   switch(type)
			   {
			   case XML_LOGIN_VALIDATION:
			   {
				   buildValidationResponceXML(doc,data);
				   break;
			   }
			   case XML_USER_REGISTRATION:
			   {
				   buildRegistrationResponceXML(doc,data);
				   break;
			   }
			   case XML_GROUP_VALIDATION:
			   {
				   buildGroupValidationResponceXML(doc,data);
				   break;
			   }
			   case XML_GROUP_CREATION:
			   {
				   buildGroupCreationResponceXML(doc,data);
				   break;
			   }
			   case XML_INSERTION_TO_GROUP:
			   {
				   buildGroupAddUserResponceXML(doc,data);
				   break;
			   }
			   case XML_QUIT_FROM_GROUP:
			   {
				   buildQuitGroupResponceXML(doc,data);
				   break;
			   }
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
	   
	   
	   
	   private static void buildGarbageXML(Document doc,List<EntityDao> elements,Iterator<EntityDao> queryListIterator)
	   {
		   Element rootElement = doc.createElement("garbage");
	         doc.appendChild(rootElement);

	         Element user = doc.createElement("user");
	         rootElement.appendChild(user);
	         
	         Element name=doc.createElement("name");
	         if(queryListIterator.hasNext())
	         {
	        	 EntityDao buff=queryListIterator.next();
	        	 
	   //     name.appendChild(doc.createTextNode(buff.getName()));	
	         user.appendChild(name);
	         
	         Element surname=doc.createElement("surname");		
	         if(queryListIterator.hasNext())
	   //      surname.appendChild(doc.createTextNode(buff.getSurname()));
	         user.appendChild(surname);
	         
	         Element date=doc.createElement("date");		         
	         if(queryListIterator.hasNext())
	   //     	 date.appendChild(doc.createTextNode(buff.getDate()));
	         user.appendChild(date);
	         }
	   }
	   
	   private static void buildBreadXML(Document doc,List<EntityDao> elements,Iterator<EntityDao> queryListIterator)
	   {
		  
		   Element rootElement = doc.createElement("bread");
	         doc.appendChild(rootElement);

	         Element user = doc.createElement("user");
	         rootElement.appendChild(user);
	         
	         Element name=doc.createElement("name");
	         if(queryListIterator.hasNext())
	         {
	        	 
	        EntityDao buff=queryListIterator.next();
	        	 
	        // name.appendChild(doc.createTextNode(buff.getName()));	
	         user.appendChild(name);
	         
	         Element surname=doc.createElement("surname");		
	         
	       //  surname.appendChild(doc.createTextNode(buff.getSurname()));
	         user.appendChild(surname);
	         
	         Element date=doc.createElement("date");		         
	         
	       // 	 date.appendChild(doc.createTextNode(buff.getDate()));
	         user.appendChild(date);
	         }
	   }
	   
	   private static void buildValidationResponceXML(Document doc,List<String> elements)
	   {
		   
		   Iterator<String> queryListIterator=elements.iterator();
		   Element rootElement = doc.createElement("validation");	
	               
	         if(queryListIterator.hasNext())
	         {
	        	 rootElement.appendChild(doc.createTextNode(queryListIterator.next()));
	         }	         	         
			 doc.appendChild(rootElement);
	   }
	   
	   private static void buildRegistrationResponceXML(Document doc,List<String> elements)
	   {
		   Iterator<String> queryListIterator=elements.iterator();
		   Element rootElement = doc.createElement("registration");	
	               
	         if(queryListIterator.hasNext())
	         {
	        	 rootElement.appendChild(doc.createTextNode(queryListIterator.next()));
	         }	         	         
			 doc.appendChild(rootElement);
	   }
	   
	   private static void buildGroupValidationResponceXML(Document doc,List<String> elements)
	   {
		   Iterator<String> queryListIterator=elements.iterator();
		   Element rootElement = doc.createElement("groupvalidation");	
	               
	         if(queryListIterator.hasNext())
	         {
	        	 rootElement.appendChild(doc.createTextNode(queryListIterator.next()));
	         }	         	         
			 doc.appendChild(rootElement);
	   }
	   
	   private static void buildGroupCreationResponceXML(Document doc,List<String> elements)
	   {
		   Iterator<String> queryListIterator=elements.iterator();
		   Element rootElement = doc.createElement("groupcreation");	
	               
	         if(queryListIterator.hasNext())
	         {
	        	 rootElement.appendChild(doc.createTextNode(queryListIterator.next()));
	         }	         	         
			 doc.appendChild(rootElement);
	   }
	   
	   private static void buildGroupAddUserResponceXML(Document doc,List<String> elements)
	   {
		   Iterator<String> queryListIterator=elements.iterator();
		   Element rootElement = doc.createElement("groupinsert");	
	               
	         if(queryListIterator.hasNext())
	         {
	        	 rootElement.appendChild(doc.createTextNode(queryListIterator.next()));
	         }	         	         
			 doc.appendChild(rootElement);
	   }
	   
	   private static void buildQuitGroupResponceXML(Document doc,List<String> elements)
	   {
		   Iterator<String> queryListIterator=elements.iterator();
		   Element rootElement = doc.createElement("groupquit");	
	               
	         if(queryListIterator.hasNext())
	         {
	        	 rootElement.appendChild(doc.createTextNode(queryListIterator.next()));
	         }	         	         
			 doc.appendChild(rootElement);
	   }
	   
	   public static String createGetGarbageXML(List<UserActivity> elements)
       {
           Document doc=null;
           Iterator<UserActivity> iterator=elements.iterator();
           try {

               DocumentBuilderFactory dbFactory =
                       DocumentBuilderFactory.newInstance();
               DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
               doc = dBuilder.newDocument();

               Element getGarbage = doc.createElement(XMLConstants.TAGS_REQUEST_GARBAGE_HISTORY);
               doc.appendChild(getGarbage); 
               
               
               UserActivity buff=null;
             
               while (iterator.hasNext())
               {
            	   buff=iterator.next();
            	   Element garbage = doc.createElement(XMLConstants.TAGS_GARBAGE);
            	   
            	   Element login = doc.createElement(XMLConstants.TAGS_LOGIN);
            	   login.appendChild(doc.createTextNode(buff.getPerformerLogin()));
            	   garbage.appendChild(login);
            	   
            	   Element name = doc.createElement(XMLConstants.TAGS_NAME);
            	   name.appendChild(doc.createTextNode(buff.getPerformerName()));
            	   garbage.appendChild(name);
            	   
            	   Element surname = doc.createElement(XMLConstants.TAGS_SURNAME);
            	   surname.appendChild(doc.createTextNode(buff.getPerformerSurname()));
            	   garbage.appendChild(surname);
            	   
            	   Element date = doc.createElement(XMLConstants.TAGS_DATE);
            	   date.appendChild(doc.createTextNode(buff.getDate()));
            	   garbage.appendChild(date);
            	   
            	   getGarbage.appendChild(garbage);
               	
               }

               printXMLtoConsole(doc);


           } catch (Exception e) {
               e.printStackTrace();
           }
           return convertXMLDocToString(doc);


       }
	   
	   public static String createGetBreadXML(List<UserActivity> elements)
       {
		   Document doc=null;
           Iterator<UserActivity> iterator=elements.iterator();
           try {

               DocumentBuilderFactory dbFactory =
                       DocumentBuilderFactory.newInstance();
               DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
               doc = dBuilder.newDocument();

               Element getBread = doc.createElement(XMLConstants.TAGS_REQUEST_BREAD_HISTORY);
               doc.appendChild(getBread); 
               
               
               UserActivity buff=null;
             
               while (iterator.hasNext())
               {
            	   buff=iterator.next();
            	   Element bread = doc.createElement(XMLConstants.TAGS_BREAD);
            	   
            	   Element login = doc.createElement(XMLConstants.TAGS_LOGIN);
            	   login.appendChild(doc.createTextNode(buff.getPerformerLogin()));
            	   bread.appendChild(login);
            	   
            	   Element name = doc.createElement(XMLConstants.TAGS_NAME);
            	   name.appendChild(doc.createTextNode(buff.getPerformerName()));
            	   bread.appendChild(name);
            	   
            	   Element surname = doc.createElement(XMLConstants.TAGS_SURNAME);
            	   surname.appendChild(doc.createTextNode(buff.getPerformerSurname()));
            	   bread.appendChild(surname);
            	   
            	   Element date = doc.createElement(XMLConstants.TAGS_DATE);
            	   date.appendChild(doc.createTextNode(buff.getDate()));
            	   bread.appendChild(date);
            	   
            	   getBread.appendChild(bread);
               	
               }

               printXMLtoConsole(doc);


           } catch (Exception e) {
               e.printStackTrace();
           }
           return convertXMLDocToString(doc);

       }
	   
	   public static String createInsertGarbageResponceXML(String responce)
       {
           Document doc=null;         
           try {

               DocumentBuilderFactory dbFactory =
                       DocumentBuilderFactory.newInstance();
               DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
               doc = dBuilder.newDocument();

               Element insertgarbage = doc.createElement(XMLConstants.TAGS_INSERT_GARBAGE);
               insertgarbage.appendChild(doc.createTextNode(responce));
               doc.appendChild(insertgarbage);                  

               printXMLtoConsole(doc);


           } catch (Exception e) {
               e.printStackTrace();
           }
           return convertXMLDocToString(doc);


       }
	   
	   
	   public static String createInsertBreadResponceXML(String responce)
       {
		   Document doc=null;         
           try {

               DocumentBuilderFactory dbFactory =
                       DocumentBuilderFactory.newInstance();
               DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
               doc = dBuilder.newDocument();

               Element insertbread = doc.createElement(XMLConstants.TAGS_INSERT_BREAD);
               insertbread.appendChild(doc.createTextNode(responce));
               doc.appendChild(insertbread);                  

               printXMLtoConsole(doc);


           } catch (Exception e) {
               e.printStackTrace();
           }
           return convertXMLDocToString(doc);


       }
	   
	 
	   
	}
