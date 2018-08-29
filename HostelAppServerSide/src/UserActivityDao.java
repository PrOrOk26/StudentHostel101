import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

public class UserActivityDao {
	
	private static final String SQL_ACTIVITY_TYPE_ID="Activity_type_id";
	
	private static final String SQL_PERFORMANCE_DATE="Users_activities_datetime";
	
	private static final String SQL_PERFORMER_LOGIN="Users_User_Login";
	
	private static final String SQL_ACTIVITIES_TABLE="Users_activities";
	
	private static final String SQL_ACTIVITY_TYPE_TABLE="Activity_types";
	
	private static final String SQL_INSERT_ACTIVITY_TYPE_GARBAGE="INSERT INTO Activity_types (Activity_type_name,Activity_type_description) VALUES ('garbage','describes taking out garbage packages performed by users')";
	
	private static final String SQL_INSERT_ACTIVITY_TYPE_BREAD="INSERT INTO Activity_types (Activity_type_name,Activity_type_description) VALUES ('bread','describes buying bread performed by users')";
	
	private static final String SQL_GET_ALL_GARBAGE="SELECT Users_activities_datetime,Users_User_Login FROM Users_activities WHERE Activity_type_id=? AND Users_User_Login IN (SELECT User_Login FROM Users WHERE User_Group=(SELECT User_Group FROM Users WHERE User_Login=?))";
	
	private static final String SQL_GET_ALL_BREAD="SELECT Users_activities_datetime,Users_User_Login FROM Users_activities WHERE Activity_type_id=? AND Users_User_Login IN (SELECT User_Login FROM Users WHERE User_Group=(SELECT User_Group FROM Users WHERE User_Login=?))";
	
	private static final String SQL_GET_RECENT_BREAD="SELECT Users_activities_datetime,Users_User_Login FROM Users_activities WHERE Activity_type_id=? AND Users_User_Login IN (SELECT User_Login FROM Users WHERE User_Group=(SELECT User_Group FROM Users WHERE User_Login=?)) ORDER BY Users_activities_datetime DESC LIMIT 5";
	
	private static final String SQL_GET_RECENT_GARBAGE="SELECT Users_activities_datetime,Users_User_Login FROM Users_activities WHERE Activity_type_id=? AND Users_User_Login IN (SELECT User_Login FROM Users WHERE User_Group=(SELECT User_Group FROM Users WHERE User_Login=?)) ORDER BY Users_activities_datetime DESC LIMIT 5";
	
	private static final String SQL_INSERT_BREAD="INSERT INTO Users_activities (Users_activities_datetime,Users_User_Login,Activity_type_id) VALUES (?,?,?)";
	
	private static final String SQL_INSERT_GARBAGE="INSERT INTO Users_activities (Users_activities_datetime,Users_User_Login,Activity_type_id) VALUES (?,?,?)";
	
	
	
	
	public static String getAllGarbage(List<String> retrieverLogin) throws SQLException
	{
		List<UserActivity> result=new ArrayList<>();
		
        Connection userCon=ConnectionFactory.getConnection();
        
        String outputLine=null;
		
        UserActivity buff;
        
        int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_ALL_GARBAGE)) {
	    	
	    	stm.setInt(++i,XMLConstants.ACTIVITY_GARBAGE_TYPE);
	        stm.setString(++i,retrieverLogin.get(0));

	        ResultSet rs = stm.executeQuery();
	        
	        Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
		       
		       Timestamp buffStamp=null;
		       User buffUser=null;
	        while(rs.next())
	        {
	        	buff=new UserActivity(XMLConstants.ACTIVITY_GARBAGE_TYPE);
	        	
	        	buff.setPerformerLogin(rs.getString(SQL_PERFORMER_LOGIN));
	        	      
				buffStamp=rs.getTimestamp(SQL_PERFORMANCE_DATE);
				
				buffUser=UserDao.getByLogin(buff.getPerformerLogin());
					        	
	        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffStamp.toLocalDateTime()));
	        	
	        	buff.setPerformerName(buffUser.getName());
	        	
	        	buff.setPerformerSurname(buffUser.getSurname());
	        	
	        	result.add(buff);
	        	
	        }
	        
	        outputLine=XMLCreator.createGetGarbageXML(result);
	    }
	    
		return outputLine;
	}
	 
	public static String getAllBread(List<String> retrieverLogin) throws SQLException
	{
        List<UserActivity> result=new ArrayList<>();
		
        Connection userCon=ConnectionFactory.getConnection();
        
        String outputLine=null;
		
        UserActivity buff;
        
        int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_ALL_BREAD)) {
	    	
	    	stm.setInt(++i,XMLConstants.ACTIVITY_BREAD_TYPE);
	        stm.setString(++i,retrieverLogin.get(0));

	        ResultSet rs = stm.executeQuery();
	        
	        Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
		       
		       Timestamp buffStamp=null;
		       User buffUser=null;
	        while(rs.next())
	        {
	        	buff=new UserActivity(XMLConstants.ACTIVITY_BREAD_TYPE);
	        	
	        	buff.setPerformerLogin(rs.getString(SQL_PERFORMER_LOGIN));
	        	      
				buffStamp=rs.getTimestamp(SQL_PERFORMANCE_DATE);
				
				buffUser=UserDao.getByLogin(buff.getPerformerLogin());
					        	
	        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffStamp.toLocalDateTime()));
	        	
	        	buff.setPerformerName(buffUser.getName());
	        	
	        	buff.setPerformerSurname(buffUser.getSurname());
	        	
	        	result.add(buff);
	        	
	        }
	        
	        outputLine=XMLCreator.createGetBreadXML(result);
	    }
	    return outputLine;
	}
	
	public static String getRecentGarbage(List<String> retrieverLogin) throws SQLException
	{
        List<UserActivity> result=new ArrayList<>();
		
        Connection userCon=ConnectionFactory.getConnection();
        
        String outputLine=null;
		
        UserActivity buff;
        
        int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_RECENT_GARBAGE)) {
	    	
	    	stm.setInt(++i,XMLConstants.ACTIVITY_GARBAGE_TYPE);
	        stm.setString(++i,retrieverLogin.get(0));

	        ResultSet rs = stm.executeQuery();
	        
	        Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
		       
		       Timestamp buffStamp=null;
		       User buffUser=null;
	        while(rs.next())
	        {
	        	buff=new UserActivity(XMLConstants.ACTIVITY_GARBAGE_TYPE);
	        	
	        	buff.setPerformerLogin(rs.getString(SQL_PERFORMER_LOGIN));
	        	      
				buffStamp=rs.getTimestamp(SQL_PERFORMANCE_DATE);
				
				buffUser=UserDao.getByLogin(buff.getPerformerLogin());
					        	
	        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffStamp.toLocalDateTime()));
	        	
	        	buff.setPerformerName(buffUser.getName());
	        	
	        	buff.setPerformerSurname(buffUser.getSurname());
	        	
	        	result.add(buff);
	        	
	        }
	        
	        outputLine=XMLCreator.createGetGarbageXML(result);
	    }
		return outputLine;
	}
	
	public static String getRecentBread(List<String> retrieverLogin) throws SQLException
	{
    List<UserActivity> result=new ArrayList<>();
		
        Connection userCon=ConnectionFactory.getConnection();
		
        UserActivity buff;
        
        String outputLine=null;
        
        int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_RECENT_BREAD)) {
	    	
	    	stm.setInt(++i,XMLConstants.ACTIVITY_BREAD_TYPE);
	        stm.setString(++i,retrieverLogin.get(0));

	        ResultSet rs = stm.executeQuery();
	        
	        Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
		       
		       Timestamp buffStamp=null;
		       User buffUser=null;
	        while(rs.next())
	        {
	        	buff=new UserActivity(XMLConstants.ACTIVITY_BREAD_TYPE);
	        	
	        	buff.setPerformerLogin(rs.getString(SQL_PERFORMER_LOGIN));
	        	      
				buffStamp=rs.getTimestamp(SQL_PERFORMANCE_DATE);
				
				buffUser=UserDao.getByLogin(buff.getPerformerLogin());
					        	
	        	buff.setDate(DateTimeUtils.convertDateTimeToString(buffStamp.toLocalDateTime()));
	        	
	        	buff.setPerformerName(buffUser.getName());
	        	
	        	buff.setPerformerSurname(buffUser.getSurname());
	        	
	        	result.add(buff);
	        	
	        }
	        
	        outputLine=XMLCreator.createGetBreadXML(result);
	    }
		return outputLine;
	}
	
	public static String insertBread(List<String> breadActivity) throws SQLException
	{
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
		
		int rst=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_INSERT_BREAD)) {
	    		    
	    	
	    	stm.setTimestamp(++i,java.sql.Timestamp.valueOf(breadActivity.get(1)));
	    	stm.setString(++i,breadActivity.get(0));
	    	stm.setInt(++i,XMLConstants.ACTIVITY_BREAD_TYPE);
	    	
	    	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
	    		    			   
	        rst = stm.executeUpdate();	        	      
	      
	       	        
	    	}
	    
	    if(rst>0)
	    	return new String(XMLCreator.createInsertBreadResponceXML(XMLConstants.INSERT_BREAD_GARBAGE_SUCCESS));
	    else
	    	return new String(XMLCreator.createInsertBreadResponceXML("failed to insert bread/garbage!"));
	   
	}
	
	public static String insertGarbage(List<String> garbageActivity) throws SQLException
	{
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
		
		int rst=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_INSERT_GARBAGE)) {
	    		    
	    	
	    	stm.setTimestamp(++i,java.sql.Timestamp.valueOf(garbageActivity.get(1)));
	    	stm.setString(++i,garbageActivity.get(0));
	    	stm.setInt(++i,XMLConstants.ACTIVITY_GARBAGE_TYPE);
	    	
	    	  Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
	    		    			   
	        rst = stm.executeUpdate();	        	      
	      
	       
	    	}
	    
	    if(rst>0)
	    	return new String(XMLCreator.createInsertGarbageResponceXML(XMLConstants.INSERT_BREAD_GARBAGE_SUCCESS));
	    else
	    	return new String(XMLCreator.createInsertGarbageResponceXML("failed to insert bread/garbage!"));
	}
	
}
