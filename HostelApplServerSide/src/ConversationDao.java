import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

public class ConversationDao {
	
	private static String SQL_GET_ALL_CONVERSATIONS_ID="SELECT Conversation_Id,Participant_Login FROM Participants WHERE NOT Participant_Login=? AND Conversation_Id IN (SELECT Conversation_Id FROM Participants WHERE Participant_Login=?)";
	
	private static String SQL_GET_ALL_CONVERSATION_MESSAGES="SELECT Date_Sent,Message_text,Sender_Login,Conversation_Id FROM Message WHERE Conversation_id = ?";
	
	private static String SQL_INSERT_CONVERSATION_MESSAGE="INSERT INTO Message (Date_Sent,Message_Text,Sender_Login,Conversation_Id) VALUES (?,?,?,?)";
	
	private static String SQL_CREATE_CONVERSATION_FOR_NOT_PARTICIPATING_USERS="INSERT INTO Conversation (Conversation_Name,Creator_Login,Creation_date) VALUES (?,?,?)";
	
	private static String SQL_ADD_USER_TO_CONVERSATION="INSERT INTO Participants (Entrance_Date,Participant_Login,Conversation_Id) VALUES (?,?,?)";
	
	private static String SQL_GET_LAST_CONVERSATION="SELECT max(Conversation_Id) FROM Conversation";
	
	private static String SQL_CONVERSATION_ID="Conversation_Id";
	
	private static String SQL_PARTICIPANT_LOGIN="Participant_Login";
	
	private static String SQL_DATE_SENT="Date_sent";
	
	private static String SQL_MESSAGE_TEXT="Message_text";
	
	private static String SQL_SENDER_LOGIN="Sender_Login";
	
	
	//the logic is:
	//we want to get all conversations that are possible between users.We want every user to have conversation with every user,so we check if there are all conversations created..
	public static List<String> getAllConversationsInfo(String hostLogin) throws SQLException
	{
		Connection userCon=ConnectionFactory.getConnection();
		
		List<String> conversationsInfo = new ArrayList<>();
		
		//all users that are participating in the same conv with our requester
		
		List<String> loginsParticipating=new ArrayList<>();
		
		List<User> loginsAll=UserDao.getAllUsers();
		
		
		//we determine what users must be inserted into conversation and store them in this list
		
		List<String> usersToCreateConversation=new ArrayList<>();
		
		
		//this code is for getting info about all users participating in the same conversation with our requester
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_ALL_CONVERSATIONS_ID)) {
	    	
	    	stm.setString(1, hostLogin);
	    	stm.setString(2, hostLogin);
	    	
	   	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
	       querry.info(stm.toString());

	        ResultSet rs = stm.executeQuery();
	        while(rs.next())
	        {
	        	conversationsInfo.add(rs.getString(SQL_CONVERSATION_ID));
	        	conversationsInfo.add(rs.getString(SQL_PARTICIPANT_LOGIN));	    
	        	
	        	loginsParticipating.add(rs.getString(SQL_PARTICIPANT_LOGIN));
	        }
	        		
	}
	    
	    for(User buffUser:loginsAll)
	    {
	    	if(!loginsParticipating.contains(buffUser.getLogin())&&!buffUser.getLogin().equals(hostLogin))
	    		usersToCreateConversation.add(buffUser.getLogin());
	    }
	    
	    Integer conversation=new Integer(-1);
	    
	    for(String userToAdd:usersToCreateConversation)
	    	 
	    {
	    	
    try (PreparedStatement stm = userCon.prepareStatement(SQL_CREATE_CONVERSATION_FOR_NOT_PARTICIPATING_USERS)) {
	    	
    	    stm.setString(1, "test");
	    	stm.setString(2, hostLogin);
	    	stm.setTimestamp(3, java.sql.Timestamp.valueOf(DateTimeUtils.convertDateTimeToString(Calendar.getInstance().getTime())));

	    	
	   	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
	       querry.info(stm.toString());
	       
	        int rs = stm.executeUpdate();	 
	        		
	}
    
    try (Statement stm = userCon.createStatement()) {

        ResultSet rs = stm.executeQuery(SQL_GET_LAST_CONVERSATION);	
        
        if(rs.next())
        {
        		
        conversation=new Integer(rs.getInt(1));
        
        }
        
      }
    
    //add user to just created conversation
    try (PreparedStatement stm = userCon.prepareStatement(SQL_ADD_USER_TO_CONVERSATION)) {
    	
    	
  	        stm.setTimestamp(1, java.sql.Timestamp.valueOf(DateTimeUtils.convertDateTimeToString(Calendar.getInstance().getTime())));
	    	stm.setString(2, userToAdd);
	    	stm.setInt(3, conversation.intValue());
	    	
	   	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
	       querry.info(stm.toString());

	    	int rs = stm.executeUpdate();	 
        	
	    	conversationsInfo.add(conversation.toString());
	    	conversationsInfo.add(userToAdd);
}
    
    //we must add host user to every conversation as a participant
    try (PreparedStatement stm = userCon.prepareStatement(SQL_ADD_USER_TO_CONVERSATION)) {
    	
    	
	        stm.setTimestamp(1, java.sql.Timestamp.valueOf(DateTimeUtils.convertDateTimeToString(Calendar.getInstance().getTime())));
    	stm.setString(2, hostLogin);
    	stm.setInt(3, conversation.intValue());
    	
   	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
       querry.info(stm.toString());

    	int rs = stm.executeUpdate();	 
}  
	    }
	    	 
	    return conversationsInfo;
	}
	
	
	public static List<PrivateMessage> getAllConversationMessages(int conversationId) throws SQLException
	{
        Connection userCon=ConnectionFactory.getConnection();
		
		List<PrivateMessage> conversationsInfo = new ArrayList<>();
		
		PrivateMessage buff;
		
		Timestamp buffTime;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_ALL_CONVERSATION_MESSAGES)) {
	    	
	    	stm.setString(1, Integer.toString(conversationId));

	        ResultSet rs = stm.executeQuery();
	        
	   	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
	       querry.info(stm.toString());
	       
	        while(rs.next())
	        {
	        	buff=new PrivateMessage();
	        	
	        	buff.setConversationId(rs.getInt(SQL_CONVERSATION_ID));
	        	    	
	        	buffTime=rs.getTimestamp(SQL_DATE_SENT);
	        	
	        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffTime.toLocalDateTime()));
	        	
	        	buff.setMessage(rs.getString(SQL_MESSAGE_TEXT));
	        	
	        	User toSet=new User();
	        	
	        	toSet.setLogin(rs.getString(SQL_SENDER_LOGIN));
	        	
	        	buff.setSender(toSet);
	   
	        	conversationsInfo.add(buff);
	        	   
	        }
		
	}
	    return conversationsInfo;
	}
	
	
	public static String insertMessageToConversation(PrivateMessage toInsert) throws SQLException
	{
		Connection userCon=ConnectionFactory.getConnection();
	
		
		   try (PreparedStatement stm = userCon.prepareStatement(SQL_INSERT_CONVERSATION_MESSAGE)) {
		    
		    	stm.setTimestamp(1, java.sql.Timestamp.valueOf(toInsert.getDate()));
		    	stm.setString(2,toInsert.getMessage() );
			    stm.setString(3, toInsert.getSender().getLogin());
			    stm.setInt(4,toInsert.getConversationId());
			    
			 	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
			       querry.info(stm.toString());

		        int rs = stm.executeUpdate();	 
		        
		        new Thread(new ReceivedConversationMessagesNotifyClientsRunnable(toInsert)).start();
		        
		        if(rs==1)
		        {
		        	return new String("message successfully inserted to conversation!");	    
		        }
		        else
		        	return new String("message is failed to insert to conversation!");	
		        		
		}
	}

}
