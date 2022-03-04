package com.bookingrest.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class ValidReservationValidator
        implements ConstraintValidator<ValidReservation, Reservation> {

    @Override
    public boolean isValid(
            Reservation reservation, ConstraintValidatorContext context) {

        if (reservation == null) {
            return true;
        }

        if (!(reservation instanceof Reservation)) {
            throw new IllegalArgumentException("Illegal method signature, "
                    + "expected parameter of type Reservation.");
        }

        if (reservation.getCheckin() == null
                || reservation.getGuest() == null
                || reservation.getRoom() == null) {
            return false;
        }

        if(reservation.getCheckin().before(new Date())
                || reservation.getPeople() < 1
                || reservation.getPeople() > reservation.getRoom().getCapacity()) {
            return false;
        }

        return (reservation.getCheckout() != null
                && reservation.getCheckout().after(reservation.getCheckin()));
    }
}
