package com.bookingrest.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class ValidBookingValidator
        implements ConstraintValidator<ValidBooking, Booking> {

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
            return false;
        }

        if(booking.getCheckin().before(new Date())
                || booking.getPeople() < 1
                || booking.getPeople() > booking.getRoom().getCapacity()) {
            return false;
        }

        return (booking.getCheckout() != null
                && booking.getCheckout().after(booking.getCheckin()));
    }
}
