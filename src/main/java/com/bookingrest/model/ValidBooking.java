package com.bookingrest.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidBookingValidator.class)
@Target({ TYPE, METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
@Documented
public @interface ValidBooking {
    String message() default "Check-out date must be after check-in date and both must be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
