package com.bookingrest.util;

import java.util.Date;

public final class DateComparator {

    public static Date older(Date date1, Date date2){
        if(date1.after(date2)) return date1;

        return date2;
    }

    public static Date newer(Date date1, Date date2){
        if(date1.after(date2)) return date2;

        return date1;
    }
}
