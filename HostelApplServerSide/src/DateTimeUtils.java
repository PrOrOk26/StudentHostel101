import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

	public static String convertDateTimeToString(LocalDateTime myDate)
	{
		   String dateString = null;		  
		   DateTimeFormatter myFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		   
		   try
		   {
			dateString = myFormatter.format(myDate);
		   }
		   catch (Exception ex )
		   {
			System.out.println(ex);
		   }
		   return dateString;
	}
	
	 public static String convertDateTimeToString(Date myDate)
     {
         String dateString = null;
         SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

         try
         {
             dateString = sdfr.format(myDate);
         }
         catch (Exception ex )
         {
             System.out.println(ex);
         }
         return dateString;
     }
}
