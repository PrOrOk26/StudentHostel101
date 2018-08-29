package com.applications.a306app.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

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

        public static String getTimeString(Date myDate)
        {
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            String time = localDateFormat.format(myDate);
            return time;
        }

        public static Date convertStringToDate(String date)
        {
            SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateResult=new Date();

            try
            {
                dateResult = sdfr.parse(date);
            }
            catch (Exception ex )
            {
                System.out.println(ex);
            }
            return dateResult;
        }
    }

