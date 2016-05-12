package com.ddd.poc.query.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String format(Date date) {
        return new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(date);
    }
}
