package com.bookingrest.exception;

public class InvalidBookingException extends Exception {
    public InvalidBookingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBookingException(String message) {
        this(message, new Throwable("Booking not allowed"));
    }
}
