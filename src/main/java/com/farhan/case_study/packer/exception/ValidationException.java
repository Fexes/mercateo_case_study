package com.farhan.case_study.packer.exception;

import com.farhan.case_study.packer.Validation;

/**
 * Exceptions are thrown when a conditions are not met for the scenario.
 * @author Farhan Fida
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}

