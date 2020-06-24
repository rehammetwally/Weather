package com.engrehammetwally.weather.common;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Common {
    public static final String API_KEY = "c1097cbd83b5a6215be6bf0c5d56add0";
    public static final String API_LINK = "https://api.openweathermap.org/data/2.5/weather";
    public static final String API_DAILY_LINK = "http://api.openweathermap.org/data/2.5/forecast";

    public static String apiRequest(String lat, String lng) {
        StringBuilder stringBuilder = new StringBuilder(API_LINK);
        stringBuilder.append(String.format("?lat=%s&lon=%s&appid=%s&units=metric", lat, lng, API_KEY));

        return stringBuilder.toString();
    }

//    http://api.openweathermap.org/data/2.5/forecast?lat=30.0626&lon=31.2497&appid=c1097cbd83b5a6215be6bf0c5d56add0

    //    https://api.openweathermap.org/data/2.5/forecast?q=Cairo,EG&appid=c1097cbd83b5a6215be6bf0c5d56add0
//    https://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&cnt=7&appid=c1097cbd83b5a6215be6bf0c5d56add0
    public static String apiDailyRequest(String lat, String lng) {
        StringBuilder stringBuilder = new StringBuilder(API_DAILY_LINK);
        stringBuilder.append(String.format("?lat=%s&lon=%s&appid=%s", lat, lng, API_KEY));

        return stringBuilder.toString();
    }

    public static String unixTimeStampToDateTime(double unixTimeStamp) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long) unixTimeStamp * 1000);
        return dateFormat.format(date);
    }


    public static String getImage(String icon) {
        return String.format("https://api.openweathermap.org/img/w/%s.png", icon);
    }

    public static String getTimeNow() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getDateNow() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getDay() {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

    }

    public static String getDayName(String dateString) {

        Date date = null;
        try {
            if (dateString.equals("Tomorrow")) {
                date = new SimpleDateFormat("yyyy-M-d").parse(getTomorrow());
            } else if (dateString.equals("Today")) {
                date = new SimpleDateFormat("yyyy-M-d").parse(getDateNow());
            } else {
                date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);

    }

    public static String getTomorrowDay() {

        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(tomorrow.getTime());

    }

    public static String getTomorrow() {

        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        return new SimpleDateFormat("yyyy-MM-dd").format(tomorrow.getTime());

    }

    public static String getDate(String dateStr) {
        Date date = null;
        try {
            if (dateStr.equals("Tomorrow")) {
                date = new SimpleDateFormat("yyyy-M-d").parse(getTomorrow());
            } else if (dateStr.equals("Today")) {
                date = new SimpleDateFormat("yyyy-M-d").parse(getDateNow());
            } else {
                date = new SimpleDateFormat("yyyy-M-d").parse(dateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
