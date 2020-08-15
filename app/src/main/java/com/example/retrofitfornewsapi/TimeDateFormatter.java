package com.example.retrofitfornewsapi;

import org.ocpsoft.prettytime.PrettyTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeDateFormatter
{
    private static final String TAG = "TimeDateFormatter";
    public static String DateToTimeFormat(String oldStringDate)
    {
        PrettyTime p = new PrettyTime(new Locale(getCountry()));
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH);

            Date date = sdf.parse(oldStringDate);

            String newsHour = String.valueOf(date).substring(11, 13);
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

            int ago = currentHour-Integer.parseInt(newsHour);

            if(ago<0)
            {
                ago = ago+24;
                if(ago<0)
                    return "1 day ago";
            }
            isTime = String.valueOf(ago)+" hours ago";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTime;
    }
    private static String getCountry()
    {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
