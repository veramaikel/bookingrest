package com.bookingrest.model;

import com.bookingrest.exception.InvalidBookingException;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class ValidBookingValidator
        implements ConstraintValidator<ValidBooking, Booking> {

    @SneakyThrows
    @Override
    public boolean isValid(
            Booking booking, ConstraintValidatorContext context) {

        if (booking == null) {
            return true;
        }

        if (!(booking instanceof Booking)) {
            throw new IllegalArgumentException("Illegal method signature, "
                    + "expected parameter of type Booking.");
        }

        if (booking.getCheckin() == null
                || booking.getGuest() == null
                || booking.getRoom() == null) {
            throw new InvalidBookingException("Check-in, Guest and Room are mandatory data, they cannot be null");
        }

        if(booking.getPeople() < 1 || booking.getPeople() > booking.getRoom().getCapacity()) {
            throw new InvalidBookingException("The number of people must be between 1 and the capacity of the room");
        }

        return (booking.getCheckin().after(new Date()) &&
                (booking.getCheckout() == null || booking.getCheckout().after(booking.getCheckin())));
    }
}
