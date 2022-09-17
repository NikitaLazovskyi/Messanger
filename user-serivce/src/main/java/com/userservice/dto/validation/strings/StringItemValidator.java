package com.userservice.dto.validation.strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class StringItemValidator implements ConstraintValidator<ValidateString, String> {

    private final String FIRSTNAME = "^[-' \\p{L}]{2,20}$";
    private final String LASTNAME = "^[-' \\p{L}]{2,20}$";
    private final String USERNAME = "^\\w{5,25}$";
    private final String NUMBER = "^[0-9]{10}$";

    //        private final String PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private final String PASSWORD = "^\\w{8,20}$";
    private final HashMap<StringItem, String> validations = new HashMap<>();
    private StringItem item;

    @Override
    public void initialize(ValidateString constraintAnnotation) {
        validations.put(StringItem.USERNAME, USERNAME);
        validations.put(StringItem.FIRSTNAME, FIRSTNAME);
        validations.put(StringItem.LASTNAME, LASTNAME);
        validations.put(StringItem.NUMBER, NUMBER);
        validations.put(StringItem.PASSWORD, PASSWORD);

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
