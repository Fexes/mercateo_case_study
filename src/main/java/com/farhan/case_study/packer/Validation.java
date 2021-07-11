package com.farhan.case_study.packer;

import com.farhan.case_study.packer.exception.ValidationException;

import java.util.function.Function;

/**
 * This class is to check that given conditions are met or not.
 * @author Farhan Fida
 */
public class Validation<T> {

    private Function<T, Boolean> condition;
    private String message;

    /**
     * @param condition the condition to check
     * @param message the message of {@link ValidationException} when the condition is not met
     */
    public Validation(Function<T, Boolean> condition, String message) {
        this.condition = condition;
        this.message = message;
    }

    /**
     * @param t the validation subject
     * @throws ValidationException when the condition is not met
     */
    public void validate(T t) throws ValidationException {
        if (condition.apply(t)) {
            throw new ValidationException(message);
        }
    }



}
