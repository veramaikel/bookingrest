package com.bookingrest.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;

public final class BookingUtil {

    public static Date older(Date date1, Date date2){
        if(date1.after(date2)) return date1;

        return date2;
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
