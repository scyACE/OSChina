package com.wuxianedu.oschina.utils;

import android.content.Context;

import com.wuxianedu.oschina.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeFormatUtil {

    private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//    public static SimpleDateFormat hourMinSSDateFormat = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat hourMinDateFormat = new SimpleDateFormat("HH:mm");

    public static String friendlyFormat(Context context, String updated_at) {
        int index = updated_at.lastIndexOf(':');
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < updated_at.length(); i++) {
            if (i != index)
                stringBuilder.append(updated_at.charAt(i));
        }
        Date date = null;
        try {
            date = defaultDateFormat.parse(stringBuilder.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "Unknown";
        }
        Date now = new Date(System.currentTimeMillis());
        if (now.getYear() == date.getYear()) {
            if (now.getMonth() == date.getMonth()) {
                if (now.getDate() == date.getDate()) {
                    return context.getString(R.string.today, hourMinDateFormat.format(date));
                    //return hourMinDateFormat.format(date);
                } else if (now.getDate() - date.getDate() == 1) {
                    return context.getString(R.string.first_before_dat, hourMinDateFormat.format(date));
                } else if (now.getDate() - date.getDate() == 2) {
                    return context.getString(R.string.secode_before_dat, hourMinDateFormat.format(date));
                } else {
                    return String.format(context.getString(R.string.before_day), now.getDate() - date.getDate(), hourMinDateFormat.format(date));
                }
            } else {
                return context.getString(R.string.before_month, now.getMonth() - date.getMonth());
            }
        } else {
            return context.getString(R.string.before_year, now.getYear() - date.getYear());
        }
    }
}
