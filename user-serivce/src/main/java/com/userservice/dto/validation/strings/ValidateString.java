package com.userservice.dto.validation.strings;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringItemValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Used to validate specified strings in com.userservice.dto.validation.strings.StringItem
 * When the string is null return true
 */
public @interface ValidateString {
    StringItem value();

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
