package com.bookingrest.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class BookingUtil {

    public static Date convert(LocalDate date){
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        return Date.from(date.atStartOfDay(defaultZoneId).toInstant());
    }

    public static long subtractDates(LocalDate min, LocalDate max){

        return convert(max).getTime() - convert(min).getTime();
    }

    public static LocalDate older(LocalDate date1, LocalDate date2){
        if(date1.isAfter(date2)) return date1;

        return date2;
    }

    public static Date older(Date date1, Date date2){
        if(date1.after(date2)) return date1;

        return date2;
    }

    public static LocalDate newer(LocalDate date1, LocalDate date2){
        if(date1.isAfter(date2)) return date2;

        return date1;
    }

    public static Date newer(Date date1, Date date2){
        if(date1.after(date2)) return date2;

        return date1;
    }

    public static Pageable getPageable(Pageable pageable, Sort defaultSort){
        if(pageable.getSort().isUnsorted())
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(defaultSort));
    }

    public static Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }
}
