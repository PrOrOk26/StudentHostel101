
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class CustomServer {
	
	private int[] ports;
	private String host_name;
	private ServerSocket serverSocket;
	private Logger log;
	private static int numberOfClients;
	private static Map<User,Socket> listeningForReceivedChatMessagesSocketMap=new HashMap<>();
	private static Map<User,Socket> listeningForReceivedPrivateMessagesSocketMap=new HashMap<>();
	
	public static Map<User, Socket> getListeningForReceivedPrivateMessagesSocketMap() {
		return listeningForReceivedPrivateMessagesSocketMap;
	}

	public static Map<User,Socket> getListeningForReceivedMessagesSocketMap() {
		return listeningForReceivedChatMessagesSocketMap;
	}

	public static void main(String[] args)
	{
		
		CustomServer customServer=new CustomServer(new int[]{4467},"306Server");
		customServer.runServer();
		
	}
	
	public CustomServer(int[] ports,String host_name)
	{
		this.ports=ports;
		this.host_name=host_name;
	}
	
	
	public void runServer()
	{
		
		log=Logger.getLogger(LoggingMXBean.class.getName());
		numberOfClients=0;
		
		try {
			    serverSocket= new ServerSocket(ports[0]);
			    log.info("Server port:"+Integer.toString(serverSocket.getLocalPort()));
	            
	            while(true)
	            {            	
	            	new Thread(new ServeClient(serverSocket.accept())).start();
	            }
		          	         
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + ports[0] + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
		finally {
			if(serverSocket!=null)
			{
				try
				{
					serverSocket.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			
		}
		
}
		
	
    class ServeClient implements Runnable
    {		
		private Socket connection;
		private String inputLine;
		private String outputLine;	
		private List<String> parsingResult;
		private XMLParser myParser;
		private XMLMessagesParser myMessageParser;	
		
		private int toTestMessageType;
		
        public ServeClient(Socket connection) {
			
			this.connection = connection;	
			myParser=new XMLParser();
			log.info("Client"+Integer.toString(++numberOfClients)+"accepted!");
		}
        
        
    	    	
		public void run() {
			
			try {
			PrintWriter out =
	                new PrintWriter(connection.getOutputStream());
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(connection.getInputStream()));	       	       	            	

	        if((inputLine = in.readLine()) != null) {
	        	
	        	log.info(inputLine);
	                
	        	parsingResult=myParser.getParsingResultFromStream(inputLine);
	        	switch(myParser.getXMLMessageType())
	        	{
	        	case XMLConstants.SETVALIDATIONMESSAGE:
	        	{
	        		try {
	        			
	        		User userBuff=new User();
	        		userBuff.setLogin(parsingResult.get(0));
	        		userBuff.setPassword(parsingResult.get(1));
	        		
	        		UserDao user=new UserDao();
	        		
	        		ArrayList<String> result=new ArrayList<String>();
        			
	        		if(user.validateUser(userBuff))
	        		{        	        			
	        			result.add(XMLConstants.TAGS_LOGIN_VALIDATION_RESPONCE_ALLOWED);
	        			
	        		    outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_LOGIN_VALIDATION);
	        		}
	        		else
	        		{
                        result.add(XMLConstants.TAGS_LOGIN_VALIDATION_RESPONCE_FAILED);
	        			
	        		    outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_LOGIN_VALIDATION);
	        		}
	        		
	        		log.info("Result of XMLCreator for validation:\n");
	        		log.info(outputLine);
	        		out.println(outputLine);
	        		out.flush();
	        	}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	        		break;
	        	}
	        	case XMLConstants.SETREGISTRATIONMESSAGE:
	        	{
	        		try {
	        			
		        		User userBuff=new User();
		        		userBuff.setName(parsingResult.get(0));
		        		userBuff.setSurname(parsingResult.get(1));
		        		userBuff.setLogin(parsingResult.get(2));
		        		userBuff.setPassword(parsingResult.get(3));
		        	
		        		
		        		UserDao user=new UserDao();
		        		
		        		ArrayList<String> result=new ArrayList<String>();
	        			
		      		if(user.insert(userBuff))
		        		{        	        			
		        			result.add(XMLConstants.TAGS_USER_REGISTRATION_SUCCESS);
		        			
		        		    outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_USER_REGISTRATION);
		        		}
		        		else
		        		{
	                        result.add(XMLConstants.TAGS_USER_REGISTRATION_FAILED);
		        			
		        		    outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_USER_REGISTRATION);
		        		}
		        		
		        		log.info("Result of XMLCreator for registration:\n");
		        		log.info(outputLine);
		        		out.println(outputLine);
		        		out.flush();
		        	}
		        		catch(SQLException e)
		        		{
		        			e.printStackTrace();
		        		}
	        		break;
	        	}
	        	case XMLConstants.SETGROUPVALIDATIONMESSAGE:
	        	{
	        		try
	        		{
	        		String responce=UserDao.validateUserGroup(parsingResult);	        
	        		log.info("Result of XMLCreator for group validation:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETGROUPCREATIONMESSAGE:
	        	{
	        		try
	        		{
	        		String responce=UserDao.createUserGroup(parsingResult);
	        		log.info("Result of XMLCreator for group creation:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETGROUPUSERQUITMESSAGE:
	        	{
	        		try
	        		{
	        		String responce=UserDao.deleteUserFromGroup(parsingResult);
	        		log.info("Result of XMLCreator for group QUIT:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETGROUPINVITATIONMESSAGE:
	        	{
	        		try
	        		{
	        		String responce=UserDao.insertUserToGroup(parsingResult);
	        		log.info("Result of XMLCreator for group invitation:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETGETGARBAGEHISTORYMESSAGEALL:
	        	{
	        		try
	        		{
	        		String responce=UserActivityDao.getAllGarbage(parsingResult);
	        		log.info("Result of XMLCreator for all garbage request:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETGETGARBAGEHISTORYMESSAGERECENT:
	        	{
	        		try
	        		{
	        		String responce=UserActivityDao.getRecentGarbage(parsingResult);
	        		log.info("Result of XMLCreator for recent garbage request:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETGETBREADHISTORYMESSAGEALL:
	        	{
	        		try
	        		{
	        		String responce=UserActivityDao.getAllBread(parsingResult);
	        		log.info("Result of XMLCreator for all bread history:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETGETBREADHISTORYMESSAGERECENT:
	        	{
	        		try
	        		{
	        		String responce=UserActivityDao.getRecentBread(parsingResult);
	        		log.info("Result of XMLCreator for resent bread history:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETINSERTBREADMESSAGE:
	        	{
	        		try
	        		{
	        		String responce=UserActivityDao.insertBread(parsingResult);
	        		log.info("Result of XMLCreator for inserting bread:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}
	        	case XMLConstants.SETINSERTGARBAGEMESSAGE:
	        	{
	        		try
	        		{
	        		String responce=UserActivityDao.insertGarbage(parsingResult);
	        		log.info("Result of XMLCreator for inserting garbage:\n");
	        		log.info(responce);
	       		    out.println(responce);
	        		out.flush();
	        		}
	        		catch(SQLException e)
	        		{
	        			e.printStackTrace();
	        		}
	       		break;
	        	}	        
	        	case XMLConstants.RETURN:
	        	{
	        		myMessageParser=new XMLMessagesParser();
	        		parsingResult=this.myMessageParser.getParsingResultFromStream(inputLine);
	        		
	        		toTestMessageType=myMessageParser.getXMLMessageType();
	        		
	        		switch(myMessageParser.getXMLMessageType())
	        		{
	        		case XMLConstants.SETSENDCHATMESSAGE:
	        		{
	        			
	                    MessageChatDao myDao=new MessageChatDao();
	                    User buff=new User();
	                    buff.setLogin(parsingResult.get(0));
	                    ChatMessage message=new ChatMessage(parsingResult.get(1),buff,parsingResult.get(2));
	                    
	                    ArrayList<String> result=new ArrayList<String>();
	        			try
		        		{
		        		if(myDao.insert(message))
		        		{		        	
		        		        	result.add(XMLConstants.TAGS_SEND_MAIN_CHAT_SUCCESS);
		        		        	outputLine=XMLMessagesCreator.createSendChatMessagesXML(result);
		        		}
		        		else
		        		{
		        			result.add(XMLConstants.TAGS_SEND_MAIN_CHAT_FAIL);
        		        	outputLine=XMLMessagesCreator.createSendChatMessagesXML(result);
		        		}
		        			
		        			log.info("Result of XMLCreator for sent message:\n");
		        		log.info(outputLine);
		       		    out.println(outputLine);
		        		out.flush();
		        		}
		        		catch(SQLException e)
		        		{
		        			e.printStackTrace();
		        		}
		       		break;
	        		}
	        		case XMLConstants.SETRETRIEVEALLCHATMESSAGE:
	        		{
	        			try
		        		{
	        		       
		        		String responce=MessageChatDao.getAllMessages();
		        		log.info("Result of XMLCreator for all messages:\n");
		        		log.info(responce);
		       		    out.println(responce);
		        		out.flush();
		        		    
		        		}
		        		catch(SQLException e)
		        		{
		        			e.printStackTrace();
		        		}
		        		
	                   
		       		break;
	        		}
	        		case XMLConstants.SETRETRIEVERESENTCHATMESSAGE:
	        		{
	        			Socket connectionBuff=connection;
	        			User buffUser=new User();
	        			buffUser.setLogin(parsingResult.get(0));
	        			
	        			for(Map.Entry<User, Socket> entry:listeningForReceivedChatMessagesSocketMap.entrySet())
	        			{
	        				if(entry.getValue().isClosed())
	        				{
	        					listeningForReceivedChatMessagesSocketMap.remove(entry.getKey());
	        				}
	        			}
	        			
	        			//we must decline those users who are already responded with messages!!!
	        			
	        			if(!listeningForReceivedChatMessagesSocketMap.containsKey(buffUser))
	        			listeningForReceivedChatMessagesSocketMap.put(buffUser,connectionBuff);	        	                          
	       		    break;
	        			
	        		}
	        		case XMLConstants.SETGETALLUSERSINFO:
		        	{
		        		try
		        		{
		        			
		        			//we extract history about all users except initiator of request
		        		String responce=XMLMessagesCreator.createGetAllUsersConversationsResponce(ConversationDao.getAllConversationsInfo(parsingResult.get(0)));
		        		log.info("Result of XMLCreator for getting all users info:\n");
		        		log.info(responce);
		       		    out.println(responce);
		        		out.flush();
		        		}
		        		catch(SQLException e)
		        		{
		        			e.printStackTrace();
		        		}
		       		break;
		        	}
	        		case XMLConstants.SETGETALLCONVERSATIONMESSAGES:
		        	{
		        		try
		        		{
		        					        
		        		String responce=XMLMessagesCreator.createReceiveAllConversationMessagesXML(ConversationDao.getAllConversationMessages(Integer.parseInt(parsingResult.get(0))));
		        		log.info("Result of XMLCreator for getting all users info:\n");
		        		log.info(responce);
		       		    out.println(responce);
		        		out.flush();
		        		}
		        		catch(SQLException e)
		        		{
		        			e.printStackTrace();
		        		}
		       		break;
		        	}
	        		case XMLConstants.SETINSERTCONVERSATIONMESSAGE:
		        	{
		        		try
		        		{
		        				
		        		List<String> buffListToXML= new ArrayList<>();
		        		
		        		PrivateMessage messageToInsert=new PrivateMessage();
		        		messageToInsert.setDate(parsingResult.get(0));
		        		messageToInsert.setMessage(parsingResult.get(1));
		        		
		        		User toInsert=new User();
		        		toInsert.setLogin(parsingResult.get(2));
		        		messageToInsert.setSender(toInsert);
		        		
		        		messageToInsert.setConversationId(Integer.parseInt(parsingResult.get(3)));
		        		
		        		buffListToXML.add(ConversationDao.insertMessageToConversation(messageToInsert));
		        			
		        	    String responce=XMLMessagesCreator.createInsertConversationMessagesXML(buffListToXML);
		        		log.info("Result of XMLCreator for insertion message into conversation:\n");
		        		log.info(responce);
		       		    out.println(responce);
		        		out.flush();
		        		}
		        		catch(SQLException e)
		        		{
		        			e.printStackTrace();
		        		}
		       		break;
		        	}
	        		case XMLConstants.SETGETRECENTCONVERSATIONMESSAGES:
		        	{
		        		Socket connectionBuff=connection;
	        			User buffUser=new User();
	        			buffUser.setLogin(parsingResult.get(1));
	        			
	        			for(Map.Entry<User, Socket> entry:listeningForReceivedPrivateMessagesSocketMap.entrySet())
	        			{
	        				if(entry.getValue().isClosed())
	        				{
	        					listeningForReceivedPrivateMessagesSocketMap.remove(entry.getKey());
	        				}
	        			}
	        			
	        			//we must decline those users who are already responded with messages!!!
	        			
	        			if(!listeningForReceivedPrivateMessagesSocketMap.containsKey(buffUser))
	        			listeningForReceivedPrivateMessagesSocketMap.put(buffUser,connectionBuff);	        	                          
	       		    break;
		        	}
	        		
	        		
	        	}
	        	
	        		        	        	
	           }
	        }
	     }
	 }
	       		catch(IOException e)
			{
	       			e.printStackTrace();
			}
			finally {
				
				//we mustn't close socket when it's listening to new messages(either it's for conversation or for chat)
				int i=0;
				
				if(connection!=null&&myMessageParser==null)
				{
					i=1;
				}
					
					if(connection!=null&&myMessageParser!=null&&(myMessageParser.getXMLMessageType())!=XMLConstants.SETRETRIEVERESENTCHATMESSAGE)
					{
						i=2;
					}
						
						if(connection!=null&&myMessageParser!=null&&(myMessageParser.getXMLMessageType()!=XMLConstants.SETGETRECENTCONVERSATIONMESSAGES))
						{
							i=3;
						}
				
				if((connection!=null&&myMessageParser==null)||(connection!=null&&myMessageParser!=null&&(myMessageParser.getXMLMessageType())!=XMLConstants.SETRETRIEVERESENTCHATMESSAGE&&(myMessageParser.getXMLMessageType()!=XMLConstants.SETGETRECENTCONVERSATIONMESSAGES)))
				{
					try
					{
						connection.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
    	
          }
			--numberOfClients;   
        }
     }
  }
    


