/*
 * Copyright (c) 2024.
 */

package com.gts.backcommons.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException{

    private final List<String> errorMessages;

    public ValidationException(List<String> errorMessages) {
        super("Validation failed for the form fields");
        this.errorMessages = errorMessages;
    }

}
