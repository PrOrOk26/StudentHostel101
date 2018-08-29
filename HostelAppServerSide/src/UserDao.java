import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;


public class UserDao implements InterfaceDao<User> {
	
	private static final String SQL_NAME_COLUMN="User_Name";
	
	private static final String SQL_SURNAME_COLUMN="User_Surname";
	
	private static final String SQL_LOGIN="User_Login";
	
	private static final String SQL_PASSWORD="User_Password";
	
	private static final String SQL_USERS_TABLE="users";
	
	private static final String SQL_GROUP="User_group";
	

	private static final String SQL_GET_USER=
			"SELECT * FROM users "
			+ "WHERE users.User_Login=?";
	
	private static final String SQL_GET_ALL_USERS=
			"SELECT * "
			+ "FROM users";
	
	private static final String SQL_INSERT_USER=
			"INSERT INTO users"
			+ " VALUES(?,?,?,?,?)";
	
	private static final String SQL_DELETE_USER=
			"DELETE FROM users"
			+ " WHERE users.User_Login=?";
	
	private static final String SQL_VALIDATE_USER=
			"SELECT User_Login FROM users "
					+ "WHERE users.User_Login=? AND "
					+ "users.User_Password=?";
	
	private static final String SQL_REGISTRATE_USER=
			"INSERT INTO users (User_Name,User_Surname,User_Login,User_Password) VALUES "+"(?,?,?,?)";
	
	private static final String SQL_VALIDATE_USER_GROUP="SELECT User_Group "
			+ "FROM users "
			+ "WHERE User_Login=? AND User_Group IS NOT NULL";
	
	private static final String SQL_CHECK_USER_LOGIN=
			"SELECT User_Login FROM users WHERE User_Login=?" ;
	
	private static final String SQL_CHECK_USER_GROUP="SELECT User_Group FROM users WHERE User_Login=? AND User_Group IS NOT NULL";
	
	private static final String SQL_CREATE_USER_GROUP="UPDATE users "
			+ "SET User_Group=? "
			+ "WHERE User_Login=?";		
	
	private static final String SQL_INSERT_USER_TO_GROUP="UPDATE users SET User_Group=? WHERE User_Login=?";
			
	private static final String SQL_CHECK_IF_USER_IS_THIS_GROUP="SELECT User_Group FROM users WHERE User_Group=? "
			+ "AND User_Login=?";
	
	private static final String SQL_CHECK_IF_GROUP_AVALIABLE="SELECT User_Login FROM users WHERE User_Group=?";
	
	
	private static final String SQL_DELETE_USER_FROM_GROUP="UPDATE users SET User_Group=NULL WHERE User_Login=?";
	
	private static final String SQL_CHECK_IF_GROUP_IS_EMPTY="SELECT User_Login FROM users WHERE User_Group=?";
	
	private static final String SQL_DELETE_GROUP="UPDATE users SET User_Group=NULL WHERE User_Group=?";
	
	private static final String SQL_RETRIEVE_ALL_GROUP_PARTICIPANTS="SELECT User_Login,User_Name,User_Surname FROM Users WHERE Group_Name="
			+ "(SELECT Group_Name FROM Users WHERE User_Login=?)";
	
	private static final String SQL_GET_USER_BY_LOGIN="SELECT * FROM users WHERE User_Login=?";
	
	
	@Override
	public User get(int id) throws SQLException {
		
		Connection userCon=ConnectionFactory.getConnection();
		User user = new User();
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_USER)) {

	        stm.setInt(1,id);

	        ResultSet rs = stm.executeQuery();
	        while(rs.next())
	        {   
	        user.setName(rs.getString(SQL_NAME_COLUMN));
	        user.setSurname(rs.getString(SQL_SURNAME_COLUMN));
	        user.setLogin(rs.getString(SQL_LOGIN));
	        user.setPassword(rs.getString(SQL_PASSWORD));
	    }
		
	}
	    return user;
	}

	@Override
	public List<User> getAll() throws SQLException{
		
		Connection userCon=ConnectionFactory.getConnection();
		
		List<User> users = new ArrayList<>();
		User buff;
		
	    try (Statement stm = userCon.createStatement()) {

	        ResultSet rs = stm.executeQuery(SQL_GET_ALL_USERS);
	        while(rs.next())
	        {
	        	buff=new User();
	        	
	        	buff.setName(rs.getString(SQL_NAME_COLUMN));
	        	buff.setSurname(rs.getString(SQL_SURNAME_COLUMN));
	        	buff.setLogin(rs.getString(SQL_LOGIN));
	        	buff.setPassword(rs.getString(SQL_PASSWORD));
	        	users.add(buff);
	    }
		
	}
	    return users;
	}
	
   public static User getByLogin(String login) throws SQLException {
		
		Connection userCon=ConnectionFactory.getConnection();
		User user = new User();
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_GET_USER)) {

	        stm.setString(1,login);

	        ResultSet rs = stm.executeQuery();
	        while(rs.next())
	        {   
	        user.setName(rs.getString(SQL_NAME_COLUMN));
	        user.setSurname(rs.getString(SQL_SURNAME_COLUMN));
	        user.setLogin(rs.getString(SQL_LOGIN));
	        user.setPassword(rs.getString(SQL_PASSWORD));
	    }
		
	}
	    return user;
	}
	
    public List<User> getAllGroupParticipants(String loginUser) throws SQLException{
		
    	Connection userCon=ConnectionFactory.getConnection();
		List<User> users=new ArrayList<>();
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_RETRIEVE_ALL_GROUP_PARTICIPANTS)) {

	        stm.setString(1,loginUser);

	        ResultSet rs = stm.executeQuery();
	        
	        User buff;
	        
	        while(rs.next())
	        {   	
	        	buff=new User();
	        	buff.setLogin(rs.getString(SQL_LOGIN));	     
	        	buff.setName(rs.getString(SQL_NAME_COLUMN));
	        	buff.setSurname(rs.getString(SQL_SURNAME_COLUMN));
	        	users.add(buff);
	    }
		
	}
	    return users;
	}

	@Override
	public boolean insert(User entity) throws SQLException{
		
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_REGISTRATE_USER)) {
	    	
	    	PreparedStatement stmCheck = userCon.prepareStatement(SQL_CHECK_USER_LOGIN);
	    	
	    	stmCheck.setString(1,entity.getLogin());
	    	
	    	ResultSet rs = stmCheck.executeQuery();
	    	
	    	if(rs.isBeforeFirst())
	    		return false;
	    	else
	    	{
	        stm.setString(++i,entity.getName());
	        stm.setString(++i,entity.getSurname());
	        stm.setString(++i,entity.getLogin());
	        stm.setString(++i,entity.getPassword());
	    	

	        int rst = stm.executeUpdate();
	      
	        return (rst==1 ? true: false);
	    	}
	        
	    }
		
	}
	
	public boolean validateUser(User entity) throws SQLException
	{
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_VALIDATE_USER)) {
	    	stm.setString(++i,entity.getLogin());
	        stm.setString(++i,entity.getPassword());
	        
	       Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
	       querry.info(stm.toString());
	        
	        ResultSet rs=stm.executeQuery();
	       
	        if(rs.isBeforeFirst())
	           return true;
	        return false;
	     	        
	        
	    }
	}


	@Override
	public boolean update(User entity) throws SQLException {
		
		    //TODO	      
	        return (true ? true: false);
	        
	    }

	@Override
	public boolean delete(User entity) throws SQLException{
		
		//TODO	      
	        return (true ? true: false);
	        
	    }
	
	public static String validateUserGroup(List<String> parsingResult) throws SQLException
	{
		
		String outputLine=new String();
		try {
			
    		User userBuff=new User();
    		userBuff.setLogin(parsingResult.get(0)); 		
    		
    		ArrayList<String> result=new ArrayList<String>();
    		
    		Connection userCon=ConnectionFactory.getConnection();
    		int i=0;
    		
    	    try (PreparedStatement stm = userCon.prepareStatement(SQL_VALIDATE_USER_GROUP)) {
    	    	stm.setString(++i,userBuff.getLogin());	      
    	        
    	       Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
    	       querry.info(stm.toString());
    	        
    	        ResultSet rs=stm.executeQuery();
    	       
    	        if(rs.isBeforeFirst())
    	        {
    	        	rs.next();
    	        	result.add(rs.getString(SQL_GROUP));
    	        }	         
    	        result.add(XMLConstants.TAGS_GROUP_VALIDATION_FAIL);
    	     	        
    	        
    	    }
			      	        	   	    
    			
    		outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_GROUP_VALIDATION);
    		
    
    	
    	}
    		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
		
		return outputLine;
	}
	
	
	public static String createUserGroup(List<String> parsingResult) throws SQLException
	{
		boolean isItNeededToContinueCheckingQueries=true;
		String outputLine=new String();
		try {
			
    		User userBuff=new User();
    		userBuff.setLogin(parsingResult.get(0)); 	
    		userBuff.setGroup(parsingResult.get(1)); 
    		
    		Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
    		
    		ArrayList<String> result=new ArrayList<String>();
    		
    		Connection userCon=ConnectionFactory.getConnection();
    		int i=0;
    		
    	    try (PreparedStatement stm = userCon.prepareStatement(SQL_CHECK_USER_GROUP)) {
    	    	stm.setString(++i,userBuff.getLogin());	
    	           	      
    	       querry.info(stm.toString());
    	        
    	        ResultSet rs=stm.executeQuery();
    	       
    	        while(rs.next())	    
    	        {
    	        	result.add(XMLConstants.TAGS_GROUP_CREATION_FAIL);  
    	        	isItNeededToContinueCheckingQueries=false;
    	        	break;
    	        }
    	        if(!isItNeededToContinueCheckingQueries)
    	        {
    	        	outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_GROUP_CREATION);
    	        	return outputLine;
    	        }
    	        	   	      
    	        	i=0;
    	        	try (PreparedStatement stm2 = userCon.prepareStatement(SQL_CHECK_IF_GROUP_AVALIABLE)) {
    	    	    	stm2.setString(++i,userBuff.getGroup());	
    	    	            	    	     
    	    	       querry.info(stm2.toString());
    	    	        
    	    	        ResultSet rs2=stm.executeQuery();
    	    	        
    	    	       while(rs2.next())
    	    	        {    	       
    	    	        	result.add(XMLConstants.TAGS_GROUP_CREATION_ALREADY_EXISTS); 
    	    	        	isItNeededToContinueCheckingQueries=false;
    	    	        	break;
    	    	        }	
    	    	       if(!isItNeededToContinueCheckingQueries)
    	    	        {
    	    	    	   outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_GROUP_CREATION);
    	    	    	   return outputLine;
    	    	        }
    	    	        
    	        	i=0;
    	        	PreparedStatement stm3 = userCon.prepareStatement(SQL_CREATE_USER_GROUP);
    	    	    	
    	    	    stm3.setString(++i,userBuff.getGroup());
    	    	    stm3.setString(++i,userBuff.getLogin());
    	    	           	    	       
    	    	    querry.info(stm3.toString());    	    	         	    	   
    	    	    
    	    	    int rst = stm3.executeUpdate();
    	    	    
    	    	    if(rst>0)
    	    	    	result.add(userBuff.getGroup());
    	    	    else
    	    	    	result.add(XMLConstants.TAGS_GROUP_CREATION_FAIL);
    	  	         		      
    	        }
    	           	     	          	        
    	    }			       	        			   	 
    			
    		outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_GROUP_CREATION);
    		     	
    	}
    		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
		return outputLine;
	}
	
public static String insertUserToGroup(List<String> parsingResult) throws SQLException{
	
	boolean isItNeededToContinueCheckingQueries=true;
	Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
	
	String outputLine=new String();
	
	
	try {
		
		User userBuff=new User();
		userBuff.setLogin(parsingResult.get(0)); 	
		userBuff.setGroup(parsingResult.get(1)); 
		
		ArrayList<String> result=new ArrayList<String>();
		
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
		PreparedStatement stmCheck = userCon.prepareStatement(SQL_CHECK_USER_LOGIN);
    	
    	stmCheck.setString(++i,userBuff.getLogin());
    	
    	ResultSet rst2 = stmCheck.executeQuery();
    	
    	while(!rst2.next())
    	{
    		result.add(XMLConstants.TAGS_GROUP_ADD_USER_FAIL);  
        	isItNeededToContinueCheckingQueries=false;
        	break;
        }	
        
        if(!isItNeededToContinueCheckingQueries)
        {
        	outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_INSERTION_TO_GROUP);
        	return outputLine;
        }	       
    
		
		i=0;
		
		try (PreparedStatement stm2 = userCon.prepareStatement(SQL_CHECK_IF_USER_IS_THIS_GROUP)) {
    		stm2.setString(++i,userBuff.getGroup());
    		stm2.setString(++i,userBuff.getLogin());	
	        	    	     
	       querry.info(stm2.toString());
	        
	        ResultSet rs2=stm2.executeQuery();
	       
	        while(rs2.next())
	        {
	        	result.add(XMLConstants.TAGS_GROUP_ADD_USER_ALREADY_IN_GROUP);  
	        	isItNeededToContinueCheckingQueries=false;
	        	break;
	        }	
	        if(!isItNeededToContinueCheckingQueries)
	        {
	        	outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_INSERTION_TO_GROUP);
	        	return outputLine;
	        }	  
	        
	        i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_CHECK_USER_GROUP)) {
	    	stm.setString(++i,userBuff.getLogin());	
	        	      
	       querry.info(stm.toString());
	        
	        ResultSet rs=stm.executeQuery();
	       
	        while(rs.next())
	        {
	        	result.add(XMLConstants.TAGS_GROUP_ADD_USER_ALREADY_IN_ANOTHER_GROUP);  
	        	isItNeededToContinueCheckingQueries=false;
	        	break;
	        }	
	        
	        if(!isItNeededToContinueCheckingQueries)
	        {
	        	outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_INSERTION_TO_GROUP);
	        	return outputLine;
	        }	       
	        	       	        		    	        	        
	    	        	i=0;
	    	        	PreparedStatement stm3 = userCon.prepareStatement(SQL_INSERT_USER_TO_GROUP);	    	    	   	
	    	    	    stm3.setString(++i,userBuff.getGroup());	
	    	    	    stm3.setString(++i,userBuff.getLogin());
	    	    	           	    	       
	    	    	    querry.info(stm3.toString());    	    	         	    	   
	    	    	    
	    	    	    int rst = stm3.executeUpdate();
	    	    	    
	    	    	    if(rst>0)
	    	    	    	result.add(XMLConstants.TAGS_GROUP_ADD_USER_SUCCESS);
	    	    	    else
	    	    	    	result.add(XMLConstants.TAGS_GROUP_ADD_USER_FAIL);
	    	  	         		      
	    	        
	    	        	
	        }	       
	        
	           	     	          	        
	    	
	
			
		outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_INSERTION_TO_GROUP);
		     	
	    }
	}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	return outputLine;
		
	}

public static String deleteUserFromGroup(List<String> parsingResult) throws SQLException
{
	
	String outputLine=new String();
	try {
		
		User userBuff=new User();
		userBuff.setLogin(parsingResult.get(0)); 	
		userBuff.setGroup(parsingResult.get(1)); 
		
		ArrayList<String> result=new ArrayList<String>();
		
		Connection userCon=ConnectionFactory.getConnection();
		int i=0;
		
	    try (PreparedStatement stm = userCon.prepareStatement(SQL_DELETE_USER_FROM_GROUP)) {
	    	stm.setString(++i,userBuff.getLogin());	
	 //   	stm.setString(++i,userBuff.getGroup());	
	    	
	       Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
	       querry.info(stm.toString());
	        
	        int rs=stm.executeUpdate();	 
	        
	        if(rs==1)
	        	result.add(XMLConstants.TAGS_GROUP_USER_QUIT_SUCCESS);
	        	else
	        		result.add(XMLConstants.TAGS_GROUP_USER_QUIT_FAILURE);
	        i=0;
	        
	        PreparedStatement stmForGroupCheck=userCon.prepareStatement(SQL_CHECK_IF_GROUP_IS_EMPTY);
	        
	        stmForGroupCheck.setString(++i,userBuff.getGroup());
	        
	        ResultSet rs2=stmForGroupCheck.executeQuery();
	        
	        if(!rs2.next())
	        {
	        	i=0;
	        	
	        	PreparedStatement stmForGroupDelete=userCon.prepareStatement(SQL_DELETE_GROUP);
		        
	        	stmForGroupDelete.setString(++i,userBuff.getGroup());
		        
	        	stmForGroupDelete.executeUpdate();
	        }
	        
	      }
	           	     	          	        	    			       	        			   	 			
		outputLine=XMLCreator.createResponceXML(result,COMMON_RESPONCE.XML_QUIT_FROM_GROUP);
		     	
	}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	return outputLine;
}

public static List<User> getAllUsers() throws SQLException
{
	Connection userCon=ConnectionFactory.getConnection();
	
	List<User> users = new ArrayList<>();
	User buff;
	
    try (Statement stm = userCon.createStatement()) {

        ResultSet rs = stm.executeQuery(SQL_GET_ALL_USERS);
        while(rs.next())
        {
        	buff=new User();
        	
        	buff.setName(rs.getString(SQL_NAME_COLUMN));
        	buff.setSurname(rs.getString(SQL_SURNAME_COLUMN));
        	buff.setLogin(rs.getString(SQL_LOGIN));
        	buff.setPassword(rs.getString(SQL_PASSWORD));
        	buff.setGroup(rs.getString(SQL_GROUP));
        	users.add(buff);
    }
	
}
    return users;
}
}


	
