package com.vodafone.com.newsapplicationwithfragement.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by admin on 8/6/2016.
 */

public class DateUtil {

    public static String GetLocalDate(String utcDate) throws ParseException {
        Locale.setDefault(Locale.US);

        SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'");
        Date sourceDate = sourceDateFormat.parse("2016-08-06T05:04:01Z");

        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String targetString = targetFormat.format(sourceDate);
        return targetString;

    }
}
