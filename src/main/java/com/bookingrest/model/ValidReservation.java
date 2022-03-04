package com.bookingrest.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidReservationValidator.class)
@Target({ TYPE, METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
@Documented
public @interface ValidReservation {
    String message() default "Check-out date must be after check-in date and both must be in the future, "
            + "the number of people must be between 1 and the capacity of the room";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
