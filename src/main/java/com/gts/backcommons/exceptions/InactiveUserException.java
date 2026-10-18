package com.gts.backcommons.exceptions;

import com.gts.backcommons.exceptions.constants.ExceptionConstant;
import org.springframework.security.authentication.DisabledException;

public class InactiveUserException extends DisabledException {
    public static final String ERROR_CODE = "USER_INACTIVE";

    public InactiveUserException() {
        super(ExceptionConstant.USER_INACTIVE_MESSAGE);
    }
}
