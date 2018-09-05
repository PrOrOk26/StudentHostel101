import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import com.mysql.cj.jdbc.Driver;

public class ConnectionFactory {
     
	   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/HostelTasks101?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	   static final String USER = "prorok26";
	   static final String PASS = "pikasso1937";
	   
	   static final String SQL_USEDB_STATEMENT="USE hosteltasks101";
	   
	   
	   public static Connection getConnection()
	   {
		   try {
		          DriverManager.registerDriver(new Driver());
		          Connection myConnection=DriverManager.getConnection(DB_URL, USER, PASS);		        
		          return myConnection;
		      } catch (SQLException ex) {
		          throw new RuntimeException("Error connecting to the database", ex);
		      }
	    }
	   
	   private static void useDb(Connection myConn) throws SQLException
	   {
		 			
		    try (PreparedStatement stm = myConn.prepareStatement(SQL_USEDB_STATEMENT)) {		    
		        
		       Logger querry=Logger.getLogger(LoggingMXBean.class.getName());
		       querry.info(stm.toString());
		        
		       stm.executeUpdate();
		       				     	        		        
		    }
	   }
	   
}
