package com.farhan.case_study.packer.exception;

/**
 * Exceptions are thrown when a conditions are not met for reading a file.
 * @author Farhan Fida
 */
public class FileException extends Exception {

    public FileException(String message, Exception e) {
        super(message, e);
    }

    public FileException(String message) {
        super(message);
    }
}
