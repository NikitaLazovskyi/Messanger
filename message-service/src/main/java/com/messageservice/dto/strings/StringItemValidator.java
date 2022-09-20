package com.messageservice.dto.strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class StringItemValidator implements ConstraintValidator<ValidateString, String> {

    private final String MESSAGE = "^.{1,500}$";
    private final String ROOM_NAME = "^[\\-\\s0-9\\p{L}]{3,50}$";
    private final String USERNAME = "^\\w{5,25}$";
    private final HashMap<StringItem, String> validations = new HashMap<>();
    private StringItem item;

    @Override
    public void initialize(ValidateString constraintAnnotation) {
        validations.put(StringItem.USERNAME, USERNAME);
        validations.put(StringItem.ROOM_NAME, ROOM_NAME);
        validations.put(StringItem.MESSAGE, MESSAGE);

        item = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (string == null) {
            return true;
        }
        if (validations.containsKey(item)) {
            return string.matches(validations.get(item));
        } else {
            return false;
        }
    }
}
