package com.sbhandare.pawdopt.Service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class PawDoptValidationService {

    public static boolean isValidEmail(String email){
        return EmailValidator.getInstance().isValid(email) && StringUtils.length(email) <= 355;
    }

    public static boolean isValidPassword(String password){
        return StringUtils.length(password) <= 100;
    }

    public static boolean isValidFirstName(String fName){
        return StringUtils.length(fName) <= 50;
    }

    public static boolean isValidLastName(String lName){
        return StringUtils.length(lName) <= 50;
    }
}
