import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

public class MessageChatDao implements InterfaceDao<Message>{
	
private static final String SQL_NAME_COLUMN="User_Name";
	
	private static final String SQL_LOGIN="User_Login";	
	
	private static final String SQL_TEXT="Message_text";	
	
	private static final String SQL_DATE="Message_date_time";	
			
	private static final String SQL_CHAT_TABLE="MessageChat";		
	
	private static final String SQL_INSERT_CHAT_MESSAGE="INSERT INTO MessageChat (Message_text,Message_date_time,User_Login) VALUES (?,?,?)";
		
	private static final String SQL_RETRIEVE_ALL_CHAT_MESSAGES="SELECT Message_text,Message_date_time,User_Login "
			+ "FROM MessageChat";
	
	private static final String SQL_RETRIEVE_ALL_RECEIVED_CHAT_MESSAGES_BY_USER="SELECT (Message_text,Message_date_time,User_Login) "
			+ "FROM MessageChat "
			+ "WHERE NOT User_Login=?";
	
	private static final String SQL_RETRIEVE_ALL_RECENT_RECEIVED_MESS_BY_USER="SELECT (Message_text,Message_date_time,User_Login) "
			+ "FROM MessageChat "
			+ "WHERE Message_Id>(SELECT Message_Id FROM MessageChat WHERE Message_Login=? AND Message_Text=? AND Message_date_time=?) AND NOT User_Login=?";
	
	
				

	@Override
	public Message get(int value) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getAll() throws SQLException {
		
       Connection userCon=ConnectionFactory.getConnection();
		
		List<Message> messages = new ArrayList<>();
		Message buff;
		
	    try (Statement stm = userCon.createStatement()) {

	        ResultSet rs = stm.executeQuery(SQL_RETRIEVE_ALL_CHAT_MESSAGES);
	        
	        Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
		       
		       Timestamp buffStamp=null;
	        while(rs.next())
	        {
	        	buff=new Message();
	        	
	        	buff.setMessage(rs.getString(SQL_TEXT));
	        	
	        	buffStamp=rs.getTimestamp(SQL_DATE);
	        	
	        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffStamp.toLocalDateTime()));
	        	buff.getSender().setLogin(rs.getString(SQL_LOGIN));	        	
	        	messages.add(buff);
	    }
		
	}
	    return messages;
	}
	
		
	@Override
	public boolean insert(Message entity) throws SQLException {
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_INSERT_CHAT_MESSAGE)) {
	    		    
	    	
	    	stm.setString(++i,entity.getMessage());
	    	stm.setTimestamp(++i,java.sql.Timestamp.valueOf(entity.getDate()));
	    	stm.setString(++i,entity.getSender().getLogin());
	    	
	    	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
	    		    			   
	        int rst = stm.executeUpdate();
	        
	        new Thread(new ReceivedChatMessagesNotifyClientsRunnable(entity)).start();
	      
	        return (rst==1 ? true: false);
	    	}
	        
	    }
	
	public static String getAllMessages() throws SQLException
	{
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
		String outputLine=new String();
		
		List<Message> resultList=new ArrayList<>();
		Message buff=new Message();
		
	    try (Statement stm = userCon.createStatement()) {
	    	   
	    	
	    	 ResultSet rs = stm.executeQuery(SQL_RETRIEVE_ALL_CHAT_MESSAGES);
	    	 
	    	 Timestamp buffStamp=null;
	    		    	 
		        while(rs.next())
		        {
		        	buff=new Message();		        	
		        	buff.setMessage(rs.getString(SQL_TEXT));
		        	
		        	buffStamp=rs.getTimestamp(SQL_DATE);
		        	
		        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffStamp.toLocalDateTime()));
		        	buff.getSender().setLogin(rs.getString(SQL_LOGIN));	        	
		        	resultList.add(buff);
		    }
			
		}
		    outputLine=XMLMessagesCreator.createReceiveAllChatMessagesXML(resultList);	   
		    
		    return outputLine;
	}
	
	public static String getAllRecentMessagesForUser(List<String> userData) throws SQLException
	{
		Connection userCon=ConnectionFactory.getConnection();
		
		Iterator<String> myIterator=userData.iterator();
		
		int i=0;
		
		String outputLine=new String();
		
		List<Message> resultList=new ArrayList<>();
		Message buff=new Message();
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_RETRIEVE_ALL_RECENT_RECEIVED_MESS_BY_USER)) {
	    	
	    	String buffForLogin=myIterator.next();
	    	
	    	stm.setString(++i, buffForLogin);
	    	stm.setString(++i, myIterator.next());
	    	stm.setString(++i, myIterator.next());
	    	stm.setString(++i, buffForLogin);
	    	
	    	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
		       
	    	 ResultSet rs = stm.executeQuery();
	    	 
	    	 Timestamp buffStamp=null;
	    	 
		        while(rs.next())
		        {
		        	buff=new Message();		        	
		        	buff.setMessage(rs.getString(SQL_TEXT));
		        	
		        	buffStamp=rs.getTimestamp(SQL_DATE);
		        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffStamp.toLocalDateTime()));
		        	buff.getSender().setLogin(rs.getString(SQL_LOGIN));	        	
		        	resultList.add(buff);
		    }
			
		}
		    outputLine=XMLMessagesCreator.createReceiveAllRecentChatMessagesXML(resultList);	   
		    
		    return outputLine;
	}
	



	@Override
	public boolean update(Message entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Message entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	 

}
